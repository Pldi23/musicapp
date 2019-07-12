<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Musician Library</title></head>
<body>
<hr/>
<form action="controller" method="post">
    <input type="hidden" name="command" value="search"/>
    <label>
        Search panel
        <input type="text" name="searchrequest" placeholder="playlist, track, musician"/>
    </label>
    <input type="submit" name="submit" value="Search">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="sort-musician-by-id">
    <input type="hidden" name="offset" value="marker">
    <input type="hidden" name="order" value="marker">
    <input type="submit" name="submit" value="Sort MUSICIAN by ID">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="sort-musician-by-name">
    <input type="hidden" name="offset" value="marker">
    <input type="hidden" name="order" value="marker">
    <input type="submit" name="submit" value="Sort MUSICIAN by NAME">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-library">
    <input type="submit" name="submit" value="library">
</form>
<c:if test="${ not empty entities }">
    <table>
        Musicians:
        <c:forEach var="musician" items="${ entities }">
            <tr>
                <td><c:out value="${ musician.id }"></c:out></td>
                <td><c:out value="${ musician.name }"></c:out></td>
                <td>
                    <form method="get" action="controller">
                        <input type="hidden" name="command" value="to-remove-musician">
                        <input type="hidden" name="id" value="${ musician.id }">
                        <input type="submit" name="submit" value="remove">
                    </form>
                </td>
                <td>
                    <form method="get" action="controller">
                        <input type="hidden" name="command" value="to-update-musician" >
                        <input type="hidden" name="id" value="${ musician.id }">
                            <%--                        <input type="hidden" name="name" value="${ track.name }">--%>
                        <input type="submit" name="submit" value="update">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${ nextunavailable eq 'false'}">
        <form action="controller" method="get">
            <input type="hidden" name="command" value="show-all-musicians">
            <input type="hidden" name="direction" value="next">
            <input type="submit" name="submit" value="next">
        </form>
    </c:if>
    <c:if test="${ previousunavailable eq 'false' }">
        <form action="controller" method="get">
            <input type="hidden" name="command" value="show-all-musicians">
            <input type="hidden" name="direction" value="previous">
            <input type="submit" name="submit" value="previous">
        </form>
    </c:if>
</c:if>

${violations}
${removeResult}
${updateResult}

</body>
</html>
