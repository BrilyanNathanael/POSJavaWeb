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
	<h1>Item List</h1>
	<a href='?action=create'>New Item</a>
	<table border='10' width='70%'>
		<tr>
			<th>Item Code</th>
			<th>Description</th>
			<th>Price</th>
			<th>Type</th>
			<th>Action</th>
		</tr>
		
		<c:forEach items="${data_item}" var="item">
			<tr>
				<td>
					<c:out value="${item.itemCode}"></c:out>
				</td>
				<td>
					<c:out value="${item.description}"></c:out>
				</td>
				<td>
					<c:out value="${item.price}"></c:out>
				</td>
				<td>
					<c:out value="${item.type}"></c:out>
				</td>
				<td>
					<a href="item.do?item-code=${item.itemCode}&action=update">Update</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>