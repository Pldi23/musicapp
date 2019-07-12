<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/library.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.library"/></title></head>
<body>
<c:import url="../locale-form.jsp"/>
<c:import url="../header.jsp"/>
<hr/>
<c:if test="${ not empty violations }">
    <ctg:violations violations="${ violations }"/>
</c:if>
<form action="controller" method="get">
    <input type="hidden" name="command" value="show-all-tracks">
    <input type="hidden" name="offset" value="0">
    <input type="submit" name="submit" value="<fmt:message key="button.showalltracks"/>">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="show-all-playlists">
    <input type="hidden" name="offset" value="0">
    <input type="submit" name="submit" value="<fmt:message key="button.showallplaylists"/>">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="show-all-musicians">
    <input type="hidden" name="offset" value="0">
    <input type="submit" name="submit" value="<fmt:message key="button.showallmusicians"/>">
</form>

${addResult}
${removeResult}
${updateResult}

</body>
</html>
<%--<c:if test="${ not empty tracks }">--%>
<%--    <form action="controller" method="get">--%>
<%--        <input type="hidden" name="command" value="sorttrackbyid">--%>
<%--        <input type="submit" name="submit" value="SortCommandExecutor TRACK by ID">--%>
<%--    </form>--%>
<%--    <form action="controller" method="get">--%>
<%--        <input type="hidden" name="command" value="sorttrackbyname">--%>
<%--        <input type="submit" name="submit" value="SortCommandExecutor TRACK by NAME">--%>
<%--    </form>--%>
<%--    <form action="controller" method="get">--%>
<%--        <input type="hidden" name="command" value="sorttrackbygenre">--%>
<%--        <input type="submit" name="submit" value="SortCommandExecutor TRACK by GENRE">--%>
<%--    </form>--%>
<%--    <table>--%>
<%--        Tracks:--%>
<%--        <c:forEach var="track" items="${tracks}">--%>
<%--            <tr>--%>
<%--                <td><c:out value="${ track.id }"></c:out></td>--%>
<%--                <td><c:out value="${ track.name }"></c:out></td>--%>
<%--                <c:forEach var="singer" items="${ track.singers}">--%>
<%--                <td><c:out value="${ singer.name}"></c:out></td>--%>
<%--                </c:forEach>--%>
<%--                <c:forEach var="author" items="${ track.authors}">--%>
<%--                <td><c:out value="${ author.name }"></c:out></td>--%>
<%--                </c:forEach>--%>
<%--                <td><c:out value="${ track.genre.title }"/></td>--%>
<%--                <td>--%>
<%--                    <audio controls>--%>
<%--                        <source src="music/${track.uuid}" type="audio/mpeg">--%>
<%--                    </audio>--%>
<%--                </td>--%>
<%--                <td>--%>
<%--                    <form method="get" action="controller">--%>
<%--                        <input type="hidden" name="command" value="areyousure">--%>
<%--                        <input type="hidden" name="uuid" value="${ track.uuid }">--%>
<%--&lt;%&ndash;                        <input type="hidden" name="name" value="${ track.name }">&ndash;%&gt;--%>
<%--                        <input type="submit" name="submit" value="remove">--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--                <td>--%>
<%--                    <form method="get" action="controller">--%>
<%--                        <input type="hidden" name="command" value="toupdatetrack" >--%>
<%--                        <input type="hidden" name="uuid" value="${ track.uuid }">--%>
<%--&lt;%&ndash;                        <input type="hidden" name="name" value="${ track.name }">&ndash;%&gt;--%>
<%--                        <input type="submit" name="submit" value="update">--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>
<%--    <c:if test="${ nextunavailable eq 'false'}">--%>
<%--        <form action="controller" method="get">--%>
<%--            <input type="hidden" name="command" value="${ command }">--%>
<%--            <input type="hidden" name="direction" value="next">--%>
<%--            <input type="submit" name="submit" value="next">--%>
<%--        </form>--%>
<%--    </c:if>--%>
<%--    <c:if test="${ previousunavailable eq 'false' }">--%>
<%--        <form action="controller" method="get">--%>
<%--            <input type="hidden" name="command" value="${ command }">--%>
<%--            <input type="hidden" name="direction" value="previous">--%>
<%--            <input type="submit" name="submit" value="previous">--%>
<%--        </form>--%>
<%--    </c:if>--%>
<%--</c:if>--%>
<%--<br>--%>
<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="back">--%>
<%--    <input type="submit" name="submit" value="Back">--%>
<%--</form>--%>
<%--<a href="${header['referer']}">--%>
<%--    Back--%>
<%--</a>--%>
<%--<hr/>--%>
