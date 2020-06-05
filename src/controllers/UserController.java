package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DBConnection;
import helpers.PropertiesReader;

public class UserController { 
	
	public void loginUser(String email, String password) throws IOException {
		String query = "SELECT * FROM cliente WHERE correo_cliente = ? AND contraseña_cliente = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email.toLowerCase());
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				System.out.println("User successfully logged");
			} else {
				System.out.println("Error");
			} 
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void registerUser(String name, String lastName, String email, String password, String country, String city, String state, String street, String postalCode, String phone) throws IOException {
		String query = "INSERT INTO cliente (nombre_cliente, apellido_cliente, correo_cliente, contraseña_cliente, pais_cliente, ciudad_cliente, estado_cliente, calle_cliente, codpostal_cliente, telefono_cliente) VALUES(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
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
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(int id, String name, String lastName, String email, String password, String country, String city, String state, String street, String postalCode, String phone) throws IOException {
		String query = "UPDATE cliente SET nombre_cliente = ?, apellido_cliente = ?, correo_cliente = ?, pais_cliente = ?, ciudad_cliente = ?, estado_cliente = ?, calle_cliente = ?, codpostal_cliente = ?, telefono_cliente = ? WHERE id_cliente = ? AND contraseña_cliente = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
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
		String query = "DELETE FROM cliente WHERE id_cliente = ? and contraseña_cliente = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
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
		
	public boolean emailUser(String email) {
		String query = "SELECT * FROM cliente WHERE correo_cliente = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email.toLowerCase());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
		}
		return false;
	}
}
