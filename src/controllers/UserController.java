package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import helpers.PoolManager;
import helpers.PropertiesReader;
import models.Response;
import models.User;

public class UserController { 
	private static PropertiesReader prop = PropertiesReader.getInstance();
	private static PoolManager poolManager = PoolManager.getPoolManager();
	
	public static Response<User> loginUser(User user) throws IOException {
		Connection conn = poolManager.getConn();
		Response<User> response = new Response<>();
		String query = prop.getValue("db.login.user");
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getEmail().toLowerCase());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				getUserData(rs, user);
				response.setStatus(200);
				response.setMessage("Access granted");
				response.setData(user);
			} else {
				response.setStatus(401);
				response.setMessage("Invalid credentials, try again");
			} 
		} catch(SQLException e) {
			response.setStatus(500);
			response.setMessage("Database error");
			e.printStackTrace();
		}
		poolManager.returnConn(conn);
		return response;
	}
	
	public static Response<User> registerUser(User user) throws IOException {
		Connection conn = poolManager.getConn();
		Response<User> response = new Response<>();
		String query = prop.getValue("db.register.user");
		if(checkEmail(user.getEmail().toLowerCase())) {
			response.setStatus(409);
			response.setMessage("Email in use");
			poolManager.returnConn(conn);
			return response;
		}
		try {
			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getEmail().toLowerCase());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getCountry());
			pstmt.setString(6, user.getCity());
			pstmt.setString(7, user.getState());
			pstmt.setString(8, user.getStreet());
			pstmt.setString(9, user.getPostalCode());
			pstmt.setString(10, user.getPhone());
			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			user.setId(rs.getInt(1));
			response.setStatus(200);
			response.setMessage("User successfully registered");
			user.setPassword(null);
			response.setData(user);
		} catch(SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setMessage("Database error");
		}
		poolManager.returnConn(conn);
		return response;
	}
	
	public static Response<User> updateUser(User user) throws IOException {
		Connection conn = poolManager.getConn();
		Response<User> response = new Response<>();
		String query = prop.getValue("db.update.user");
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getEmail().toLowerCase());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getCountry());
			pstmt.setString(6, user.getCity());
			pstmt.setString(7, user.getState());
			pstmt.setString(8, user.getStreet());
			pstmt.setString(9, user.getPostalCode());
			pstmt.setString(10, user.getPhone());
			pstmt.setInt(11, user.getId());
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows == 1) {
				response.setStatus(200);
				response.setMessage("User successfully registered");
				response.setData(user);
			} else {
				response.setStatus(401);
				response.setMessage("Invalid credentials");
				response.setData(null);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setMessage("Database error");
			response.setData(user);
		}
		poolManager.returnConn(conn);
		return response;
	}
	
	public static Response<User> deleteUser(ResultSet rs, User user) throws SQLException {
		Connection conn = poolManager.getConn();
		Response<User> response = new Response<>();
		String query = prop.getValue("db.delete.user");
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user.getId());
			pstmt.setString(2, user.getPassword());
			int affectedRows = pstmt.executeUpdate();
			if(affectedRows == 1) {
				response.setStatus(200);
				response.setMessage("User successfully deleted");
				response.setData(user);
			} else {
				response.setStatus(401);
				response.setMessage("Invalid credentials");
				response.setData(null);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setMessage("Database error");
			response.setData(user);
		}
		poolManager.returnConn(conn);
		return response;
	}
	
	public static void getUserData(ResultSet rs, User user) throws SQLException {
		user.setId(rs.getInt(1));
		user.setName(rs.getString("nombre_cliente"));
		user.setLastName(rs.getString("apellido_cliente"));
		user.setEmail(rs.getString("correo_cliente"));
		user.setCountry(rs.getString("pais_cliente"));
		user.setCity(rs.getString("ciudad_cliente"));
		user.setState(rs.getString("estado_cliente"));
		user.setStreet(rs.getString("calle_cliente"));
		user.setPostalCode(rs.getString("codpostal_cliente"));
		user.setPhone(rs.getString("telefono_cliente"));
		user.setPassword(null);
	}
	
	public static boolean checkEmail(String email) {
		Connection conn = poolManager.getConn();
		String query = prop.getValue("db.check.email");
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email.toLowerCase());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				poolManager.returnConn(conn);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			poolManager.returnConn(conn);
			return true;
		} finally {
			poolManager.returnConn(conn);
		}
		return false;
	}
}
