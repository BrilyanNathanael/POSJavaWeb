<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="com.wide.sale.domain.Item"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="com.wide.sale.domain.Database"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Item</title>
</head>
<body>
	<c:choose>
		<c:when test="${userContext == null}">
			<h1>Welcome user</h1>
			<ul><li><a href="home.action">Login</a></li></ul>
		</c:when>
		<c:otherwise>
			<h1>Welcome ${userContext}</h1>
			<ul>
				<li><a href="sale.do">Checkout</a></li>
				<li><a href="sale.do?action=cart">My Cart</a></li>
				<li><a href="sale.do?action=transactions">My Transactions</a></li>
				<li>
					<form method="POST" action="home.action">
						<button type="submit">Logout</button>
					</form>
				</li>
			</ul>	
		</c:otherwise>
	</c:choose>
</body>
</html>