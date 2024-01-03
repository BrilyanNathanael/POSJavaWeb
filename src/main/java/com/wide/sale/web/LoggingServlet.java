package com.wide.sale.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoggingServlet
 */
@WebServlet("/home.action")
public class LoggingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoggingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userContext") == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("index.jsp").forward(request, response);			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userContext") == null) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			RequestDispatcher dispatcher = null;
			if("Brilyan".equals(username) && "password".equals(password)) {
				session.setAttribute("userContext", username);
				dispatcher = request.getRequestDispatcher("index.jsp");
			}
			else {
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);			
		}
		else {
			session.removeAttribute("userContext");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
