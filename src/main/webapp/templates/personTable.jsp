<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
