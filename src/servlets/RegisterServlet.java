package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.UserController;
import helpers.DBConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		
		String name = request.getParameter("name");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String street = request.getParameter("street");
		String postalCode = request.getParameter("postalcode");
		String phone = request.getParameter("phone");
		
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/amazon", "postgres", "postgres");
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO cliente (nombre_cliente, apellido_cliente, correo_cliente, contraseña_cliente, pais_cliente, ciudad_cliente, estado_cliente, calle_cliente, codpostal_cliente, telefono_cliente) VALUES(?,?,?,?,?,?,?,?,?,?)");
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
			int i = pstmt.executeUpdate();
			if(i > 0) {
				System.out.println("User succesfully registered");
			} 
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
