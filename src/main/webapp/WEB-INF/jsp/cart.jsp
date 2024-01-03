<%@page import="java.util.List"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.Out"%>
<%@page import="com.wide.sale.domain.SaleItem"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cart</title>
<script>
	function changePayment(){
		var typePayment = document.getElementById('typePayment');
		var colPayment = document.getElementById('colPayment');
		var totalPayment = document.getElementById('totalPayment');
		if(typePayment.value == 'cash'){
			colPayment.innerHTML = '<input type="text" style="width:100%;" placeholder="Please input amount" name="inputPayment"/>'
		}else{
			colPayment.innerHTML = '<input type="text" style="width:100%;" placeholder="Please input amount" value="' + totalPayment.innerText + '" name="inputPayment" disabled/>'
		}
	}
</script>

</head>
<body>
	<h1>.: My Cart :.</h1>
	<form type="submit" action="sale.do" method="POST">
	<table border="2">
		<tr>
			<th>No</th>
			<th>Item Code</th>
			<th>Description</th>
			<th>Type</th>
			<th>Quantity</th>
			<th>Price</th>
			<th>Sub Price</th>
		</tr>
		<c:if test="${cart.size() > 0}">
			<c:forEach items="${cart}" var="item" varStatus="loop">
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
				<td>${grandPrice}</td>
			</tr>
			<tr>
				<td colspan="6"><b>Tax</b></td>
				<td>${tax}</td>
			</tr>
			<tr>
				<td colspan="6"><b>Total Grand Price + Tax</b></td>
				<td id="totalPayment">${grandPrice + tax}</td>
			</tr>
			<tr><td colspan="7"></td></tr>
			<tr>
				<td>Type Payment</td>
				<td>
					<select id="typePayment" onchange="changePayment()" name="typePayment">
						<option value="cash">Cash</option>
						<option value="qris">QRis</option>
					</select>
				</td>
				<td id="colPayment" colspan="5" style="padding-right: 10px;">
					<input type="text" style="width:100%;" placeholder="Please input amount" name="inputPayment"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
				<center>
				<a href="sale.do" style="width:100%;cursor:pointer;display:inline-block;text-decoration:none;">Add more item</a>				
				</center>
				</td>
				<td colspan="4">
					<input type="hidden" name="action" value="payment"/>
					<button style="width:100%;cursor:pointer;">Pay</button>
				</td>
			</tr>
		</c:if>
		<c:if test="${cart == null}">
			<tr>
				<td colspan="7">There is nothing in here...</td>
			</tr>
			<tr>
				<td colspan="7">
				<center>
				<a href="sale.do" style="width:100%;cursor:pointer;display:inline-block;text-decoration:none;">Add item to cart</a>				
				</center>
				</td>
			</tr>
		</c:if>
	</table>
	</form>
</body>
</html>