<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="/jsp/create-playlist.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.playlist.create"/></title>
</head>
<body>
<c:import url="header.jsp"/>
${ process }
<form action="controller" method="get">
    <input type="hidden" name="command" value="playlist-create">
    <input type="text" name="name" minlength="2" maxlength="50" required="required">
    <input type="submit" name="submit" value="<fmt:message key="button.playlist.create"/> ">
</form>
</body>
</html>
