package com.github.abx.boxtest.svc;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.hadoop.shaded.org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.hadoop.shaded.org.apache.http.client.methods.HttpPost;
import org.apache.hadoop.shaded.org.apache.http.entity.StringEntity;
import org.apache.hadoop.shaded.org.apache.http.impl.client.CloseableHttpClient;
import org.apache.hadoop.shaded.org.apache.http.impl.client.HttpClients;
import org.apache.spark.deploy.master.Master;
import org.apache.spark.deploy.worker.Worker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BootSpark {

	public static void main(String[] args) {
		startSparkCluser(Integer.parseInt(System.getProperty("boxapp.boxspark.master_port", "8077")),
				Integer.parseInt(System.getProperty("boxapp.boxspark.webui_port", "8078")),
				Integer.parseInt(System.getProperty("boxapp.boxspark.worker_base_port", "12300")));
	}
	
	private static final ExecutorService execs = Executors.newFixedThreadPool(4);
	private static int masterPort;
	public static void startSparkCluser(int port,int webuiPort,int workerWebuiBasePort) {
		if(masterPort!=0) {
			throw new IllegalStateException();
		}
		if(port<=0) {
			throw new IllegalArgumentException();
		}
		masterPort = port;
		int workerCount = 3;
		execs.submit(()->Master.main(new String[]{"--host", "localhost", "--port", String.valueOf(port),"--webui-port",String.valueOf(webuiPort)}));		
		for(int i=0;i<workerCount;i++) {
			execs.submit(()->Worker.main(new String[]{"--webui-port",String.valueOf(workerWebuiBasePort),"spark://localhost:"+port}));
		}
		boolean ready = false;
		for (int i = 0; i < 60; i++) {
			try {
				Thread.sleep(1000);
				String response = HttpClient.newHttpClient().send(
						HttpRequest.newBuilder(URI.create("http://localhost:" + webuiPort + "/json/")).GET().build(),
						BodyHandlers.ofString()).body();
				Map<String, Object> result = new ObjectMapper().readValue(response,
						new TypeReference<Map<String, Object>>() {
						});
				if (result.get("workers") instanceof List && ((List<?>) result.get("workers")).size() == workerCount) {
					ready = true;
					break;
				}
			} catch (Exception e) {
				// retry until ready
				System.err.println("error checking spark status "+i+" "+e.getMessage()+" retrying ...");
			}
		}
		if(!ready) {
			throw new IllegalStateException("spark cluster error");
		}
	}
	
	public static int submitJob(Class<?> mainClass) throws IOException, URISyntaxException {
        String sparkMasterUrl = "http://localhost:"+masterPort+"/v1/submissions/create";
        File jarFile = new File(mainClass.getProtectionDomain().getCodeSource().getLocation().toURI());
        String payload = "{"
                + "  \"action\": \"CreateSubmissionRequest\","
                + "  \"appResource\": \"file://"+jarFile.getAbsolutePath()+"\","
                + "  \"clientSparkVersion\": \"3.0.0\","
                + "  \"mainClass\": \""+mainClass.getName()+"\","
                + "  \"appArgs\": [],"
                + "  \"sparkProperties\": {"
                + "    \"spark.jars\": \""+jarFile.getAbsolutePath()+"\","
                + "    \"spark.app.name\": \""+mainClass.getSimpleName()+"\""
                + "  }"
                + "}";

           try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
               HttpPost httpPost = new HttpPost(sparkMasterUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(payload));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                return statusCode;
            }
        }
    
	}

	public static void createLocalClusterAndRunJob(Class<?> mainClass, int numberOfWorkersN, int coresPerWorkerC,
			int memoryinMbPerWorkerM) throws IOException, URISyntaxException {
		String masterUrl = "local-cluster[" + numberOfWorkersN + "," + coresPerWorkerC + "," + memoryinMbPerWorkerM
				+ "]";
		submitJob(mainClass, masterUrl);
	}
	
	public static void submitJob(Class<?> mainClass, String masterUrl) throws IOException, URISyntaxException {
		File jarFile = new File(mainClass.getProtectionDomain().getCodeSource().getLocation().toURI());
		String[] submitArgs = new String[] {
				"--class",mainClass.getName(),
				"--master",masterUrl,
				"--deploy-mode","client", //client = driver runs on client, cluster = driver runs on cluster
				"--jars",jarFile.getAbsolutePath(), //additional jars in classpath, using app jar as example
				jarFile.getAbsolutePath(),
				"jobarg1","jobarg2"};
		org.apache.spark.deploy.SparkSubmit.main(submitArgs);
	}
	
}
