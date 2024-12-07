package com.github.abx.boxtest.svc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;

import com.github.abx.boxtest.BoxLauncher;

public class DatabaseLauncher implements AutoCloseable {
	
	private final int port;
	
	public DatabaseLauncher(int port,int webPort) throws SQLException {
		this.port = port;
		Server.main(new String[]{"-tcp","-tcpPort", String.valueOf(port), "-tcpAllowOthers", "-ifNotExists","-web","-webAllowOthers","-webPort",String.valueOf(webPort)});
	}

	@Override
	public void close() throws Exception {
		
	}

	public void initDbs()  throws Exception {		
		for(int i=1;i<5;i++) {
			try(Connection conn=DriverManager.getConnection("jdbc:h2:tcp://localhost:"+port+"/mem:testdb"+i+";DB_CLOSE_DELAY=-1")){
				try(Statement stmt=conn.createStatement()){
							try(InputStream is=BoxLauncher.class.getResourceAsStream("/schema"+i+".sql")){
						String schemaContents = new String(is.readAllBytes());
						stmt.execute(schemaContents);
					}					
				}
			}
		}
	}

}
