<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/common/musician.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${ requestScope.musician.name }</title>
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
        <div class="col-8">
            <ul class="list-inline">
                <li class="list-inline-item"><h3><c:out value="${ requestScope.musician.name }"/></h3></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="badge.quantity"/> :: <c:out value="${ requestScope.size }"/></h5></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="label.filter.genre"/> :: <c:out value="${ requestScope.genre }"/></h5></li>
            </ul>
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
            <c:if test="${ sessionScope.user.admin eq true }">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="to-update-musician">
                    <input type="hidden" name="entityType" value="musician">
                    <input type="hidden" name="id" value="${ requestScope.musician.id }">
                    <input type="submit" class="btn btn-outline-info" name="submit"
                           value="<fmt:message key="button.update"/>">
                </form>
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="to-remove-musician">
                    <input type="hidden" name="id" value="${ requestScope.musician.id }">
                    <input type="hidden" name="entityType" value="musician">
                    <input type="submit" class="btn btn-outline-danger" name="submit"
                           value="<fmt:message key="button.remove"/>">
                </form>
            </c:if>
            <table>
                <c:forEach var="track" items="${ requestScope.entities }" varStatus="status">
                    <tr>
                        <td>
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="track-detail">
                                <input type="hidden" name="id" value="${ track.id }">
                                <input type="submit" class="btn btn-light" name="submit" value="${ track.name }">
                            </form>
                        </td>
                        <td>
                            <audio controls preload="metadata" id="${ status.index }" onplay="setCookie('lastPlayed', '${ track.id }')">
                                <source src="music/${track.uuid}" type="audio/mpeg">
                            </audio>
                        </td>
                        <td>
                            <span class="badge badge-secondary"><c:out value="${ track.genre.title }"/></span>
                        </td>
                        <td>
                            <span class="badge badge-pill badge-dark"><c:out value="${ track.releaseDate }"/></span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-2">
            <c:import url="search-form.jsp"/>
            <img class="img-fluid" src="<c:url value="/resources/primary-logo.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
