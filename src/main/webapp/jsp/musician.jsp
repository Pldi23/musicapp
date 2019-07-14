<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/musician.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title>${ musician.name }</title>
</head>
<body>
<%--<c:import url="locale-form.jsp"/>--%>
<c:import url="header.jsp"/>
<c:out value="${ musician.name }"/>
<table>
    <c:forEach var="track" items="${ entities }" varStatus="status">
        <tr>
            <td><c:out value="${ track.name }"/></td>
            <td><audio controls><source src="music/${track.uuid}" type="audio/mpeg"></audio></td>
            <td><form action="controller" method="get">
                <input type="hidden" name="command" value="track-detail">
                <input type="hidden" name="id" value="${ track.id }">
                <input type="submit" name="submit" value="<fmt:message key="button.details"/>">
            </form></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
