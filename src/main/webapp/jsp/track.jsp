<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/track.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title>${ track.name }</title>
</head>
<body>
<%--<c:import url="locale-form.jsp"/>--%>
<c:import url="header.jsp"/>
<c:out value="${ track.name }"/>
<br>
<c:out value="${ track.genre.title }"/>
<br>
<c:out value="${ track.releaseDate }"/>
<br>
<audio controls><source src="music/${ track.uuid }" type="audio/mpeg"></audio>
<br>
<%--mb foto--%>
<c:if test="${ not empty track.singers }"><fmt:message key="label.singers"/></c:if>
<table>
    <c:forEach var="singer" items="${ track.singers }" varStatus="status">
        <tr>
            <td><c:out value="${ singer.name }"/></td>
            <td><form action="controller" method="get">
                <input type="hidden" name="command" value="musician-detail">
                <input type="hidden" name="id" value="${ singer.id }">
                <input type="submit" name="submit" value="<fmt:message key="button.details"/>">
            </form></td>
        </tr>
    </c:forEach>
</table>
<c:if test="${ not empty track.authors }"><fmt:message key="label.authors"/></c:if>
<table>
    <c:forEach var="author" items="${ track.authors }" varStatus="status">
        <tr>
            <td><c:out value="${ author.name }"/></td>
            <td><form action="controller" method="get">
                <input type="hidden" name="command" value="musician-detail">
                <input type="hidden" name="id" value="${ author.id }">
                <input type="submit" name="submit" value="<fmt:message key="button.details"/>">
            </form></td>
        </tr>
    </c:forEach>
</table>
<c:if test="${ not empty playlists }"><fmt:message key="label.playlists"/></c:if>
<table>
    <c:forEach var="playlist" items="${ playlists }" varStatus="status">
        <tr>
            <td><c:out value="${ playlist.name }"/></td>
<%--            <td><c:out value="${ size }"/></td>--%>
<%--            <td><c:out value="${ summary length }"/></td>--%>
            <td><form action="controller" method="get">
                <input type="hidden" name="command" value="playlist-detail">
                <input type="hidden" name="id" value="${ playlist.id }">
                <input type="submit" name="submit" value="<fmt:message key="button.details"/>">
            </form></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
