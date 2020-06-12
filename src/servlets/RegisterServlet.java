package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.UserController;
import helpers.Encryption;

import java.io.*;

@WebServlet("/register")

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String street = request.getParameter("street");
		String postalCode = request.getParameter("postalCode");
		String phone = request.getParameter("phone");
	
		Encryption encryption = new Encryption();
		String passEncrypted = encryption.getSHA256(password, email.toLowerCase());
		
		UserController user = new UserController();
		
		String registered = user.registerUser(email, name, lastName, passEncrypted, country, city, state, street, postalCode, phone);
		if(registered.equals("registered")) {
			response.sendRedirect("http://localhost:8080/Amazon/public/views/login.html");
		} else {
			PrintWriter writer = response.getWriter();
			writer.print("<h1>Unable to register successfully</h1>");
			response.setStatus(404);
		}
	}
}
