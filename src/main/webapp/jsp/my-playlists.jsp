<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="/jsp/my-playlists.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.playlists"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<hr/>
${ process }
<form action="controller" method="get">
    <input type="hidden" name="command" value="playlist-create">
    <input type="text" name="name" minlength="2" maxlength="50" required="required">
    <input type="submit" name="submit" value="<fmt:message key="button.playlist.create"/> ">
</form>
<c:if test="${ not empty playlists }"><fmt:message key="label.playlists.total"/> :: ${ size } </c:if>
<table>
    <c:forEach var="playlist" items="${ playlists }">
        <tr>
            <td><c:out value="${ playlist.name }"/></td>
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
