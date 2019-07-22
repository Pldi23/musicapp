<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/common/search-result.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.search.result"/></title></head>
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
            <c:if test="${ not empty requestScope.tracks }"><fmt:message key="label.tracks"/>: ${ requestScope.trackssize } <fmt:message
                    key="label.found"/>
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
                                <form action="<c:url value="/controller"/>" method="get">
                                    <input type="hidden" name="command" value="track-detail">
                                    <input type="hidden" name="id" value="${ track.id }">
                                    <input type="submit" class="btn btn-light" name="submit" value="${ track.name }">
                                </form>
                            </td>
                            <td>
                                <audio controls>
                                    <source src="music/${track.uuid}" type="audio/mpeg">
                                </audio>
                            </td>
                            <td><span class="badge badge-info"><fmt:message key="label.filter.genre"/>::
                                <c:out value="${ track.genre.title }"/></span></td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${ trackssize - nextoffset > 0 }">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="search">
                        <input type="hidden" name="searchrequest" value="${ searchrequest }">
                        <input type="hidden" name="key-tracks" value="1">
                        <input type="hidden" name="direction" value="next">
                        <input type="hidden" name="offset" value="${ nextoffset }">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.next"/>">
                    </form>
                </c:if>
                <c:if test="${ previousoffset >= 0 }">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="search">
                        <input type="hidden" name="searchrequest" value="${ searchrequest }">
                        <input type="hidden" name="key-tracks" value="1">
                        <input type="hidden" name="direction" value="previous">
                        <input type="hidden" name="offset" value="${ previousoffset }">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.previous"/>">
                    </form>
                </c:if>
            </c:if>
            <br>
            <c:if test="${ not empty musicians }"><fmt:message key="label.musicians"/>: ${ musicianssize } <fmt:message
                    key="label.found"/>
                <table>
                    <c:forEach var="musician" items="${ musicians }" varStatus="status">
                        <tr>
                            <td>
                                <form action="<c:url value="/controller"/>" method="get">
                                    <input type="hidden" name="command" value="musician-detail">
                                    <input type="hidden" name="id" value="${ musician.id }">
                                    <input type="submit" class="btn btn-light" name="submit" value="${ musician.name }">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${ musicianssize - nextoffset > 0 }">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="search">
                        <input type="hidden" name="searchrequest" value="${ searchrequest }">
                        <input type="hidden" name="key-musicians" value="1">
                        <input type="hidden" name="direction" value="next">
                        <input type="hidden" name="offset" value="${ nextoffset }">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.next"/>">
                    </form>
                </c:if>
                <c:if test="${ previousoffset >= 0 }">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="search">
                        <input type="hidden" name="searchrequest" value="${ searchrequest }">
                        <input type="hidden" name="key-musicians" value="1">
                        <input type="hidden" name="direction" value="previous">
                        <input type="hidden" name="offset" value="${ previousoffset }">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.previous"/>">
                    </form>
                </c:if>
            </c:if>
            <br>
            <c:if test="${ not empty playlists }"><fmt:message key="label.playlists"/>: ${ playlistssize } <fmt:message
                    key="label.found"/>
                <table>
                    <c:forEach var="playlist" items="${ playlists }" varStatus="status">
                        <tr>
                            <td>
                                <form action="<c:url value="/controller"/>" method="get">
                                    <input type="hidden" name="command" value="playlist-detail">
                                    <input type="hidden" name="id" value="${ playlist.id }">
                                    <input type="submit" class="btn btn-light" name="submit" value="${ playlist.name }">
                                </form>
                            </td>
                            <td><span class="badge badge-info">
                                    <fmt:message key="badge.duration"/>::<c:out value="${ playlist.getTotalDuration() }"/></span></td>
                            <td><span class="badge badge-info">
                                <fmt:message key="badge.quantity"/>::<c:out value="${ playlist.getSize() }"/></span></td>
                            <td><span class="badge badge-info"><fmt:message key="label.filter.genre"/>::
                                <c:out value="${ playlist.getMostPopularGenre() }"/></span></td>
                        </tr>
                    </c:forEach>
                </table>
                <c:if test="${ playlistssize - nextoffset > 0 }">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="search">
                        <input type="hidden" name="searchrequest" value="${ searchrequest }">
                        <input type="hidden" name="key-playlists" value="1">
                        <input type="hidden" name="direction" value="next">
                        <input type="hidden" name="offset" value="${ nextoffset }">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.next"/>">
                    </form>
                </c:if>
                <c:if test="${ previousoffset >= 0 }">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="search">
                        <input type="hidden" name="searchrequest" value="${ searchrequest }">
                        <input type="hidden" name="key-playlists" value="1">
                        <input type="hidden" name="direction" value="previous">
                        <input type="hidden" name="offset" value="${ previousoffset }">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.previous"/>">
                    </form>
                </c:if>
            </c:if>
        </div>
        <div class="col-2">
            <c:import url="search-form.jsp"/>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>