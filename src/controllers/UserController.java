package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public void updateUser(int id, String name, String lastName, String email, String password, String country, String city, String state, String street, String postalCode, String phone) throws IOException {
		String query = prop.getValue("db.update.user");
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
			pstmt.setInt(11, id);
			int rowsUpdated = pstmt.executeUpdate();
			if(rowsUpdated > 0) {
				System.out.println("An existing user was updated successfully");
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(int id, String password) throws SQLException {
		String query = prop.getValue("db.delete.user");
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setString(2, password);
			int rowsDeleted = pstmt.executeUpdate();
			if(rowsDeleted > 0) {
				System.out.println("User successfully deleted");
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
