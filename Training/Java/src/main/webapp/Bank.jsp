<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="style.css" rel="Stylesheet">
</head>
<body>
	<div class="container" id="mainContainer">
		<h1 id="title">Welcome</h1>
		<button onclick="showContainer('create')" id="option">Create Account</button>
		<button onclick="showContainer('delete')" id="option">Delete Account</button>
		<button onclick="showContainer('deposit')" id="option">Deposit</button>
		<button onclick="showContainer('withdraw')" id="option">Withdraw</button>
	</div>

	<div class="container" id="create" style="display: none;">
		<form action="InfoServlet" method="post">
			<label>ID:</label> 
			<input type="number" id="id" name="id" required><br>
			<label>Balance:</label> 
			<input type="number" id="balance" name="balance" required><br>
			<input type="submit" value="Create Account" id="submit">
			<a href="Bank.jsp" id="Link">Go Back</a>
		</form>
	</div>

	<div class="container" id="delete" style="display: none;">
		<form action="DeleteServlet" method="post">
			<label>Name:</label> 
			<input type="text" id="name" name="name" required><br>
			<input type="submit" value="Delete Account" id="submit">
			<a href="Bank.jsp" id="Link">Go Back</a>
		</form>
	</div>

	<div class="container" id="deposit" style="display: none;">
		<form action="DepositServlet" method="post">
			<label>Deposit:</label> 
			<input type="number" id="balance" name="balance" required><br>
			<input type="submit" value="Deposit" id="submit">
			<a href="Bank.jsp" id="Link">Go Back</a>
		</form>
	</div>

	<div class="container" id="withdraw" style="display: none;">
		<form action="WithdrawServlet" method="post">
			<label>Name:</label> 
			<input type="text" id="name" name="name" required><br>
			<label>Withdraw:</label> 
			<input type="number" id="balance" name="balance" required><br>
			<input type="submit" value="Withdraw" id="submit">
			<a href="Bank.jsp" id="Link">Go Back</a>
		</form>
	</div>

	<script>
		function showContainer(option) {
			var containers = document.getElementsByClassName("container");
			for (var i = 0; i < containers.length; i++) {
				containers[i].style.display = "none";
			}
			document.getElementById(option).style.display = "block";
		}
	</script>
</body>
</html>