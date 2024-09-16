<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
