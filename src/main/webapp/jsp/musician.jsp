<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/musician.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${ musician.name }</title>
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
                <li class="list-inline-item"><h3><c:out value="${ musician.name }"/></h3></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="badge.quantity"/> :: <c:out value="${ size }"/></h5></li>
                <li class="list-inline-item"><h5 class="text-muted"><fmt:message key="label.filter.genre"/> :: <c:out value="${ genre }"/></h5></li>
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
            <table>
                <c:forEach var="track" items="${ entities }" varStatus="status">
                    <tr>
                        <td>
                            <form action="controller" method="get">
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
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
