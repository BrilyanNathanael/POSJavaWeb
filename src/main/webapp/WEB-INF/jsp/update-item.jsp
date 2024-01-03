<%@page import="com.wide.sale.domain.Item"%>
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
	<h1>Update Item</h1>
	<form method="POST" action="item.do">
		<input type="hidden" value="update" name="action"/>
		<input type="hidden" value='<c:out value="${data_item.itemCode}"></c:out>' name="item_code"/>
		<label>Item Code: </label><br/>
		<input type="text" name="item_code" value='<c:out value="${data_item.itemCode}"></c:out>' disabled /><br/>
		<label>Price: </label><br/>
		<input type="number" name="price" value='<c:out value="${data_item.price}"></c:out>'/><br/>
		<label>Description: </label><br/>
		<input type="text" name="description" value='<c:out value="${data_item.description}"></c:out>' /><br/>
		<label>Type: </label><br/>
		<input type="text" name="type" value='<c:out value="${data_item.type}"></c:out>' /><br/>
		<label>Taxable: </label><br/>
		<c:choose>
			<c:when test="${data_item.taxable == 'true'}">
				<input type="radio" id="yes" name="taxable" value="true" checked>
				<label for="yes">Yes</label><br>
				<input type="radio" id="no" name="taxable" value="false">
				<label for="no">No</label><br>
			</c:when>
			<c:when test="${data_item.taxable == 'false'}">
				<input type="radio" id="yes" name="taxable" value="true">
				<label for="yes">Yes</label><br>
				<input type="radio" id="no" name="taxable" value="false" checked>
				<label for="no">No</label><br>
			</c:when>
		</c:choose>
		<br/>
		<input type="submit" value="update"/>
	</form>
</body>
</html>