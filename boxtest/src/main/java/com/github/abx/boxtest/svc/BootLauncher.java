package com.github.abx.boxtest.svc;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.h2.tools.Server;
import org.springframework.boot.loader.launch.Archive;
import org.springframework.boot.loader.launch.JarLauncher;

public class BootLauncher extends JarLauncher {
	
	private BootJarClassLoader bootJarClassLoader;

	public BootLauncher(File archive) throws Exception {
		super(Archive.create(archive));
	}

	@Override
	public void launch(String[] args) throws Exception {
		super.launch(args);
	}

	@Override
	protected ClassLoader createClassLoader(Collection<URL> urls) throws Exception {
		if (this.classPathIndex != null) {
			throw new UnsupportedOperationException("classPathIndex!=null");
		}
		List<URL> urlsCustom = new ArrayList<>();
		urlsCustom.addAll(urls);
		urlsCustom.add(Server.class.getProtectionDomain().getCodeSource().getLocation());
		bootJarClassLoader = new BootJarClassLoader(urlsCustom);
		return bootJarClassLoader;
	}

	static class BootJarClassLoader extends URLClassLoader {

		public BootJarClassLoader(List<URL> urls) {
			super(urls.toArray(new URL[0]), null);
		}

		@Override
		protected Class<?> findClass(String className) throws ClassNotFoundException {			
			try {
				Class<?> out = super.findClass(className);
				if("org.apache.catalina.webresources.TomcatURLStreamHandlerFactory".equals(className)) { //temporary
					 try {
						out.getMethod("disable").invoke(out);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				 }
				return out;
			} catch (ClassNotFoundException e) {
				return ClassLoader.getPlatformClassLoader().loadClass(className);
			}
		}

	}

}
