<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<!DOCTYPE html>
<html>
<head>
    <title>Header</title>
</head>
<body>
${ user.firstname }, <c:choose><c:when test="${ user.admin eq true}"><fmt:message key="role.admin"/></c:when><c:otherwise><fmt:message key="role.user"/></c:otherwise></c:choose>
<form action="controller" method="post">
    <input type="hidden" name="command" value="search"/>
    <label>
        <fmt:message key="label.search"/>:
        <input type="text" name="searchrequest" placeholder="<fmt:message key="placeholder.search"/>"/>
    </label>
    <input type="submit" name="submit" value="<fmt:message key="button.search"/>">
</form>
<form action="controller" method="post">
    <input type="hidden" name="command" value="to-profile">
    <input type="submit" name="submit" value="<fmt:message key="button.profile"/>">
</form>
<form action="controller" method="post">
    <input type="hidden" name="command" value="logout">
    <input type="submit" name="submit" value="<fmt:message key="button.logout"/>">
</form>
</body>
</html>