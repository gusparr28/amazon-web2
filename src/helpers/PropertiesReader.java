package helpers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public class PropertiesReader {

	private static PropertiesReader prop = new PropertiesReader();
	
	public static Properties loadPropertiesFile() throws Exception {
		String path = "./resources/config.properties";
		Properties p = new Properties();
		InputStream is = new FileInputStream(path);
		p.load(is);
		is.close();
		return p;
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			Properties p = loadPropertiesFile();
			
			String driver = p.getProperty("db.driver.class");
			String url = p.getProperty("db.conn.url");
			String username = p.getProperty("db.username");
			String password = p.getProperty("db.password");
			
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, username, password);
			
			if (conn != null) {
				System.out.println("Successfully connected to PostgreSQL ");
			} else {
				System.out.println("Unable to connect to PostgreSQL");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	public static PropertiesReader getInstance() {
		return prop;
	}
	
	@SuppressWarnings("null")
	public String getValue(String value) {
		Properties p = null;
		return p.getProperty(value);
	}
	
}
