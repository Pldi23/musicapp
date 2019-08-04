<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/common/track.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${ requestScope.track.name }</title>
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
        <div class="col-3">
            <h3><c:out value="${ requestScope.track.name }"/></h3>
        </div>
        <div class="col-5">
            <audio controls preload="metadata" onplay="setCookie('lastPlayed', '${ requestScope.track.id }')">
                <source src="music/${ requestScope.track.uuid }" type="audio/mpeg">
            </audio>
        </div>
        <div class="col-2">
            <img src="<c:url value="/resources/note.svg"/>" width="100" height="60" alt="">
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
            <c:if test="${ not empty sessionScope.user }">
                <p class="text-info"><c:out value="${ requestScope.process }"/></p>
                <form action="<c:url value="/controller"/>" method="post" class="form-inline">
                    <input type="hidden" name="command" value="add-track-to-playlist">
                    <input type="hidden" name="id" value="${ requestScope.track.id }">
                    <label><fmt:message key="label.choose.playlist"/></label>
                    <select name="playlistid" class="custom-select my-1 mr-sm-2">
                        <c:forEach var="playlist" items="${ sessionScope.user.playlists }">
                            <option value="${ playlist.id }">${ playlist.name }</option>
                        </c:forEach>
                    </select>
                    <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.add.to.playlist"/>">
                </form>
            </c:if>
            <br>
            <span class="badge badge-secondary"><c:out value="${ requestScope.track.genre.title }"/></span>
            <span class="badge badge-pill badge-dark"><c:out value="${ requestScope.track.releaseDate }"/></span>
            <%--            <img src="music/${ track.uuid }">--%>
            <br>
            <c:if test="${ not empty requestScope.track.singers }">
                <label class="display-5"><fmt:message key="label.singers"/></label>
                <div class="btn-group" role="group" aria-label="Basic">
                    <c:forEach var="singer" items="${ requestScope.track.singers }" varStatus="status">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="musician-detail">
                            <input type="hidden" name="id" value="${ singer.id }">
                            <input type="submit" class="btn btn-outline-secondary" name="submit"
                                   value="${ singer.name }">
                        </form>
                    </c:forEach>
                </div>
            </c:if>
            <br>
            <c:if test="${ not empty requestScope.track.authors }">
                <label class="display-5"><fmt:message key="label.authors"/></label>
                <div class="btn-group" role="group" aria-label="Basic">
                    <c:forEach var="author" items="${ requestScope.track.authors }" varStatus="status">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="musician-detail">
                            <input type="hidden" name="id" value="${ author.id }">
                            <input type="submit" class="btn btn-outline-secondary" name="submit"
                                   value="${ author.name }">
                        </form>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${ not empty requestScope.playlists }"><fmt:message key="label.playlists"/></c:if>
            <table>
                <c:forEach var="playlist" items="${ requestScope.playlists }" varStatus="status">
                    <tr>
                        <td>
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="playlist-detail">
                                <input type="hidden" name="id" value="${ playlist.id }">
                                <input type="submit" class="btn btn-light" name="submit" value="${ playlist.name }">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${ sessionScope.user.admin eq true }">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="to-update-track">
                    <input type="hidden" name="entityType" value="track">
                    <input type="hidden" name="id" value="${ requestScope.track.id }">
                    <input type="submit" class="btn btn-outline-info" name="submit"
                           value="<fmt:message key="button.update"/>">
                </form>
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="to-remove-track">
                    <input type="hidden" name="id" value="${ requestScope.track.id }">
                    <input type="hidden" name="entityType" value="track">
                    <input type="submit" class="btn btn-outline-danger" name="submit"
                           value="<fmt:message key="button.remove"/>">
                </form>
            </c:if>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/primary-logo.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
