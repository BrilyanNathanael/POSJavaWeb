package com.wide.sale.web;

import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wide.sale.domain.Database;
import com.wide.sale.domain.Item;
import com.wide.sale.repository.ItemRepository;

/**
 * Servlet implementation class ItemServlet
 */
@WebServlet("/item.do")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		StringBuilder fileJsp = new StringBuilder();
		
		if("list".equals(action)) {
			HttpSession session = request.getSession();
//			session.setAttribute("user_context", "Nael");
			
			ItemRepository repo = new ItemRepository();
			List<Item> itemList = repo.findAll();
			
			request.setAttribute("data_item", itemList);
			fileJsp.append("WEB-INF/jsp/list-item.jsp");
		}
		else if("create".equals(action)) {
			fileJsp.append("WEB-INF/jsp/create-item.jsp");
		}
		else if("update".equals(action)) {
			ItemRepository repo = new ItemRepository();
			Item item = repo.findByCode(request.getParameter("item-code"));
			
			request.setAttribute("data_item", item);
			fileJsp.append("WEB-INF/jsp/update-item.jsp");
		}
		
		request.getRequestDispatcher(fileJsp.toString()).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		ItemRepository repo = new ItemRepository();
		
		Item item = new Item(request.getParameter("item_code"), Integer.parseInt(request.getParameter("price")), request.getParameter("description"), request.getParameter("type"), Boolean.valueOf(request.getParameter("taxable")));
		
		if("create".equals(action)){
			repo.insertItem(item);
		}
		else if("update".equals(action)) {
			repo.updateItem(item);
		}
		response.sendRedirect("item.do?action=list");
	}

}
