<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Hibernate Spring Person Table View</title>
    </head>
    <body>

    <h3>Persons</h3>
    
    <c:if  test="${!empty personsList}">
    <table class="data">
    <tr>
        <th>Title</th>
    </tr>
    <c:forEach items="${personsList}" var="person">
        <tr>
            <td>${person.title}</td>
        </tr>
    </c:forEach>
    </table>
    </c:if>
    
    
    </body>
</html>