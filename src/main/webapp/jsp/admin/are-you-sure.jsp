<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Are you sure?</title></head>
<body>
<hr/>
Are you sure you want to delete '${ entity.name }' from storage and database?
<form action="controller" method="get">
    <input type="hidden" name="command" value="remove">
<%--    <input type="hidden" name="uuid" value="${ entity.uuid }">--%>
    <input type="hidden" name="id" value="${ entity.id }">
    <input type="submit" name="submit" value="finally remove">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-library">
    <input type="submit" name="submit" value="cancel">
</form>
<br>
<hr/>
</body>
</html>