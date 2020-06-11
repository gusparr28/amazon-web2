package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.UserController;
import helpers.Encryption;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
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
		user.updateUser(name, lastName, email, passEncrypted, country, city, state, street, postalCode, phone);
		
	}
}
