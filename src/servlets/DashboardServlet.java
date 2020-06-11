package servlets;

<<<<<<< HEAD
import java.io.IOException;
import javax.servlet.ServletException;
=======
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
>>>>>>> master
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
<<<<<<< HEAD

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

=======
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controllers.ProductController;

@MultipartConfig
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String session1 = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session1 = (String) session.getAttribute("email");
		System.out.println("este es el metodo get " + session1);
		String image = request.getParameter("input-file");
		ServletOutputStream sos = response.getOutputStream();
		String dir = "/home/gustavo/Pictures/" + image;
		InputStream is = new FileInputStream(image);
		String mimeType = "image/jpg";
		byte[] bytes = new byte[1024];
		int bytesRead;
		
		response.setContentType(mimeType);
		
		while((bytesRead = is.read(bytes)) != -1) {
			sos.write(bytes, 0, bytesRead);
		}
		is.close();
		sos.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session1 = (String) session.getAttribute("email");
		System.out.println("este es el metodo post " + session1);
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String amount = request.getParameter("amount");
		
		Part file = request.getPart("image");
		InputStream is = file.getInputStream();
		OutputStream os = null;
		try {
			String dir = "home/gustavo" + this.getFileName(file);
			os = new FileOutputStream(dir);
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			ProductController product = new ProductController();
			product.publishProduct(title, description, dir, amount, session1);
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
>>>>>>> master
}
