<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session"/>
<fmt:setBundle basename="pagecontent" />
<html><head><title><fmt:message key="label.track.remove.confirmation"/></title></head>
<body>
<hr/>
<fmt:message key="message.remove.confirm"/> '${ entity.name }' <fmt:message key="message.database.remove"/>
<form action="controller" method="get">
    <input type="hidden" name="command" value="remove">
    <input type="hidden" name="id" value="${ entity.id }">
    <input type="submit" name="submit" value="<fmt:message key="button.finally.remove"/> ">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-library">
    <input type="submit" name="submit" value="<fmt:message key="button.cancel"/>">
</form>
<br>
<hr/>
</body>
</html>