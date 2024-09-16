<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="delete" method="GET" class=".form-control">
    <h5 class="error">${deleteError}</h5>
    <input type="text" name="surname" style="width: 50%"
        placeholder="Enter Surname....."><br /> <input
        type="submit" value="Delete Person" class="btn btn-default" />
</form>
