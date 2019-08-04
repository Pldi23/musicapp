<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head><title><fmt:message key="label.library"/></title></head>
<body>
<c:import url="topbar.jsp"/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-10">
            <h3 class="text-info"><fmt:message key="label.new.arrivals"/></h3>
            <p class="text-info"><c:out value="${ requestScope.process }"/></p>
            <table>
                <tbody>
                <c:forEach var="track" items="${ requestScope.tracks }">
                    <tr class="table-bg-light">
                        <c:if test="${ sessionScope.user.admin eq true}">
                            <td><c:out value="${ track.id }"/></td>
                        </c:if>
                        <td>
                            <form action="<c:url value="/controller"/>" method="post" class="align-middle" style="padding-top: 15px">
                                <input type="hidden" name="command" value="track-detail">
                                <input type="hidden" name="id" value="${ track.id }">
                                <input type="submit" class="btn btn-light" name="submit" value="${ track.name }">
                            </form>
                        </td>
                        <td>
                            <div class="btn-group" role="group" aria-label="Basic example">
                                <c:forEach var="singer" items="${ track.singers }">
                                    <form action="<c:url value="/controller"/>" method="post" class="align-middle" style="padding-top: 15px">
                                        <input type="hidden" name="command" value="musician-detail">
                                        <input type="hidden" name="id" value="${ singer.id}">
                                        <input type="submit" class="btn btn-light btn-sm" name="submit"
                                               value="${ singer.name }">
                                    </form>
                                </c:forEach>
                            </div>
                        </td>
                        <td>
                            <span class="badge badge-info">${ track.genre.title }</span>
                        </td>
                        <td>
                            <audio controls preload="metadata" onplay="setCookie('lastPlayed', '${ track.id }')">
                                <source src="music/${ track.uuid }" type="audio/mpeg">
                            </audio>
                        </td>
                        <td>
                            <form action="<c:url value="/controller"/>" method="post" class="form-inline" style="padding-top: 15px">
                                <input type="hidden" name="command" value="add-track-to-playlist">
                                <input type="hidden" name="id" value="${ track.id }">
                                <select name="playlistid" class="custom-select custom-select-sm" onchange="submit()">
                                    <option selected="selected">
                                        <fmt:message key="label.add.to.playlist"/>
                                    </option>
                                    <c:forEach var="playlist" items="${ sessionScope.user.playlists }">
                                        <option value="${ playlist.id }">${ playlist.name }</option>
                                    </c:forEach>
                                </select>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>