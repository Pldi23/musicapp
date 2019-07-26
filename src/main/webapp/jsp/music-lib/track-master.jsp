<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head><title><fmt:message key="label.tracklibrary"/></title></head>
<body>
<c:import url="topbar.jsp"/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <c:import url="track-buttons.jsp"/>
            <c:if test="${ not empty requestScope.entities }">
                <fmt:message key="label.tracks"/>
                <table>
                    <tbody>
                    <c:forEach var="track" items="${ requestScope.entities }">
                        <tr class="table-bg-light">
                            <c:if test="${ sessionScope.user.admin eq true}">
                                <td><c:out value="${ track.id }"/></td>
                            </c:if>
                            <td>
                                <form action="<c:url value="/controller"/>" method="post">
                                    <input type="hidden" name="command" value="track-detail">
                                    <input type="hidden" name="id" value="${ track.id }">
                                    <input type="submit" class="btn btn-light" name="submit" value="${ track.name }">
                                </form>
                            </td>
                            <td>
                                <div class="btn-group" role="group" aria-label="Basic example">
                                    <c:forEach var="singer" items="${ track.singers }">
                                        <form action="<c:url value="/controller"/>" method="post">
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
                                <audio controls preload="metadata">
                                    <source src="music/${ track.uuid }" type="audio/mpeg">
                                </audio>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:import url="pagination-bar.jsp"/>
            </c:if>
            <%--            <ctg:track-table tracks="${ requestScope.entities }" admin="${ sessionScope.user.admin }"--%>
            <%--                             current="${ requestScope.current }" commandValue="show-all-tracks"--%>
            <%--                             nextUnavailable="${ requestScope.nextunavailable }"--%>
            <%--                             previousUnavailable="${ requestScope.previousunavailable }" pages="${ requestScope.size }"/>--%>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
