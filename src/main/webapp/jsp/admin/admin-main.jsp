<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/admin-main.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.welcome"/></title></head>
<body>
<c:import url="../locale-form.jsp"/>
<c:import url="../header.jsp"/>
<h3><fmt:message key="label.welcome"/> </h3>
<hr/>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-upload-track">
    <input type="submit" name="submit" value="<fmt:message key="button.upload.track"/>">
</form>
<c:import url="../library-form.jsp"/>
</body>
</html>

<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="to-library">--%>
<%--    <input type="submit" name="submit" value="<fmt:message key="button.library"/>">--%>
<%--</form>--%>

<%--<form action="controller" method="post">--%>
<%--    <input type="hidden" name="command" value="query"/>--%>
<%--    <h3>Remove track</h3>--%>
<%--    <label>--%>
<%--        query track for removing :--%>
<%--        <input type="text" name="searchrequest" required="" placeholder="track name"/>--%>
<%--    </label>--%>
<%--    <input type="submit" name="submit" value="query">--%>
<%--</form>--%>
<%--<br>--%>
<%--<table>--%>
<%--    <c:if test="${not empty tracks}">--%>
<%--        <c:out value="Tracks"/>--%>
<%--    </c:if>--%>
<%--    <c:forEach var="track" items="${tracks}" varStatus="status">--%>
<%--        <tr>--%>
<%--            <td><c:out value="${ track.id }"/></td>--%>
<%--            <td><c:out value="${ track.name }"/></td>--%>
<%--            <td><c:out value="${ track.singers }"/></td>--%>
<%--            <td><c:out value="${ track.genre }"/></td>--%>
<%--            <td>--%>
<%--                <audio controls>--%>
<%--                    <source src="music/${track.uuid}" type="audio/mpeg">--%>
<%--                </audio>--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <form method="get" action="controller">--%>
<%--                    <input type="hidden" name="command" value="areyousure">--%>
<%--                    <input type="hidden" name="uuid" value="${ track.uuid }">--%>
<%--                    <input type="hidden" name="name" value="${ track.name }">--%>
<%--                    <input type="submit" name="submit" value="remove">--%>
<%--                </form>--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <form method="get" action="controller">--%>
<%--                    <input type="hidden" name="command" value="toupdatetrack" >--%>
<%--                    <input type="hidden" name="uuid" value="${ track.uuid }">--%>
<%--                    <input type="hidden" name="name" value="${ track.name }">--%>
<%--                    <input type="submit" name="submit" value="update">--%>
<%--                </form>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--</table>--%>
<%--${removeResult}--%>
<%--${updateResult}--%>
