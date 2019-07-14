<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/playlist.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${ playlist.name }</title>
</head>
<body>
<c:import url="header.jsp"/>
<c:out value="${ playlist.name }"/>
<br>
<c:if test="${ not empty playlist.tracks }"><fmt:message key="label.tracks"/></c:if>
<table>
    <c:forEach var="track" items="${ playlist.tarcks }" varStatus="status">
        <tr>
            <td>
                <audio controls>
                    <source src="music/${track.uuid}" type="audio/mpeg">
                </audio>
            </td>
            <td><c:out value="${ track.name }"/></td>
            <td><c:out value="${ track.genre.title }"/></td>
            <td><c:out value="${ track.releaseDate }"/></td>
            <td><c:forEach var="singer" items="track.singers">
                <c:out value="${ singer.name }"/>
            </c:forEach></td>
            <td><c:forEach var="author" items="track.authors">
                <c:out value="${ author.name }"/>
            </c:forEach></td>
            <td>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="track-detail">
                    <input type="hidden" name="id" value="${ track.id }">
                    <input type="submit" name="submit" value="<fmt:message key="button.details"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
