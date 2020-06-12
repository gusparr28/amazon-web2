package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	public String showProduct() {
		String query = prop.getValue("db.show.product");
		return query;
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
