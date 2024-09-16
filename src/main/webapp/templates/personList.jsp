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

    <c:import url="nav.jsp" />

	<div class="container">
		<h3>Sample Data Manager</h3>
		<p>Add / delete entries below and view the first 10 results.</p>

        <c:import url="addPerson.jsp">
            <c:param name="person" value="${person}" />
        </c:import>

        <c:import url="deletePerson.jsp" />

	</div>

    <c:import url="personTable.jsp">
        <c:param name="personsList" value="${personsList}" />
    </c:import>

</body>
</html>
