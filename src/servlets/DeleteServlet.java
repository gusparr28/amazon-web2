package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.UserController;
import helpers.Encryption;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Encryption encryption = new Encryption();
		String passEncrypted = encryption.getSHA256(password, email.toLowerCase());
		
		UserController user = new UserController();
		
		String deleted = null;
		try {
			deleted = user.deleteUser(email, passEncrypted);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(deleted.equals("deleted")) {
			System.out.println("User deleted successfully");
			response.sendRedirect("http://localhost:8080/Amazon/public/views/login.html");
		}
	}
}
