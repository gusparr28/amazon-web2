package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	Connection conn = null;
	
	public DBConnection() {
		String driver = "org.postgresql.Driver";
		String url = "jdbc:postgresql://ec2-34-194-198-176.compute-1.amazonaws.com:5432/d5ecb09k5g7hio";
		String username = "xrhlwjmkoiyxcq";
		String password = "386fe8b117e2c385e3676039da5b3948b022e30b0548515dc2467f6554e8e007";
		try {
			Class.forName(driver);
				
			conn = DriverManager.getConnection(url, username, password);
			
			if(conn != null) {
				System.out.println("Successfully connected to PostgreSQL");
			} else {
				System.out.println("Unable to connect to PostgreSQL");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public Connection getConn() {
		return conn;
	}
}
