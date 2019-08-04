<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/common/filter-result.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title><fmt:message key="label.filter.result"/></title>
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
            <p class="text-warning">${ requestScope.process }</p>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <h4><fmt:message key="label.filter.result"/></h4>
            <c:if test="${ empty requestScope.entities }">
                <fmt:message key="label.not.found"/>
            </c:if>
            <table>
                <tbody>
                <c:forEach var="track" items="${ requestScope.entities }">
                    <tr class="table-bg-light align-center align-middle">
                        <c:if test="${ sessionScope.user.admin eq true}">
                            <td>
                                <span class="badge badge-info"><c:out value="id:: ${ track.id }"/></span>
                            </td>
                        </c:if>
                        <td>
                            <form action="<c:url value="/controller"/>" method="post" style="padding-top: 15px">
                                <input type="hidden" name="command" value="track-detail">
                                <input type="hidden" name="id" value="${ track.id }">
                                <input type="submit" class="btn btn-light align-middle" name="submit"
                                       value="${ track.name }">
                            </form>
                        </td>
                        <td>
                            <div class="btn-group align-middle" role="group" aria-label="Basic example">
                                <c:forEach var="singer" items="${ track.singers }">
                                    <form action="<c:url value="/controller"/>" method="post" class="align-middle"
                                          style="padding-top: 15px">
                                        <input type="hidden" name="command" value="musician-detail">
                                        <input type="hidden" name="id" value="${ singer.id}">
                                        <input type="submit" class="btn btn-light btn-sm align-middle" name="submit"
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
                    </tr>
                </c:forEach>
                </tbody>
            </table>
<%--            <nav aria-label="Page navigation example">--%>
<%--                <ul class="pagination">--%>
<%--                    <li class="page-item">--%>
<%--                        <c:if test="${ requestScope.previousunavailable eq 'false' }">--%>
<%--                            <form action="<c:url value="/controller"/>" method="post">--%>
<%--                                <input type="hidden" name="command" value="filter-track">--%>
<%--                                <input type="hidden" name="direction" value="previous">--%>
<%--                                <input type="hidden" name="current" value="${ requestScope.current }">--%>
<%--                                <input type="hidden" name="trackname" value="${ requestScope.filter.trackName }">--%>
<%--                                <input type="hidden" name="singer" value="${ requestScope.filter.singerName }">--%>
<%--                                <input type="hidden" name="genre" value="${ requestScope.filter.genreName }">--%>
<%--                                <input type="hidden" name="releaseFrom" value="${ requestScope.filter.fromDate }">--%>
<%--                                <input type="hidden" name="releaseTo" value="${ requestScope.filter.toDate }">--%>
<%--                                <input type="submit" class="btn btn-outline-dark" name="submit"--%>
<%--                                       value="<fmt:message key="button.previous"/>">--%>
<%--                            </form>--%>
<%--                        </c:if>--%>
<%--                    </li>--%>
<%--                    <li class="page-item">--%>
<%--                        <c:if test="${ requestScope.nextunavailable eq 'false'}">--%>
<%--                            <form action="<c:url value="/controller"/>" method="post">--%>
<%--                                <input type="hidden" name="command" value="filter-track">--%>
<%--                                <input type="hidden" name="direction" value="next">--%>
<%--                                <input type="hidden" name="current" value="${ requestScope.current }">--%>
<%--                                <input type="hidden" name="trackname" value="${ requestScope.filter.trackName }">--%>
<%--                                <input type="hidden" name="singer" value="${ requestScope.filter.singerName }">--%>
<%--                                <input type="hidden" name="genre" value="${ requestScope.filter.genreName }">--%>
<%--                                <input type="hidden" name="releaseFrom" value="${ requestScope.filter.fromDate }">--%>
<%--                                <input type="hidden" name="releaseTo" value="${ requestScope.filter.toDate }">--%>
<%--                                <input type="submit" class="btn btn-outline-dark" name="submit"--%>
<%--                                       value="<fmt:message key="button.next"/>">--%>
<%--                            </form>--%>
<%--                        </c:if>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </nav>--%>
            <c:if test="${ not empty requestScope.entities and not empty requestScope.size[1] }">
                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                            <%--                        ----------%>
                        <li class="page-item">
                            <c:if test="${ requestScope.previousunavailable eq 'false' }">
                                <form action="<c:url value="/controller"/>" method="post">
                                    <input type="hidden" name="command" value="filter-track">
                                    <input type="hidden" name="direction" value="previous">
                                    <input type="hidden" name="current" value="${ requestScope.current }">
                                    <input type="hidden" name="trackname" value="${ requestScope.filter.trackName }">
                                    <input type="hidden" name="singer" value="${ requestScope.filter.singerName }">
                                    <input type="hidden" name="genre" value="${ requestScope.filter.genreName }">
                                    <input type="hidden" name="releaseFrom" value="${ requestScope.filter.fromDate }">
                                    <input type="hidden" name="releaseTo" value="${ requestScope.filter.toDate }">
                                    <input type="submit" class="page-link" name="submit"
                                           value="<fmt:message key="button.previous"/>">
                                </form>
                            </c:if>
                        </li>
                            <%--    -----------%>
                        <c:forEach var="element" items="${ requestScope.size }" varStatus="loop">
                            <c:choose>
                                <c:when test="${ element eq requestScope.current }">
                                    <li class="page-item active" aria-current="page">
                                        <form action="<c:url value="/controller"/>" method="post">
                                            <input type="hidden" name="command" value="filter-track">
                                            <input type="hidden" name="direction" value="direct">
                                            <input type="hidden" name="current" value="${ element }">
                                            <input type="hidden" name="trackname"
                                                   value="${ requestScope.filter.trackName }">
                                            <input type="hidden" name="singer"
                                                   value="${ requestScope.filter.singerName }">
                                            <input type="hidden" name="genre"
                                                   value="${ requestScope.filter.genreName }">
                                            <input type="hidden" name="releaseFrom"
                                                   value="${ requestScope.filter.fromDate }">
                                            <input type="hidden" name="releaseTo"
                                                   value="${ requestScope.filter.toDate }">
                                            <input type="submit" class="page-link" name="submit"
                                                   value="${ element }">
                                        </form>
                                    </li>
                                </c:when>
                                <c:when test="${ loop.first or loop.last or element eq 2 or element eq fn:length(requestScope.size) - 1 }">
                                    <li class="page-item">
                                        <form action="<c:url value="/controller"/>" method="post">
                                            <input type="hidden" name="command" value="filter-track">
                                            <input type="hidden" name="direction" value="direct">
                                            <input type="hidden" name="current" value="${ element }">
                                            <input type="hidden" name="trackname"
                                                   value="${ requestScope.filter.trackName }">
                                            <input type="hidden" name="singer"
                                                   value="${ requestScope.filter.singerName }">
                                            <input type="hidden" name="genre"
                                                   value="${ requestScope.filter.genreName }">
                                            <input type="hidden" name="releaseFrom"
                                                   value="${ requestScope.filter.fromDate }">
                                            <input type="hidden" name="releaseTo"
                                                   value="${ requestScope.filter.toDate }">
                                            <input type="submit" class="page-link" name="submit"
                                                   value="${ element }">
                                        </form>
                                    </li>
                                </c:when>
                                <c:when test="${ not loop.first and (element eq requestScope.current - 1
                                        or element eq requestScope.current + 1 or element eq requestScope.current - 2
                                        or element eq requestScope.current + 2) }">
                                    <c:if test="${ element eq requestScope.current - 2 and element > 3 }">
                                        <li class="page-item">
                                            <span class="page-link"><c:out value="..."/></span>
                                        </li>
                                    </c:if>
                                    <li class="page-item">
                                        <form action="<c:url value="/controller"/>" method="post">
                                            <input type="hidden" name="command" value="filter-track">
                                            <input type="hidden" name="direction" value="direct">
                                            <input type="hidden" name="current" value="${ element }">
                                            <input type="hidden" name="trackname"
                                                   value="${ requestScope.filter.trackName }">
                                            <input type="hidden" name="singer"
                                                   value="${ requestScope.filter.singerName }">
                                            <input type="hidden" name="genre"
                                                   value="${ requestScope.filter.genreName }">
                                            <input type="hidden" name="releaseFrom"
                                                   value="${ requestScope.filter.fromDate }">
                                            <input type="hidden" name="releaseTo"
                                                   value="${ requestScope.filter.toDate }">
                                            <input type="submit" class="page-link" name="submit"
                                                   value="${ element }">
                                        </form>
                                    </li>
                                    <c:if test="${ element eq requestScope.current + 2 and element lt fn:length(requestScope.size) - 2 }">
                                        <li class="page-item">
                                            <span class="page-link"><c:out value="..."/></span>
                                        </li>
                                    </c:if>
                                </c:when>
<%--                                <c:otherwise>--%>
<%--                                    <li class="page-item">--%>
<%--                                        <form action="<c:url value="/controller"/>" method="post">--%>
<%--                                            <input type="hidden" name="command" value="filter-track">--%>
<%--                                            <input type="hidden" name="direction" value="direct">--%>
<%--                                            <input type="hidden" name="current" value="${ element }">--%>
<%--                                            <input type="hidden" name="trackname"--%>
<%--                                                   value="${ requestScope.filter.trackName }">--%>
<%--                                            <input type="hidden" name="singer"--%>
<%--                                                   value="${ requestScope.filter.singerName }">--%>
<%--                                            <input type="hidden" name="genre"--%>
<%--                                                   value="${ requestScope.filter.genreName }">--%>
<%--                                            <input type="hidden" name="releaseFrom"--%>
<%--                                                   value="${ requestScope.filter.fromDate }">--%>
<%--                                            <input type="hidden" name="releaseTo"--%>
<%--                                                   value="${ requestScope.filter.toDate }">--%>
<%--                                            <input type="submit" class="page-link" name="submit"--%>
<%--                                                   value="${ element }">--%>
<%--                                        </form>--%>
<%--                                    </li>--%>
<%--                                </c:otherwise>--%>
                            </c:choose>
                        </c:forEach>
                            <%--                        ---------%>
                        <li class="page-item">
                            <c:if test="${ requestScope.nextunavailable eq 'false'}">
                                <form action="<c:url value="/controller"/>" method="post">
                                    <input type="hidden" name="command" value="filter-track">
                                    <input type="hidden" name="direction" value="next">
                                    <input type="hidden" name="current" value="${ requestScope.current }">
                                    <input type="hidden" name="trackname" value="${ requestScope.filter.trackName }">
                                    <input type="hidden" name="singer" value="${ requestScope.filter.singerName }">
                                    <input type="hidden" name="genre" value="${ requestScope.filter.genreName }">
                                    <input type="hidden" name="releaseFrom" value="${ requestScope.filter.fromDate }">
                                    <input type="hidden" name="releaseTo" value="${ requestScope.filter.toDate }">
                                    <input type="submit" class="page-link" name="submit"
                                           value="<fmt:message key="button.next"/>">
                                </form>
                            </c:if>
                        </li>
                            <%--                        -----------%>
                    </ul>
                </nav>
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
