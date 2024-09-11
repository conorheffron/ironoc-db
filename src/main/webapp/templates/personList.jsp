<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet"
	href='<c:url value="/resources/style/main.css" />'>
<title>iRonoc - Sample Data Manager</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"
	type="text/javascript"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	type="text/javascript"></script>
</head>
<body>

	<nav id="navbar" class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/">iRonoc</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="/">Home</a></li>
			</ul>
		</div>
	</nav>

	<div class="container">
		<h3>Sample Data Manager</h3>
		<p>Add / delete entries below and view the first 10 results.</p>

		<form:form action="add" modelAttribute="person" class=".form-control">
			<form:errors path="title" cssClass="error"></form:errors>
			<form:input type="text" path="title" style="width:50%"
				placeholder="Enter title....." />
			<br />
			<form:errors path="firstName" cssClass="error"></form:errors>
			<form:input type="text" path="firstName" style="width:50%"
				placeholder="Enter First Name....." />
			<br />
			<form:errors path="surname" cssClass="error"></form:errors>
			<form:input type="text" path="surname" style="width:50%"
				placeholder="Enter Surname....." />
			<br />
			<form:errors path="age" cssClass="error"></form:errors>
			<form:input type="number" path="age" style="width:50%"
				placeholder="Enter Age....." />
			<br />
			<input type="submit" value="Add Person" class="btn btn-default" />
		</form:form>

		<form action="delete" method="GET" class=".form-control">
			<h5 class="error">${deleteError}</h5>
			<input type="text" name="surname" style="width: 50%"
				placeholder="Enter Surname....."><br /> <input
				type="submit" value="Delete Person" class="btn btn-default" />
		</form>
	</div>

	<c:if test="${!empty personsList}">
		<div class="container">
			<table class="table table-hover">
				<tr>
					<th>Title</th>
					<th>First Name</th>
					<th>Surname</th>
					<th>Age</th>
				</tr>
				<c:forEach items="${personsList}" var="person">
					<tr>
						<td>${person.title}</td>
						<td>${person.firstName}</td>
						<td>${person.surname}</td>
						<td>${person.age}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>

</body>
</html>
