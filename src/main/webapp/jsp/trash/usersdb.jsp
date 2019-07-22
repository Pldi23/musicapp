<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/usersdb.jsp" scope="request"/>
<html>
<head>
    <title><fmt:message key="label.users"/> </title>
</head>
<body>
<c:import url="../common/header.jsp"/>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-create-admin">
    <input type="submit" name="submit" value="<fmt:message key="button.admin.new"/>">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="show-user">
    <input type="submit" name="submit" value="<fmt:message key="button.users"/> ">
</form>
</body>
</html>
