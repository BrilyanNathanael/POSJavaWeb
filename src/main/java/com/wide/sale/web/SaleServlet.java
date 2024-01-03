package com.wide.sale.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wide.sale.domain.CashPayment;
import com.wide.sale.domain.Cashier;
import com.wide.sale.domain.Item;
import com.wide.sale.domain.Payment;
import com.wide.sale.domain.QrisPayment;
import com.wide.sale.domain.Sale;
import com.wide.sale.domain.SaleItem;
import com.wide.sale.domain.Tax;
import com.wide.sale.repository.ItemRepository;
import com.wide.sale.repository.SalesRepository;

/**
 * Servlet implementation class SaleServlet
 */
@WebServlet("/sale.do")
public class SaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if("cart".equals(action)) {
			request.getRequestDispatcher("WEB-INF/jsp/cart.jsp").forward(request, response);			
		}
		else if("transactions".equals(action)) {
			SalesRepository salesRepository = new SalesRepository();
			List<Sale> listSales = salesRepository.findAll();
			
			request.setAttribute("listSales", listSales);
			request.getRequestDispatcher("WEB-INF/jsp/transactions.jsp").forward(request, response);
		}
		else {
			ItemRepository itemRepository = new ItemRepository();
			List<Item> listItem = itemRepository.findAll();
			
			request.setAttribute("listId", listItem);
			
			request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		ItemRepository itemRepository = new ItemRepository();
		HttpSession session = request.getSession();
		
		Item item = itemRepository.findByCode(request.getParameter("item_code"));
		
		if("search".equals(action)) {
			List<Item> listItem = itemRepository.findAll();
			
			request.setAttribute("listId", listItem);
			
			request.setAttribute("data_item", item);
			request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp").forward(request, response);
		}
		else if("cart".equals(action)) {
			List<SaleItem> listSalesItem;
			
			if(session.getAttribute("cart") != null) {
				listSalesItem = (List<SaleItem>) session.getAttribute("cart");
				boolean quantityItem = quantityItem(listSalesItem, request);
				
				if(!quantityItem) {
					SaleItem si = new SaleItem(item, Integer.parseInt(request.getParameter("quantity")));
					listSalesItem.add(si);
				}
			}
			else {					
				listSalesItem = new ArrayList<SaleItem>();
				SaleItem si = new SaleItem(item, Integer.parseInt(request.getParameter("quantity")));
				listSalesItem.add(si);
			}
			
			
			int itemTax = calculateTax(listSalesItem);
			int totalGrandPrice = totalGrandPrice(listSalesItem);
			
			session.setAttribute("cart", listSalesItem);
			session.setAttribute("tax", itemTax);
			session.setAttribute("grandPrice", totalGrandPrice);
			
			response.sendRedirect("sale.do?action=cart");
		}
		else if("payment".equals(action)) {
			SalesRepository saleRepository = new SalesRepository();
			Sale sl = new Sale(new Date().toString(), new Cashier("Nael"));
			
			String typePayment = request.getParameter("typePayment");
			Payment p;
			
			Integer grandPrice = (Integer) session.getAttribute("grandPrice");
			Integer tax = (Integer) session.getAttribute("tax");
			List<SaleItem> listSaleItems = (List<SaleItem>) session.getAttribute("cart");
			if("cash".equals(typePayment)) {
				p = new CashPayment(grandPrice);
				p.setCashInHand(Integer.parseInt(request.getParameter("inputPayment")));
			}
			else {
				p = new QrisPayment(grandPrice);
			}
			
			sl.setPayment(p);
			sl.setSaleItems(listSaleItems);
			sl.setTax(tax);
			saleRepository.save(sl);
			session.removeAttribute("cart");
			session.removeAttribute("grandPrice");
			session.removeAttribute("tax");
			response.sendRedirect("sale.do");
		}
	}
	
	private int calculateTax(List<SaleItem> listSalesItem) {
		int itemTax = 0;
		for(SaleItem s:listSalesItem) {
			if(s.getItem().isTaxable()) {
				itemTax = itemTax + ((int) (s.totalPrice() * Tax.tax));
			}
		}
		
		return itemTax;
	}
	
	private int totalGrandPrice(List<SaleItem> listSalesItem) {
		int totalPrice = 0;
		for(SaleItem s:listSalesItem) {
			totalPrice = totalPrice + s.totalPrice();
		}
		
		return totalPrice;
	}
	
	private boolean quantityItem(List<SaleItem> listSalesItem, HttpServletRequest request) {
		for(SaleItem s:listSalesItem) {
			if(request.getParameter("item_code").equals(s.getItem().getItemCode())) {
				s.setQuantity(s.getQuantity() + Integer.parseInt(request.getParameter("quantity")));
				return true;
			}
		}
		return false;
	}

}
