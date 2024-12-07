package com.github.abx.boxtest;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.catalina.startup.Tomcat;

import com.github.abx.boxtest.svc.BootKafka;
import com.github.abx.boxtest.svc.BootLauncher;
import com.github.abx.boxtest.svc.BootSpark;
import com.github.abx.boxtest.svc.BootWeb;
import com.github.abx.boxtest.svc.DatabaseLauncher;

public class BoxLauncher {

	public static void main(String[] args) throws Exception {
		runBox();
	}

	public static void runBox() throws Exception {	
		try (DatabaseLauncher db=new DatabaseLauncher(9091,9092)) {
			db.initDbs();	
			Tomcat tomcat = runBoxSvc();
			runApps();
			tomcat.getServer().await();			
		}
	}

	public static Tomcat runBoxSvc() throws Exception {
		System.setProperty("boxapp.boxkafka.brokers.propkey","bootapp.kafka.bootstrap-servers");
		System.setProperty("boxapp.boxkafka.port","8088");
		System.setProperty("boxapp.boxkafka.topics","PAYMENT_TOPIC");
		BootKafka.main(new String[0]);
		BootSpark.main(new String[0]);
		return BootWeb.buildTomcat(null);
	}

	public static void runApps() throws Exception {
		for (File jarFile : new File("target/bootbox").listFiles()) {
			if (jarFile.getName().endsWith(".jar")) {
				System.clearProperty("spring.config.location");
				if(jarFile.getName().startsWith("bootapp")) {
					if (jarFile.getName().startsWith("bootapp1-")) {
						setConfigLoc("/bootapp1.properties");
					} else if (jarFile.getName().startsWith("bootapp2-")) {
						setConfigLoc("/bootapp2.properties");
					} else if (jarFile.getName().startsWith("bootapp3-")) {
						setConfigLoc("/bootapp3.properties");
					}
					new BootLauncher(jarFile).launch(new String[0]);
				}
			}
		}
	}
	


	private static void setConfigLoc(String classpathResource) throws URISyntaxException {
		System.setProperty("spring.config.location",
				new File(BoxLauncher.class.getResource(classpathResource).toURI()).getAbsolutePath());

	}
}
