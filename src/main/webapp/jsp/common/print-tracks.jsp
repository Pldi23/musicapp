<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>

<div class="container container-fluid bg-light">
    <div class="row">
        <div class="col-12">
            <table>
                <tbody>
                <c:forEach var="track" items="${ requestScope.tracks }">
                    <tr class="table-bg-light">
                        <td>
                            <form action="<c:url value="/controller"/>" method="get">
                                <input type="hidden" name="command" value="track-detail">
                                <input type="hidden" name="id" value="${ track.id }">
                                <input type="submit" class="btn btn-light" name="submit" value="${ track.name }">
                            </form>
                        </td>
                        <td>
                            <div class="btn-group" role="group" aria-label="Basic example">
                                <c:forEach var="singer" items="${ track.singers }">
                                    <form action="<c:url value="/controller"/>" method="get">
                                        <input type="hidden" name="command" value="musician-detail">
                                        <input type="hidden" name="id" value="${ singer.id }">
                                        <input type="submit" class="btn btn-light btn-sm" name="submit"
                                               value="${ singer.name }">
                                    </form>
                                </c:forEach>
                            </div>
                        </td>
                        <td>
                            <audio controls preload="metadata" onplay="setCookie('lastPlayed', '${ track.id }')">
                                <source src="music/${ track.uuid }" type="audio/mpeg">
                            </audio>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
