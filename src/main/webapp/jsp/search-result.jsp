<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/admin/search-result.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.search.result"/></title></head>
<body>
<c:import url="header.jsp"/>
<hr/>
<h2>
    <fmt:message key="label.search.result"/>
</h2>
${ process }
<br>
<c:if test="${ not empty musicians }"><fmt:message key="label.musicians"/>: ${ musicianssize } <fmt:message
        key="label.found"/>
    <table>
        <c:forEach var="musician" items="${ musicians }" varStatus="status">
            <tr>
                <td><c:out value="${ musician.name }"/></td>
                <td>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="musician-detail">
                        <input type="hidden" name="id" value="${ musician.id }">
                        <input type="submit" name="submit" value="<fmt:message key="button.details"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${ musicianssize - nextoffset > 0 }">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="search">
            <input type="hidden" name="searchrequest" value="${ searchrequest }">
            <input type="hidden" name="key-musicians" value="1">
            <input type="hidden" name="direction" value="next">
            <input type="hidden" name="offset" value="${ nextoffset }">
            <input type="submit" name="submit" value="<fmt:message key="button.next"/>">
        </form>
    </c:if>
    <c:if test="${ previousoffset >= 0 }">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="search">
            <input type="hidden" name="searchrequest" value="${ searchrequest }">
            <input type="hidden" name="key-musicians" value="1">
            <input type="hidden" name="direction" value="previous">
            <input type="hidden" name="offset" value="${ previousoffset }">
            <input type="submit" name="submit" value="<fmt:message key="button.previous"/>">
        </form>
    </c:if>
</c:if>
<br>
<c:if test="${ not empty tracks }"><fmt:message key="label.tracks"/>: ${ trackssize } <fmt:message key="label.found"/>
    <table>
        <c:forEach var="track" items="${tracks}" varStatus="status">
            <tr>
                <td><c:out value="${ track.name }"/></td>
                <td><c:forEach var="singer" items="${ track.singers }">
                    <c:out value="${ singer.name }"/>
                </c:forEach></td>
                <td><c:forEach var="author" items="${ track.authors }">
                    <c:out value="${ author.name }"/>
                </c:forEach></td>
                <td>
                    <audio controls>
                        <source src="music/${track.uuid}" type="audio/mpeg">
                    </audio>
                </td>
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
    <c:if test="${ trackssize - nextoffset > 0 }">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="search">
            <input type="hidden" name="searchrequest" value="${ searchrequest }">
            <input type="hidden" name="key-tracks" value="1">
            <input type="hidden" name="direction" value="next">
            <input type="hidden" name="offset" value="${ nextoffset }">
            <input type="submit" name="submit" value="<fmt:message key="button.next"/>">
        </form>
    </c:if>
    <c:if test="${ previousoffset >= 0 }">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="search">
            <input type="hidden" name="searchrequest" value="${ searchrequest }">
            <input type="hidden" name="key-tracks" value="1">
            <input type="hidden" name="direction" value="previous">
            <input type="hidden" name="offset" value="${ previousoffset }">
            <input type="submit" name="submit" value="<fmt:message key="button.previous"/>">
        </form>
    </c:if>
</c:if>
<br>
<c:if test="${ not empty playlists }"><fmt:message key="label.playlists"/>: ${ playlistssize } <fmt:message
        key="label.found"/>
    <table>
        <c:forEach var="playlist" items="${ playlists }" varStatus="status">
            <tr>
                <td><c:out value="${ playlist.name }"/></td>
                <td>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="playlist-detail">
                        <input type="hidden" name="id" value="${ playlist.id }">
                        <input type="submit" name="submit" value="<fmt:message key="button.details"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${ playlistssize - nextoffset > 0 }">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="search">
            <input type="hidden" name="searchrequest" value="${ searchrequest }">
            <input type="hidden" name="key-playlists" value="1">
            <input type="hidden" name="direction" value="next">
            <input type="hidden" name="offset" value="${ nextoffset }">
            <input type="submit" name="submit" value="<fmt:message key="button.next"/>">
        </form>
    </c:if>
    <c:if test="${ previousoffset >= 0 }">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="search">
            <input type="hidden" name="searchrequest" value="${ searchrequest }">
            <input type="hidden" name="key-playlists" value="1">
            <input type="hidden" name="direction" value="previous">
            <input type="hidden" name="offset" value="${ previousoffset }">
            <input type="submit" name="submit" value="<fmt:message key="button.previous"/>">
        </form>
    </c:if>
</c:if>
<hr/>
</body>
</html>
<%--            <td>--%>
<%--            <form method="get" action="controller">--%>
<%--                <input type="hidden" name="command" value="remove">--%>
<%--                <input type="hidden" name="uuid" value="${ track.uuid }">--%>
<%--                <input type="submit" name="submit" value="remove">--%>
<%--                ${removeresult}--%>
<%--            </form> </td>--%>
<%--                <audio controls>--%>
<%--                    <source src="${ track.path }" type="audio/mpeg">--%>
<%--                </audio>--%>
<%--                <form method="get" action="controller">--%>
<%--                    <input type="hidden" name="command" value="playsound">--%>
<%--                    <input type="hidden" name="tr" value="${track.path}">--%>
<%--                    <label>--%>
<%--                        play panel--%>
<%--                    </label>--%>
<%--                    <input type="submit" name="submit" value="play">--%>
<%--                </form>--%>

