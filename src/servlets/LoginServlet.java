package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.UserController;
import helpers.Encryption;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Encryption encryption = new Encryption();
		String passEncrypted = encryption.getSHA256(password, email.toLowerCase());
		
		UserController user = new UserController();
		user.loginUser(email, passEncrypted);
		
		String accessed = user.loginUser(email, passEncrypted);
	
		if(accessed.equals("accessed") && email.equals("admin@admin.com")) {
			System.out.println("Access granted");
			response.sendRedirect("http://localhost:8080/Amazon/public/views/admin.html");
		} else if(accessed.equals("accessed") && !email.equals("admin@admin.com")) {
			response.sendRedirect("http://localhost:8080/Amazon/public/views/client.html");
		} else {
			PrintWriter writer = response.getWriter();
			writer.print("<h1>Unable to login successfully</h1>");
			response.setStatus(404);
		}
	}
}
