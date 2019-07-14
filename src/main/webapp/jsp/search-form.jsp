<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.search"/></title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name="command" value="search"/>
    <label>
        <fmt:message key="label.search"/>:
        <input type="text" name="searchrequest" placeholder="<fmt:message key="placeholder.search"/>"/>
    </label>
    <input type="hidden" name="offset" value="0">
    <input type="submit" name="submit" value="<fmt:message key="button.search"/>">
</form>
</body>
</html>
