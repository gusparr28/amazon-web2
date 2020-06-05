package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public Connection conn(){
		Connection conn = null;
		String url = "jdbc:postgresql://localhost:5432/amazon";
		String username = "postgres";
		String pass = "postgres";
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, pass);
			if(conn != null) {
				System.out.println("Sucessfully connected to PostgreSQL");
			} else {
				System.out.println("Unable to connect to PostgreSQL");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
