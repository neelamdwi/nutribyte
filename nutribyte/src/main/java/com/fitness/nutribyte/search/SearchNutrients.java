package com.fitness.nutribyte.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fitness.nutribyte.beans.Nutrient;
import com.fitness.nutribyte.beans.Product;
import com.fitness.nutribyte.datalayer.USDADao;

@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/searchnutrients")
public class SearchNutrients extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		List<Nutrient> nutrients = new ArrayList<>();
		String fdcId = (String)request.getParameter("fdcId");
		if (fdcId != null) {
			//find the product
			Product product = USDADao.productMap.get(fdcId);
			//search its nutrients
			nutrients = USDADao.searchNutrientsForProduct(fdcId);
			//set attributes
			session.setAttribute("selectedProduct", product);
			session.setAttribute("nutrientsList", nutrients);	
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/search/searchResults.jsp");
			rd.forward(request, response);
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
