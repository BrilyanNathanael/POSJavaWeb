<%@page import="com.wide.sale.domain.Sale"%>
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
<title>Checkout</title>
<script type="text/javascript">
	
	function setSearch(){
		var formSale = document.getElementById('formSale');
		var action = document.getElementById('action');
		action.value = "search";
		formSale.submit();
	}
	
	function setCart(){
		var formSale = document.getElementById('formSale');
		var action = document.getElementById('action');
		action.value = "cart";
		formSale.submit();
	}
	
</script>
</head>
<body>
	<h1><a href="./item.do?action=list" style="text-decoration:none;color:black;">.: Add your item :.</a></h1>
	<br/>
	<c:if test="${data_item.itemCode != ''}">
		<h3>Detail Item</h3>
		<p>Item Code: <c:out value="${data_item.itemCode}"></c:out></p>
		<p>Item Price: <c:out value="${data_item.price}"></c:out></p>
		<p>Item Description: <c:out value="${data_item.description}"></c:out></p>
		<p>Item Type: <c:out value="${data_item.type}"></c:out></p>
	</c:if>
	<br/>
	
	<form method="POST" action="sale.do" id="formSale">
		<input type="hidden" name="action" value="search" id="action" />
		<table>
			<tr>
				<td><label id="item_code">Item Code: </label></td>
				<td>
					<select name="item_code" style="width:100%;">
						<c:forEach items="${listId}" var="item">
							<c:if test="${item.itemCode == data_item.itemCode}">
								<option value="${item.itemCode}" selected>${item.itemCode}</option>
							</c:if>
							<c:if test="${item.itemCode != data_item.itemCode}">
								<option value="${item.itemCode}">${item.itemCode}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<td><button type="button" id="search" onclick="setSearch()">Search</button></td>
			</tr>
			<tr>
				<td><label id="quantity">Quantity: </label></td>
				<td><input type="number" for="quantity" name="quantity" min="1" value="1"/></td>
			</tr>
			<tr>
				<td><a href="sale.do?action=cart" style="text-decoration:none;border:1px solid gray;padding:2px;">View my cart</a></td>
				<td><button type="button" id="cart" onclick="setCart()">Add to cart</button></td>
			</tr>
			<tr><td><a href="./">Kembali</a></td></tr>
		</table>
	</form>
</body>
</html>