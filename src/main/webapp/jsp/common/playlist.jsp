<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/common/playlist.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css'
      integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>

<html>
<head>
    <title>${ requestScope.playlist.name }</title>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
        </div>
        <div class="col-10">
            <c:import url="header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <img src="<c:url value="/resources/note.svg"/>" width="100" height="60" alt="">
        </div>
        <div class="col-2">
            <c:choose>
                <c:when test="${ requestScope.playlist.personal }">
                    <span class="badge badge-secondary"><fmt:message key="badge.private"/></span>
                </c:when>
                <c:otherwise>
                    <span class="badge badge-secondary"><fmt:message key="badge.public"/></span>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-6">
            <ul class="list-inline">
                <li class="list-inline-item"><h3><c:out value="${ requestScope.playlist.name }"/></h3></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="badge.duration"/> :: <c:out
                        value="${ requestScope.length }"/></h5></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="badge.quantity"/> :: <c:out
                        value="${ requestScope.size }"/></h5></li>
            </ul>
        </div>
        <div class="col-2">
            <img src="<c:url value="/resources/note.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <h5><fmt:message key="label.playlists.my"/></h5>
            <c:forEach var="userPlaylist" items="${ sessionScope.user.playlists }">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="playlist-detail">
                    <input type="hidden" name="id" value="${ userPlaylist.id }">
                    <input type="submit" class="btn btn-light btn-block" name="submit"
                           value="<c:out value="${ userPlaylist.name }"/>">
                </form>
            </c:forEach>
            <hr>
            <c:import url="search-form.jsp"/>
        </div>
        <div class="col-10">
            <p class="text-info"><c:out value="${ requestScope.process }"/></p>
            <c:if test="${ requestScope.playlist.personal eq true or (sessionScope.user.admin eq true and requestScope.playlist.personal eq false) }">
                <div class="btn-toolbar" role="toolbar" style="padding-top: 15px">
                    <div class="btn-group mr-2" role="group" aria-label="Third group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="update-tracks-order">
                            <input type="hidden" name="id" value="${ requestScope.playlist.id }">
                            <input type="hidden" name="move" value="shuffle">
                            <input type="hidden" name="index" value="-1">
                            <input type="submit" class="btn btn-primary btn-sm fa fa-sm" name="submit"
                                   value="&#xf074; <fmt:message key="button.shuffle"/>">
                        </form>
                    </div>
                    <div class="btn-group mr-2" role="group" aria-label="Fourth group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="update-tracks-order">
                            <input type="hidden" name="id" value="${ requestScope.playlist.id }">
                            <input type="hidden" name="move" value="reverse">
                            <input type="hidden" name="index" value="-1">
                            <input type="submit" class="btn btn-primary btn-sm fa fa-sm" name="submit"
                                   value="&#xf0dc; <fmt:message key="button.reverse"/>">
                        </form>
                    </div>
                    <div class="btn-group mr-2" role="group" aria-label="First group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="to-update-playlist">
                            <input type="hidden" name="entityType" value="playlist">
                            <input type="hidden" name="id" value="${ requestScope.playlist.id }">
                            <input type="submit" class="btn btn-outline-info btn-sm fa fa-sm" name="submit"
                                   value="&#xf044; <fmt:message key="button.update"/>">
                        </form>
                    </div>
                    <div class="btn-group mr-2" role="group" aria-label="Second group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="to-remove-playlist">
                            <input type="hidden" name="id" value="${ requestScope.playlist.id }">
                            <input type="hidden" name="entityType" value="playlist">
                            <input type="submit" class="btn btn-outline-danger btn-sm fa fa-sm" name="submit"
                                   value="&#xf2ed; <fmt:message key="button.remove"/>">
                        </form>
                    </div>
                </div>
            </c:if>
            <c:if test="${ not empty requestScope.playlist.tracks }"><fmt:message key="label.tracks"/></c:if>
            <table>
                <c:forEach var="track" items="${ requestScope.playlist.tracks }" varStatus="status">
                    <tr>
                        <td style="padding-top: 15px">
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="update-tracks-order">
                                <input type="hidden" name="id" value="${ requestScope.playlist.id }">
                                <input type="hidden" name="index" value="${ status.index }">
                                <select class="selectpicker" data-container="body" onchange="submit()" data-width="fit"
                                        name="move">
                                    <option selected="selected" data-content="<i class='fas fa-ellipsis-v'></i>"></option>
                                    <c:if test="${ status.index > 0 }">
                                        <option value="up"><fmt:message key="option.move.up"/></option>
                                    </c:if>
                                    <c:if test="${ status.last eq false }">
                                        <option value="down"><fmt:message key="option.move.down"/></option>
                                    </c:if>
                                    <c:if test="${ status.index > 0 }">
                                        <option value="top"><fmt:message key="option.move.top"/></option>
                                    </c:if>
                                    <c:if test="${ status.last eq false }">
                                        <option value="bottom"><fmt:message key="option.move.bottom"/></option>
                                    </c:if>
                                </select>
                            </form>
                        </td>
                        <td>
                            <audio id="${ status.index }" controls preload="metadata" onplay="setCookie('lastPlayed', '${ track.id }')">
                                <source src="music/${track.uuid}" type="audio/mpeg">
                            </audio>
                        </td>
                        <td style="padding-top: 15px">
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="track-detail">
                                <input type="hidden" name="id" value="${ track.id }">
                                <input type="submit" class="btn btn-light btn-sm" name="submit" value="${ track.name }">
                            </form>
                        </td>
                        <td><span class="badge badge-secondary"><c:out value="${ track.genre.title }"/></span></td>
                        <td><span class="badge badge-secondary"><c:out value="${ track.releaseDate }"/></span></td>
                        <td style="padding-top: 15px">
                            <c:forEach var="singer" items="${ track.singers }">
                                <form action="<c:url value="/controller"/>" method="post">
                                    <input type="hidden" name="command" value="musician-detail">
                                    <input type="hidden" name="id" value="${ singer.id }">
                                    <input type="submit" class="btn btn-light btn-sm" name="submit"
                                           value="${ singer.name }">
                                </form>
                            </c:forEach>
                        </td>
                        <td style="padding-top: 15px">
                            <c:forEach var="author" items="${ track.authors }">
                                <form action="<c:url value="/controller"/>" method="post">
                                    <input type="hidden" name="command" value="musician-detail">
                                    <input type="hidden" name="id" value="${ author.id }">
                                    <input type="submit" class="btn btn-light btn-sm" name="submit"
                                           value="${ author.name }">
                                </form>
                            </c:forEach>
                        </td>
                        <td>
                            <ctg:remove-track-from-playlist currentPlaylist="${ requestScope.playlist }"
                                                            userPlaylists="${ sessionScope.user.playlists }"
                                                            track="${ track }"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
