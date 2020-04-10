package com.fitness.nutribyte.datalayer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.fitness.nutribyte.beans.Nutrient;
import com.fitness.nutribyte.beans.Product;

/**Dao is NutriByte's Data Access Object class to connect with
 * NutriByteDB through the data source defined in tomcat's context.xml.
 * It uses Singleton pattern to ensure single instance. 
 */
public class Dao {
	private static Dao dao;
	Connection connection;

	public static Map<String, Product> productMap = new HashMap<>();  //populated by DataLoader
	public static Map<String, Nutrient> nutrientMap = new HashMap<>();  //populated by DataLoader
	
	//connect to data source as defined in context.xml
	private Dao() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			Context xmlContext = (Context) ic.lookup("java:comp/env");
			DataSource datasource = (DataSource) xmlContext.lookup("datasource"); //defined in context.xml
			connection = datasource.getConnection();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	//singleton pattern
	public static Dao getInstance() {
		if (dao == null) {
			dao = new Dao();
		}
		return dao;
	}
	

	//returns a list of product objects whose description
	//contains the searchString
	public static List<Product> searchProductOnName(String searchString) {
		List<Product> products = new ArrayList<>();
		for (Map.Entry<String, Product> entry : productMap.entrySet()) {
			if (entry.getValue().getDescription().toLowerCase().contains(searchString.toLowerCase())) {
				products.add(entry.getValue());
			}
		}
		return products;
	}

	//returns a list of Nutrient objects for a given 
	//product fdc_id
	public static List<Nutrient> searchNutrientsForProduct(String fdc_id) {
		List<Nutrient> nutrientsList = new ArrayList<>();
		Product product = productMap.get(fdc_id);
		for (String nutrientId: product.getProductNutrientMap().keySet()) {
			nutrientsList.add(nutrientMap.get(nutrientId));
		}
		return nutrientsList;
	}
}
