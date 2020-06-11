package controllers;

import java.sql.Connection;

import helpers.DBConnection;
import helpers.PropertiesReader;

public class ProductController {
	
	PropertiesReader prop = new PropertiesReader();
	DBConnection conn = new DBConnection();
	Connection con = conn.getConn();
	
	public String publishProduct() {
		String query = prop.getValue("db.publish.product");
		return query;
	}
	
	public String showProduct() {
		String query = prop.getValue("db.show.product");
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
}
