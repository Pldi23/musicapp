<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/user/create-playlist.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.playlist.create"/></title>
</head>
<body>
<c:import url="../common/header.jsp"/>
<c:out value="${ requestScope.process }"/>
<p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
<form action="<c:url value="/controller"/>" method="post">
    <input type="hidden" name="command" value="playlist-create">
    <input type="text" name="name" minlength="2" maxlength="50" required="required">
    <input type="submit" name="submit" value="<fmt:message key="button.playlist.create"/> ">
</form>
</body>
</html>
