<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<%--<head>--%>
<%--    <title>Header</title>--%>
<%--</head>--%>
<body>
<c:if test="${ empty user}">
    <fmt:message key="label.hello"/>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="to-login">
        <input type="submit" name="submit" value="<fmt:message key="button.tologin"/>">
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="to-registr"/>
        <input type="submit" name="submit" value="<fmt:message key="button.registration"/> ">
    </form>
    <c:import url="search-form.jsp"/>
</c:if>
<c:if test="${ user.admin eq true}">
    ${ user.firstname }, <fmt:message key="role.admin"/>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="logout">
        <input type="submit" name="submit" value="<fmt:message key="button.logout"/>">
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="to-profile">
        <input type="submit" name="submit" value="<fmt:message key="button.profile"/>">
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="to-user-library">
        <input type="submit" name="submit" value="<fmt:message key="button.users.db"/>">
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="to-library">
        <input type="submit" name="submit" value="<fmt:message key="button.library"/>">
    </form>
</c:if>
<c:if test="${ user.admin eq false}">
    ${ user.firstname }, <fmt:message key="role.user"/>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="logout">
        <input type="submit" name="submit" value="<fmt:message key="button.logout"/>">
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="to-profile">
        <input type="submit" name="submit" value="<fmt:message key="button.profile"/>">
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="user-playlists">
        <input type="submit" name="submit" value="<fmt:message key="button.playlists.my"/>">
    </form>
</c:if>
</body>
</html>
