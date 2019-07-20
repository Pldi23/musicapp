<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/playlist.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${ playlist.name }</title>
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
<%--            <form action="controller" method="post">--%>
<%--                <input type="hidden" name="command" value="playlist-personal">--%>
<%--                &lt;%&ndash;                    <select name="gender">&ndash;%&gt;--%>
<%--                &lt;%&ndash;                        <option selected="selected">${ user.gender }</option>&ndash;%&gt;--%>
<%--                &lt;%&ndash;                        <option value="male"><fmt:message key="option.personal"/></option>&ndash;%&gt;--%>
<%--                &lt;%&ndash;                        <option value="female"><fmt:message key="option.public"/></option>&ndash;%&gt;--%>
<%--                &lt;%&ndash;                    </select>&ndash;%&gt;--%>
<%--                <c:choose>--%>
<%--                    <c:when test="${ playlist.personal }">--%>
<%--                        <input type="submit" class="btn btn-dark" name="submit"--%>
<%--                               value="<fmt:message key="button.update"/>">--%>
<%--                    </c:when>--%>
<%--                    <c:otherwise>--%>
<%--                        <input type="submit" class="btn btn-dark" name="submit"--%>
<%--                               value="<fmt:message key="button.update"/>">--%>
<%--                    </c:otherwise>--%>
<%--                </c:choose>--%>
<%--            </form>--%>
            <c:choose>
                <c:when test="${ playlist.personal }">
                    <span class="badge badge-secondary"><fmt:message key="badge.private"/></span>
                </c:when>
                <c:otherwise>
                    <span class="badge badge-secondary"><fmt:message key="badge.public"/></span>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-6">
            <ul class="list-inline">
                <li class="list-inline-item"><h3><c:out value="${ playlist.name }"/></h3></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="badge.duration"/> :: <c:out value="${ length }"/></h5></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="badge.quantity"/> :: <c:out value="${ size }"/></h5></li>
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
            <h5>My playlists</h5>
            <c:forEach var="userPlaylist" items="${ user.playlists }">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="playlist-detail">
                    <input type="hidden" name="id" value="${ userPlaylist.id }">
                    <input type="submit" class="btn btn-light btn-block" name="submit"
                           value="<c:out value="${ userPlaylist.name }"/>">
                </form>
            </c:forEach>
        </div>
        <div class="col-8">
            <p class="text-info"><c:out value="${ process }"/></p>
            <c:if test="${ not empty playlist.tracks }"><fmt:message key="label.tracks"/></c:if>
            <table>
                <c:forEach var="track" items="${ playlist.tracks }" varStatus="status">
                    <tr>
                        <td>
                            <audio controls>
                                <source src="music/${track.uuid}" type="audio/mpeg">
                            </audio>
                        </td>
                        <td>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="track-detail">
                                <input type="hidden" name="id" value="${ track.id }">
                                <input type="submit" class="btn btn-light btn-sm" name="submit" value="${ track.name }">
                            </form>
                        </td>
                        <td><span class="badge badge-secondary"><c:out value="${ track.genre.title }"/></span></td>
                        <td><span class="badge badge-secondary"><c:out value="${ track.releaseDate }"/></span></td>
                        <td>
                            <c:forEach var="singer" items="${ track.singers }">
                                <form action="controller" method="get">
                                    <input type="hidden" name="command" value="musician-detail">
                                    <input type="hidden" name="id" value="${ singer.id }">
                                    <input type="submit" class="btn btn-light btn-sm" name="submit"
                                           value="${ singer.name }">
                                </form>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="author" items="${ track.authors }">
                                <form action="controller" method="get">
                                    <input type="hidden" name="command" value="musician-detail">
                                    <input type="hidden" name="id" value="${ author.id }">
                                    <input type="submit" class="btn btn-light btn-sm" name="submit"
                                           value="${ author.name }">
                                </form>
                            </c:forEach>
                        </td>
                        <td>
                            <ctg:remove-track-from-playlist currentPlaylist="${ playlist }"
                                                            userPlaylists="${ user.playlists }" track="${ track }"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-2">
            <c:import url="search-form.jsp"/>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
