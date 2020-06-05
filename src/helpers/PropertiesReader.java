package helpers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.Connection;

public class PropertiesReader {
	
	Connection conn = null;
	InputStream input;
	Properties prop;
	
	public PropertiesReader() {
		try {
			prop = new Properties();
			String path = "./resources/config.properties";
			input = getClass().getClassLoader().getResourceAsStream(path);
			if(input != null) {
				prop.load(input);
				String driver = prop.getProperty("db.driver.class");
				String url = prop.getProperty("db.conn.url");
				String username = prop.getProperty("db.username");
				String password = prop.getProperty("db.password");
				
				Class.forName(driver);
				
				conn = DriverManager.getConnection(url, username, password);
				
				if(conn != null) {
					System.out.println("Successfully connected to PostgreSQL ");
				} else {
					System.out.println("Unable to connect to PostgreSQL");
				}
			} else {
				throw new FileNotFoundException("File not found");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}
	
	public String getValue(String key) {
		return prop.getProperty(key);
	}
}
