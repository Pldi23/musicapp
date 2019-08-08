<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/common/search-result.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.search.result"/></title>
    <style type="text/css">
        .my-custom-scrollbar {
            position: relative;
            height: 300px;
            overflow: auto;
        }

        .table-wrapper-scroll-y {
            display: block;
        }
    </style>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1"></div>
        <div class="col-10">
            <c:import url="header.jsp"/>
        </div>
        <div class="col-1">
            <img class="img-responsive" src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
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
            <p class="text-warning"><c:out value="${ requestScope.process }"/></p>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <h4><fmt:message key="label.search.result"/></h4>
            <c:if test="${ not empty requestScope.tracks }"><fmt:message
                    key="label.tracks"/>: ${ requestScope.trackssize } <fmt:message
                    key="label.found"/>
                <br>
                <div class="table-wrapper-scroll-y my-custom-scrollbar">
                    <table>
                        <c:forEach var="track" items="${ requestScope.tracks }" varStatus="status">
                            <tr>
                                <td><c:forEach var="singer" items="${ track.singers }">
                                    <c:out value="${ singer.name }"/>
                                </c:forEach></td>
                                <td><c:forEach var="author" items="${ track.authors }">
                                    <c:out value="${ author.name }"/>
                                </c:forEach></td>
                                <td>
                                    <form action="<c:url value="/controller"/>" method="post">
                                        <input type="hidden" name="command" value="track-detail">
                                        <input type="hidden" name="id" value="${ track.id }">
                                        <input type="submit" class="btn btn-light" name="submit"
                                               value="${ track.name }">
                                    </form>
                                </td>
                                <td>
                                    <audio controls preload="metadata"
                                           onplay="setCookie('lastPlayed', '${ track.id }')">
                                        <source src="music/${track.uuid}" type="audio/mpeg">
                                    </audio>
                                </td>
                                <td><span class="badge badge-info"><fmt:message key="label.filter.genre"/>::
                                <c:out value="${ track.genre.title }"/></span></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
            <br>
            <c:if test="${ not empty requestScope.musicians }"><fmt:message
                    key="label.musicians"/>: ${ requestScope.musicianssize } <fmt:message
                    key="label.found"/>
                <br>
                <div class="table-wrapper-scroll-y my-custom-scrollbar">
                    <table>
                        <c:forEach var="musician" items="${ requestScope.musicians }" varStatus="status">
                            <tr>
                                <td>
                                    <form action="<c:url value="/controller"/>" method="post">
                                        <input type="hidden" name="command" value="musician-detail">
                                        <input type="hidden" name="id" value="${ musician.id }">
                                        <input type="submit" class="btn btn-light" name="submit"
                                               value="${ musician.name }">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
            <br>
            <c:if test="${ not empty requestScope.playlists }"><fmt:message
                    key="label.playlists"/>: ${ requestScope.playlistssize } <fmt:message
                    key="label.found"/>
                <br>
                <div class="table-wrapper-scroll-y my-custom-scrollbar">
                    <table>
                        <c:forEach var="playlist" items="${ requestScope.playlists }" varStatus="status">
                            <tr>
                                <td>
                                    <form action="<c:url value="/controller"/>" method="post">
                                        <input type="hidden" name="command" value="playlist-detail">
                                        <input type="hidden" name="id" value="${ playlist.id }">
                                        <input type="submit" class="btn btn-light" name="submit"
                                               value="${ playlist.name }">
                                    </form>
                                </td>
                                <td><span class="badge badge-info">
                                    <fmt:message key="badge.duration"/>::<c:out
                                        value="${ playlist.getTotalDuration() }"/></span></td>
                                <td><span class="badge badge-info">
                                <fmt:message key="badge.quantity"/>::<c:out value="${ playlist.getSize() }"/></span>
                                </td>
                                <td><span class="badge badge-info"><fmt:message key="label.filter.genre"/>::
                                <c:out value="${ playlist.getMostPopularGenre() }"/></span></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:if>
        </div>
        <div class="col-2">
            <c:if test="${ not empty sessionScope.user }">
                <c:import url="search-form.jsp"/>
            </c:if>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>