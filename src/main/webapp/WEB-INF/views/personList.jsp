<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet"
	href='<c:url value="/resources/style/main.css" />'>
<title>iRonoc - Person Data Manager</title>

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
				<a class="navbar-brand" href="http://www.ironoc.com">iRonoc</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="/">Home</a></li>
			</ul>
		</div>
	</nav>

	<div class="container">
		<h3>iRonoc - Person Data Manager</h3>
		<p>Add / delete entries below</p>
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

	<br />

	<div class="container">
		<form:form action="add" modelAttribute="person" class="form-inline">

			<div class="form-group">
				<form:errors path="title" cssClass="error"></form:errors>
				Title:
				<form:input type="text" path="title" />
			</div>

			<div class="form-group">
				<form:errors path="firstName" cssClass="error"></form:errors>
				First Name:
				<form:input type="text" path="firstName" />
			</div>

			<div class="form-group">
				<form:errors path="surname" cssClass="error"></form:errors>
				Last Name:
				<form:input type="text" path="surname" />
			</div>

			<div class="form-group">
				<form:errors path="age" cssClass="error"></form:errors>
				Age:
				<form:input type="text" path="age" />
			</div>

			<input type="submit" value="Add Person" class="btn btn-default" />

		</form:form>

	</div>

	<br />

	<div class="container">
		<form action="delete" method="GET" class="form-inline">
			<div class="form-group">
				<h4 class="error">${deleteError}</h4>
				Last Name: <input type="text" name="surname">
			</div>
			<input type="submit" value="Delete Person" class="btn btn-default" />
		</form>
	</div>

</body>
</html>