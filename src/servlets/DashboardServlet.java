package servlets;

import java.io.IOException;
import javax.servlet.ServletException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONObject;

import controllers.ProductController;

@MultipartConfig
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String session1 = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String session1 = (String) session.getAttribute("email");
		
		ProductController product = new ProductController();
		List<String> json = product.showProducts(session1);
		
		PrintWriter writer = response.getWriter();
		writer.print(json.toString());
		writer.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session1 = (String) session.getAttribute("email");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String amount = request.getParameter("amount");
		
		Part file = request.getPart("image");
		InputStream is = file.getInputStream();
		OutputStream os = null;
		try {
			String dir = "/home/gustavo/Pictures/" + this.getFileName(file);
			os = new FileOutputStream(dir);
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			ProductController product = new ProductController();
			
			String published = product.publishProduct(title, description, dir, amount, session1);
			
			if(published.equals("published")) {
				response.sendRedirect("http://localhost:8080/Amazon/public/views/dashboard.html");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null) {
				is.close();
			}
			if(os != null) {
				os.close();
			}
		}
	}
	
	private String getFileName(Part file) {
		for(String content : file.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
