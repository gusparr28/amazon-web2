package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import helpers.DBConnection;
import helpers.PropertiesReader;

public class UserController { 
	
	PropertiesReader prop = new PropertiesReader();
	DBConnection conn = new DBConnection();
	Connection con = conn.getConn();
	
	public String loginUser(String email, String password) throws IOException {
		String query = prop.getValue("db.login.user");
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email.toLowerCase());
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("User successfully logged");
				return "accessed";
			} else {
				System.out.println("Error");
			} 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	public String registerUser(String name, String lastName, String email, String password, String country, String city, String state, String street, String postalCode, String phone) throws IOException {
		String query = prop.getValue("db.register.user");
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, lastName);
			pstmt.setString(3, email.toLowerCase());
			pstmt.setString(4, password);
			pstmt.setString(5, country);
			pstmt.setString(6, city);
			pstmt.setString(7, state);
			pstmt.setString(8, street);
			pstmt.setString(9, postalCode);
			pstmt.setString(10, phone);
			int rowsInserted = pstmt.executeUpdate();
			if(rowsInserted > 0) {
				System.out.println("User succesfully registered");
				return "registered";
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	public String updateUser(String country, String city, String state, String street, String postalCode, String phone, String email) throws IOException {
		String query = prop.getValue("db.update.user");
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, country);
			pstmt.setString(2, city);
			pstmt.setString(3, state);
			pstmt.setString(4, street);
			pstmt.setString(5, postalCode);
			pstmt.setString(6, phone);
			pstmt.setString(7, email.toLowerCase());
			int rowsUpdated = pstmt.executeUpdate();
			if(rowsUpdated > 0) {
				System.out.println("An existing user was updated successfully");
				return "updated";
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	public String deleteUser(String email, String password) throws SQLException {
		String query = prop.getValue("db.delete.user");
		ProductController product = new ProductController();
		product.deleteProducts(email);
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			int rowsDeleted = pstmt.executeUpdate();
			if(rowsDeleted > 0) {
				System.out.println("User successfully deleted");
				return "deleted";
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	public JSONObject getUserCredentials(String email) {
		String query = "SELECT * FROM cliente WHERE id_cliente = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("nombre_cliente");
				String lastName = rs.getString("apellido_cliente");
				String emailUser = rs.getString("id_cliente");
				String country = rs.getString("pais_cliente");
				String city = rs.getString("ciudad_cliente");
				String state = rs.getString("estado_cliente");
				String street = rs.getString("calle_cliente");
				String postalCode = rs.getString("codpostal_cliente");
				String phone = rs.getString("telefono_cliente");
				
				JSONObject json = new JSONObject();
				
				json.put("name", name);
				json.put("lastName", lastName);
				json.put("email", emailUser);
				json.put("country", country);
				json.put("city", city);
				json.put("state", state);
				json.put("street", street);
				json.put("postalCode", postalCode);
				json.put("phone", phone);
				
				return json;
			}
			rs.close();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
