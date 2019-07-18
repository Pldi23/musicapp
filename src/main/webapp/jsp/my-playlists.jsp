<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="/jsp/my-playlists.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.playlists"/></title>
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
            <c:import url="track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <p class="text-info">${ process }</p>
            <form action="controller" method="get" class="form-inline">
                <input type="hidden" name="command" value="playlist-create">
                <div class="form-group mx-sm-3 mb-2">
                    <input type="text" class="form-control" name="name" minlength="2" maxlength="50"
                           required="required" placeholder="<fmt:message key="label.new.playlist.name"/>">
                </div>
                <input type="submit" class="btn btn-secondary mb-2" name="submit"
                       value="<fmt:message key="button.playlist.create"/> ">
            </form>
            <hr>
            <c:if test="${ not empty playlists }"><fmt:message key="label.playlists.total"/> :: ${ size } </c:if>
            <table>
                <c:forEach var="map" items="${ statistic }">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${ map.key.personal }">
                                    <span class="badge badge-secondary"><fmt:message key="badge.private"/></span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-secondary"><fmt:message key="badge.public"/></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="playlist-detail">
                                <input type="hidden" name="id" value="${ map.key.id }">
                                <input type="submit" class="btn btn-light" name="submit"
                                       value="<c:out value="${ map.key.name }"/>">
                            </form>
                        </td>
                        <td>
                            <form class="form-inline">
                                <div class="form-group">
                                    <span class="badge badge-info"><fmt:message key="badge.duration"/></span>
                                    <h6><c:out value="${ map.value[0] }"/></h6>
                                </div>
                                <div class="form-group">
                                    <span class="badge badge-info"><fmt:message key="badge.quantity"/></span>
                                    <h6><c:out value="${ map.value[1] }"/></h6>
                                </div>
                            </form>
                        </td>
                        <td>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="remove-my-playlist">
                                <input type="hidden" name="id" value="${ map.key.id }">
                                <input type="submit" class="btn btn-danger btn-sm" name="submit"
                                       value="<fmt:message key="button.remove"/>">
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
