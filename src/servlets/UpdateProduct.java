package servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controllers.ProductController;

@MultipartConfig
@WebServlet("/updateProduct")
public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String amount = request.getParameter("amount");
		String id = request.getParameter("id-product");
		
		Part file = request.getPart("image");
			
		InputStream is = file.getInputStream();
		OutputStream os = null;
		try {
			String dir = this.getFileName(file);
			os = new FileOutputStream(dir);
			int read = 0;
			byte[] bytes = new byte[1024];
			
			while((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
			}
			
			ProductController product = new ProductController();
			
			String updated = product.updateProduct(title, description, dir, amount, id);
			
			if(updated.equals("updated")) {
				response.sendRedirect("http://localhost:8080/Amazon/public/views/dashboard.html");
			};
			
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
