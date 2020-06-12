package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.UserController;
import helpers.Encryption;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Encryption encryption = new Encryption();
		String passEncrypted = encryption.getSHA256(password, email.toLowerCase());
		
		UserController user = new UserController();
		
		String accessed = user.loginUser(email, passEncrypted);
		
		HttpSession session = request.getSession(true);
		
		if(accessed.equals("accessed") && email.equals("admin@admin.com")) {
			System.out.println("Access granted");
			session.setAttribute("email", email);
			response.sendRedirect("http://localhost:8080/Amazon/public/views/admin.html");
		} else if(accessed.equals("accessed") && !email.equals("admin@admin.com")) {
			session.setAttribute("email", email);
			response.sendRedirect("http://localhost:8080/Amazon/public/views/dashboard.html");
		} else {
			response.sendRedirect("http://localhost:8080/Amazon/public/views/errorLogin.html");
			response.setStatus(404);
		}
	}
}
