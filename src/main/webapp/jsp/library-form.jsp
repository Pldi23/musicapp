<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<%--<head>--%>
<%--    <title><fmt:message key="label.library"/></title>--%>
<%--</head>--%>
<body>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-library">
    <input type="submit" name="submit" value="<fmt:message key="button.library"/>">
</form>
</body>
</html>
