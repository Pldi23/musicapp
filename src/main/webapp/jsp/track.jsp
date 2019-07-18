<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/track.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${ track.name }</title>
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
            <img src="music/img/epam-logo.svg" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <img src="music/img/note.svg" width="100" height="60" alt="">
        </div>
        <div class="col-3">
            <h3><c:out value="${ track.name }"/></h3>
        </div>
        <div class="col-5">
            <audio controls>
                <source src="music/${ track.uuid }" type="audio/mpeg">
            </audio>
        </div>
        <div class="col-2">
            <img src="music/img/note.svg" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <c:if test="${ not empty user }">
                <p class="text-info">${ process }</p>
                <form action="controller" method="post" class="form-inline">
                    <input type="hidden" name="command" value="add-track-to-playlist">
                    <input type="hidden" name="id" value="${ track.id }">
                    <label><fmt:message key="label.choose.playlist"/></label>
                    <select name="playlistid" class="custom-select my-1 mr-sm-2">
                        <c:forEach var="playlist" items="${ user.playlists }">
                            <option value="${ playlist.id }">${ playlist.name }</option>
                        </c:forEach>
                    </select>
                    <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.add.to.playlist"/>">
                </form>
<%--                stars?--%>
            </c:if>
            <br>
            <span class="badge badge-secondary"><c:out value="${ track.genre.title }"/></span>
            <span class="badge badge-pill badge-dark"><c:out value="${ track.releaseDate }"/></span>
            <%--            <img src="music/${ track.uuid }">--%>
            <br>
            <c:if test="${ not empty track.singers }">
                <label class="display-5"><fmt:message key="label.singers"/></label>
                <div class="btn-group" role="group" aria-label="Basic example">
                    <c:forEach var="singer" items="${ track.singers }" varStatus="status">
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="musician-detail">
                            <input type="hidden" name="id" value="${ singer.id }">
                            <input type="submit" class="btn btn-outline-secondary" name="submit"
                                   value="${ singer.name }">
                        </form>
                    </c:forEach>
                </div>
            </c:if>
            <br>
            <c:if test="${ not empty track.authors }">
                <label class="display-5"><fmt:message key="label.authors"/></label>
                <div class="btn-group" role="group" aria-label="Basic example">
                    <c:forEach var="author" items="${ track.authors }" varStatus="status">
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="musician-detail">
                            <input type="hidden" name="id" value="${ author.id }">
                            <input type="submit" class="btn btn-outline-secondary" name="submit"
                                   value="${ author.name }">
                        </form>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${ not empty playlists }"><fmt:message key="label.playlists"/></c:if>
            <table>
                <c:forEach var="playlist" items="${ playlists }" varStatus="status">
                    <tr>
                            <%--            <td><c:out value="${ size }"/></td>--%>
                            <%--            <td><c:out value="${ summary length }"/></td>--%>
                        <td>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="playlist-detail">
                                <input type="hidden" name="id" value="${ playlist.id }">
                                <input type="submit" class="btn btn-light" name="submit" value="${ playlist.name }">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="music/img/login-page-image.svg" alt="music app">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
