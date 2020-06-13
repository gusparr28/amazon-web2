package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import helpers.DBConnection;
import helpers.PropertiesReader;

public class ProductController {
	
	PropertiesReader prop = new PropertiesReader();
	DBConnection conn = new DBConnection();
	Connection con = conn.getConn();

	public String publishProduct(String title, String description, String dir, String amount, String email) {
		String query = prop.getValue("db.publish.product");
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, description);
			pstmt.setString(3, dir);
			pstmt.setString(4, amount);
			pstmt.setString(5, email);
			int rowsInserted = pstmt.executeUpdate();
			if(rowsInserted == 1) {
				System.out.println("Product published successfully");
				return "published";
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return query;
	}
	
	public String updateProduct() {
		String query = prop.getValue("db.update.product");
		return query;
	}
	
	public String deleteProduct() {
		String query = prop.getValue("db.delete.product");
		return query;
	}

	public JSONObject showProduct(String email) {
		String query = prop.getValue("db.show.product");
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				String id = rs.getString("id_publicacion");
				String title = rs.getString("titulo_publicacion");
				String description = rs.getString("descripcion_publicacion");
				String image = rs.getString("img_publicacion");
				String amount = rs.getString("monto_publicacion");
				
				JSONObject json = new JSONObject();
				
				json.put("id", id);
				json.put("title", title);
				json.put("description", description);
				json.put("image", image);
				json.put("amount", amount);
				
				return json;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String deleteProducts(String email) {
		String query = prop.getValue("db.delete.products");
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1, email);
			int rowsDeleted = pstmt.executeUpdate();
			if(rowsDeleted > 0) {
				System.out.println("Products successfully deleted");
				return "deleted";
			} else {
				System.out.println("Error");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return query;
	}
}
