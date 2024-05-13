<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
<link href="style.css" rel="Stylesheet">
</head>
<body>
	<div class="container">
		<h2 id="title">Create your account</h2>
		<%
		Boolean insert = (Boolean) request.getAttribute("inserted");
		if (insert != null && insert == true) {
		%>
		<p class="text-success">Registered Successfully!!</p>
		<%
		}
		%>
		<%
		Boolean login = (Boolean) request.getAttribute("Login");
		if (login != null && login) {
		%>
		<p class="text-danger">You don't have an account. Please
			register!!</p>
		<%
		}
		%>
		<form action="SignUpServlet" method="post">
			<label>Enter Username: </label> <input type="text" name="uname"
				id="username"> <br> <label>Enter Password: </label> <input
				type="password" name="password" id="password"> <br> <label>Confirm
				Password: </label> <input type="password" name="ConfirmPassword"
				id="cnfpassword">
			<%
			String password = request.getParameter("password");
			String cnf = request.getParameter("ConfirmPassword");
			if (password != null && cnf != null && !password.equals(cnf)) {
			%>
			<p class="text-danger">Passwords don't match</p>
			<%
			}
			%>
			<br>
			<button type="submit" id="submit">Register</button>
			<a href="index.html" id="Link">Login</a>


		</form>
	</div>
</body>
</html>
