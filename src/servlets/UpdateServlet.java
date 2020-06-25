package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import controllers.UserController;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession(true);
		String session1 = (String) session.getAttribute("email");
		
		UserController user = new UserController();
		JSONObject json = user.getUserCredentials(session1);
		
		PrintWriter writer = response.getWriter();
		writer.print(json.toString());
		writer.flush();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		String session1 = (String) session.getAttribute("email");
		
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String street = request.getParameter("street");
		String postalCode = request.getParameter("postalCode");
		String phone = request.getParameter("phone");
	
		UserController user = new UserController();
		
		String updated = user.updateUser(country, city, state, street, postalCode, phone, session1);
		
		if(updated.equals("updated")) {
			response.sendRedirect("http://localhost:8080/Amazon/public/views/dashboard.html");
		} else {
			PrintWriter writer = response.getWriter();
			writer.print("<h1>Error 404</h1>");
			response.setStatus(404);
		}
	}
}
