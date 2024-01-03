<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<h1>.: Login :.</h1>
	<form action="home.action" method="POST">
		<label for="username">Username: </label><br/>
		<input type="text" id="username" name="username"/><br/>
		<label for="password">Password: </label><br/>
		<input type="password" id="password" name="password"/><br/><br/>
		<button type="submit">Login</button>
	</form>
</body>
</html>