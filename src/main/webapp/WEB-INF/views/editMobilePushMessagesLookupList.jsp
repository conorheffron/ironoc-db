<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Hibernate Spring Mobile Push Messages Lookup Table Manager</title>
    </head>
    <body>
    <h2>Push Messages Lookup Table Management Screen</h2>
    <form:form method="post" action="add" commandName="mobilePushMessagesLookup">
        <table>
        <tr>
            <td><form:label path="isDevTest"><spring:message code="label.is_dev_test"/></form:label></td>
            <td><form:input path="isDevTest" /></td>
        </tr>
        <tr>
            <td><form:label path="title"><spring:message code="label.title"/></form:label></td>
            <td><form:input path="title" /></td>
        </tr>
        <tr>
            <td><form:label path="content"><spring:message code="label.content"/></form:label></td>
            <td><form:input path="content" /></td>
        </tr>
        <tr>
            <td><form:label path="url"><spring:message code="label.url"/></form:label></td>
            <td><form:input path="url" /></td>
        </tr>
        <tr>
            <td><form:label path="omnitureCampaignId"><spring:message code="label.omniture_campaign_id"/></form:label></td>
            <td><form:input path="omnitureCampaignId" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<spring:message code="label.push.msg.add"/>"/>
            </td>
        </tr>
    </table> 
    </form:form>
    
    
    <h3>Mobile Push Messages Lookups</h3>
    
    <c:if  test="${!empty mobilePushMessagesLookupList}">
    <table class="data">
    <tr>
        <th>Dev Message?</th>
        <th>Title</th>
        <th>Content</th>
        <th>URL</th>
        <th>Omniture Campaign ID</th>
    </tr>
    <c:forEach items="${mobilePushMessagesLookupList}" var="push">
        <tr>
            <td>${push.isDevTest}</td>
            <td>${push.title}</td>
            <td>${push.content}</td>
            <td>${push.url}</td>
            <td>${push.omnitureCampaignId}</td>
        </tr>
    </c:forEach>
    </table>
    </c:if>
    
    
    </body>
</html>