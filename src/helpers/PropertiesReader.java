package helpers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;


public class PropertiesReader {

	public static Properties loadPropertiesFile() throws Exception {
		String path = "./resources/config.properties";
		Properties prop = new Properties();
		InputStream is = new FileInputStream(path);
		prop.load(is);
		is.close();
		return prop;
	}
	
	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			Properties prop = loadPropertiesFile();
			
			String driver = prop.getProperty("db.driver.class");
			String url = prop.getProperty("db.conn.url");
			String username = prop.getProperty("db.username");
			String password = prop.getProperty("db.password");
			
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
}
