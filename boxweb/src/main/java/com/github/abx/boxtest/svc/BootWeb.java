package com.github.abx.boxtest.svc;

import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class BootWeb {

	public static void main(String[] args) {
		
	}
	
	
	public static Tomcat buildTomcat(BiConsumer<HttpServletRequest, HttpServletResponse> processor) {
		if(processor==null) {
			processor = (req,resp)->{try {
				resp.setContentType("application/json");
				resp.setStatus(200);
				resp.getWriter().write("{}");
				resp.getWriter().flush();
				resp.getWriter().close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}};
		}
		TomcatURLStreamHandlerFactory.disable();
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(9099);
		tomcat.getConnector();
		String baseDir = System.getProperty("java.io.tmpdir");
		tomcat.setBaseDir(baseDir);
		Context ctx = tomcat.addWebapp("", new File(baseDir).getAbsolutePath());
		final BiConsumer<HttpServletRequest, HttpServletResponse> reqProcessor = processor;
		Tomcat.addServlet(ctx, "ExtApp", new HttpServlet() {
			@Override
			protected void service(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				reqProcessor.accept(req, resp);
			}			
		});
		return tomcat;
	}


}
