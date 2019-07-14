<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/track-library.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head><title><fmt:message key="label.tracklibrary"/></title></head>
<body>
<%--<c:import url="../locale-form.jsp"/>--%>
<c:import url="../header.jsp"/>
<c:import url="../library-form.jsp"/>
<hr/>
<c:if test="${ not empty violations }">
    <ctg:violations violations="${ violations }"/>
</c:if>
<fmt:message var="sortidbutton" key="button.sort.track.id" scope="page"/>
<fmt:message var="sortnamebutton" key="button.sort.track.name" scope="page"/>
<fmt:message var="sortgenrebutton" key="button.sort.track.genre" scope="page"/>
<ctg:sort-form commandValue="sort-track-by-id" submitValue="${ sortidbutton }"/>
<ctg:sort-form commandValue="sort-track-by-name" submitValue="${ sortnamebutton }"/>
<ctg:sort-form commandValue="sort-track-by-genre" submitValue="${ sortgenrebutton }"/>
<c:import url="../track-filter-form.jsp"/>
<ctg:show-tracks tracks="${ entities }" admin="${ user.admin }" removeCommandValue="to-remove-track" updateCommandValue="to-update-track"
                 commandValue="show-all-tracks" nextUnavailable="${ nextunavailable }"
                 previousUnavailable="${ previousunavailable }"/>

${removeResult}
${updateResult}

</body>
</html>
<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="sort-track-by-id">--%>
<%--    <input type="hidden" name="offset" value="marker">--%>
<%--    <input type="hidden" name="order" value="marker">--%>
<%--    <input type="submit" name="submit" value="Sort TRACK by ID">--%>
<%--</form>--%>
<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="sort-track-by-name">--%>
<%--    <input type="hidden" name="offset" value="marker">--%>
<%--    <input type="hidden" name="order" value="marker">--%>
<%--    <input type="submit" name="submit" value="Sort TRACK by NAME">--%>
<%--</form>--%>
<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="sort-track-by-genre">--%>
<%--    <input type="hidden" name="offset" value="marker">--%>
<%--    <input type="hidden" name="order" value="marker">--%>
<%--    <input type="submit" name="submit" value="Sort TRACK by GENRE">--%>
<%--</form>--%>


<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="to-library">--%>
<%--    <input type="submit" name="submit" value="library">--%>
<%--</form>--%>
<%--<c:if test="${ not empty entities }">--%>
<%--    <table>--%>
<%--        Tracks:--%>
<%--        <c:forEach var="track" items="${entities}">--%>
<%--            <tr>--%>
<%--                <td><c:out value="${ track.id }"></c:out></td>--%>
<%--                <td><c:out value="${ track.name }"></c:out></td>--%>
<%--                <c:forEach var="singer" items="${ track.singers}">--%>
<%--                    <td><c:out value="${ singer.name}"></c:out></td>--%>
<%--                </c:forEach>--%>
<%--                <c:forEach var="author" items="${ track.authors}">--%>
<%--                    <td><c:out value="${ author.name }"></c:out></td>--%>
<%--                </c:forEach>--%>
<%--                <td><c:out value="${ track.genre.title }"/></td>--%>
<%--                <td>--%>
<%--                    <audio controls>--%>
<%--                        <source src="music/${track.uuid}" type="audio/mpeg">--%>
<%--                    </audio>--%>
<%--                </td>--%>
<%--                <td>--%>
<%--                    <form method="get" action="controller">--%>
<%--                        <input type="hidden" name="command" value="to-remove-track">--%>
<%--                        <input type="hidden" name="id" value="${ track.id }">--%>
<%--                            &lt;%&ndash;                        <input type="hidden" name="name" value="${ track.name }">&ndash;%&gt;--%>
<%--                        <input type="submit" name="submit" value="remove">--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--                <td>--%>
<%--                    <form method="get" action="controller">--%>
<%--                        <input type="hidden" name="command" value="to-update-track" >--%>
<%--                        <input type="hidden" name="id" value="${ track.id }">--%>
<%--                            &lt;%&ndash;                        <input type="hidden" name="name" value="${ track.name }">&ndash;%&gt;--%>
<%--                        <input type="submit" name="submit" value="update">--%>
<%--                    </form>--%>
<%--                </td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>
<%--    <c:if test="${ nextunavailable eq 'false'}">--%>
<%--        <form action="controller" method="get">--%>
<%--            <input type="hidden" name="command" value="show-all-tracks">--%>
<%--            <input type="hidden" name="direction" value="next">--%>
<%--            <input type="submit" name="submit" value="next">--%>
<%--        </form>--%>
<%--    </c:if>--%>
<%--    <c:if test="${ previousunavailable eq 'false' }">--%>
<%--        <form action="controller" method="get">--%>
<%--            <input type="hidden" name="command" value="show-all-tracks">--%>
<%--            <input type="hidden" name="direction" value="previous">--%>
<%--            <input type="submit" name="submit" value="previous">--%>
<%--        </form>--%>
<%--    </c:if>--%>
<%--</c:if>--%>
