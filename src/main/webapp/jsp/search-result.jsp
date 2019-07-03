<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Search results</title></head>
<hr/>
<h2>
    Search results
</h2>
<br>
listen top:
<audio controls><source src="music/avicii-tim.mp3" type="audio/mpeg"></audio>
<br>
Musicians :
<%--${musicians}--%>
<table>
    <c:forEach var="musician" items="${musicians}" varStatus="status">
        <tr>
            <td><c:out value="${ musician.name }"/></td>
            <td><c:out value="${ musician.id }"/></td>
        </tr>
    </c:forEach>
</table>
<br>
Tracks :
<%--${tracks}--%>
<table>
    <c:forEach var="track" items="${tracks}" varStatus="status">
        <tr>
            <td><c:out value="${ track.name }"/></td>
            <td><c:out value="${ track.id }"/></td>
            <td><c:out value="music/${track.path}"/></td>
            <td><audio controls><source src="music/${track.path}" type="audio/mpeg"></audio></td>
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

        </tr>
    </c:forEach>
</table>
<br>
Playlists :
${playlists}
<hr/>
</body>
</html>