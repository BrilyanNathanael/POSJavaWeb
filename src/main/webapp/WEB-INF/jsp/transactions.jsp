<%@page import="com.wide.sale.domain.CashPayment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Transactions</title>
</head>
<body>
	<h1>.: My Transactions :.</h1>
	<c:if test="${listSales.size() > 0}">
	<c:forEach items="${listSales}" var="sale">
	<table border="2">
		<tr>
			<th colspan="7">Sale Number #${sale.saleNumber}</th>
		</tr>
		<tr>
			<th colspan="7">${sale.transDate}</th>		
		</tr>
		<tr>
			<th>No</th>
			<th>Item Code</th>
			<th>Description</th>
			<th>Type</th>
			<th>Quantity</th>
			<th>Price</th>
			<th>Sub Price</th>
		</tr>
		<c:forEach items="${sale.getSaleItems()}" var="item" varStatus="loop">
		<tr>
			<td>${loop.index + 1}</td>
			<td>${item.item.itemCode}</td>		
			<td>${item.item.description}</td>
			<td>${item.item.type}</td>
			<td>${item.quantity}</td>
			<td>${item.price}</td>
			<td>${item.quantity * item.price}</td>
		</tr>	
		</c:forEach>
		<tr>
			<td colspan="6"><b>Total Grand Price</b></td>
			<td><b>${sale.payment.amount}</b></td>
		</tr>
		<tr>
			<td colspan="6"><b>Tax</b></td>
			<td><b>${sale.tax}</b></td>
		</tr>
		<tr>
			<td colspan="6"><b>Total Grand Price + Tax</b></td>
			<td><b>${sale.payment.amount + sale.tax}</b></td>
		</tr>
		<tr><td colspan="7"></td></tr>
		<c:choose>
			<c:when test="${sale.payment['class'].simpleName == 'CashPayment'}">
				<tr>
					<td colspan="6"><b>Payment (Cash)</b></td>
					<td><b>${sale.payment.cashInHand}</b></td>
				</tr>
				<tr>
					<td colspan="6"><b>Change</b></td>
					<td><b>${sale.payment.cashInHand - (sale.payment.amount + sale.tax)}</b></td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="6"><b>Payment Type (Qris)</b></td>
					<td><b>${sale.payment.amount + sale.tax}</b></td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<br/>
	</c:forEach>
	</c:if>
	<a href="./">Kembali</a>
</body>
</html>