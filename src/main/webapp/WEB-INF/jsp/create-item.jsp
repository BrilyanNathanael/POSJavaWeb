<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Item</title>
</head>
<body>
	<h1>Create New Item</h1>
	<form method="POST" action="item.do">
		<input type="hidden" value="create" name="action"/>
		<label>Item Code: </label><br/>
		<input type="text" name="item_code" /><br/>
		<label>Price: </label><br/>
		<input type="number" name="price" /><br/>
		<label>Description: </label><br/>
		<input type="text" name="description" /><br/>
		<label>Type: </label><br/>
		<input type="text" name="type" /><br/>
		<label>Taxable: </label><br/>
		<input type="radio" id="yes" name="taxable" value="true" checked>
		<label for="yes">Yes</label><br>
		<input type="radio" id="no" name="taxable" value="false">
		<label for="no">No</label><br>
		<br/>
		<input type="submit" value="create"/>
	</form>
</body>
</html>