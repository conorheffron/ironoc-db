<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Hibernate Spring Person Table View</title>
    </head>
    <body>

    <h3>Person Data Manager</h3>
    
    <c:if  test="${!empty personsList}">
    <table class="data">
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
    </c:if>
    
    <br />
    
    <form:form action="add" modelAttribute="person">
	    Title: <form:input type="text" path="title"/><br />
		First Name: <form:input type="text" path="firstName"/><br />
		Last Name: <form:input type="text" path="surname" /><br />
		Age: <form:input type="text" path="age" /><br />
		<input type="submit" value="Add Person" />
	</form:form>
	
	<br />
	
	<form:form action="delete" modelAttribute="person">
		Last Name: <form:input type="text" path="surname" /><br />
		<input type="submit" value="Delete Person" />
	</form:form>
    
    </body>
</html>