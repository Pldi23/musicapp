<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/library/library-playlist.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title><fmt:message key="label.playlist.library"/></title>
</head>
<body>
<c:import url="topbar.jsp"/>

<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <div class="btn-toolbar" role="toolbar">
                <div class="btn-group mr-2" role="group" aria-label="First group">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="sort-playlist-by-name">
                        <input type="hidden" name="current" value="0">
                        <input type="submit" name="submit" class="btn btn-outline-dark"
                               value="<fmt:message key="button.sort.playlist.name"/>">
                    </form>
                </div>
                <c:if test="${ sessionScope.user.admin eq true}">
                    <div class="btn-group" role="group" aria-label="Second group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="sort-playlist-by-id">
                            <input type="hidden" name="current" value="0">
                            <input type="submit" name="submit" class="btn btn-outline-dark"
                                   value="<fmt:message key="button.sort.playlist.id"/>">
                        </form>
                    </div>
                </c:if>
                <div class="btn-group" role="group" aria-label="Third group">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="sort-playlist-by-length">
                        <input type="hidden" name="current" value="0">
                        <input type="submit" name="submit" class="btn btn-outline-dark"
                               value="<fmt:message key="button.sort.playlist.length"/>">
                    </form>
                </div>
            </div>
            <c:if test="${ not empty requestScope.entities }">
                <fmt:message key="label.playlists"/>
                <table>
                    <tbody>
                    <c:forEach var="playlist" items="${ requestScope.entities }">
                        <tr class="table-bg-light">
                            <c:if test="${ sessionScope.user.admin eq true}">
                                <td><c:out value="${ playlist.id }"/></td>
                            </c:if>
                            <td>
                                <form action="<c:url value="/controller"/>" method="post" class="align-middle"
                                      style="padding-top: 15px">
                                    <input type="hidden" name="command" value="playlist-detail">
                                    <input type="hidden" name="id" value="${ playlist.id }">
                                    <input type="submit" class="btn btn-light" name="submit" value="${ playlist.name }">
                                </form>
                            </td>
                            <td><span class="badge badge-info">
                                    <fmt:message key="badge.duration"/>::
                                <ctg:duration playlist="${ playlist }"/>
                            </span></td>
                            <td><span class="badge badge-info">
                                <fmt:message key="badge.quantity"/>::<c:out value="${ playlist.getSize() }"/></span>
                            </td>
                            <td><span class="badge badge-info"><fmt:message key="label.filter.genre"/>::
                                <c:out value="${ playlist.getMostPopularGenreName() }"/></span></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:import url="pagination-bar.jsp"/>
            </c:if>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/primary-logo.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
