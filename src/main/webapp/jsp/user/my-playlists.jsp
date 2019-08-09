<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/user/my-playlists.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
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
            <c:import url="../common/header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<c:if test="${ sessionScope.user.admin eq true }">
    <c:import url="../music-lib/libbar.jsp"/>
    <hr/>
</c:if>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <p class="text-info">${ requestScope.process }</p>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <c:if test="${ sessionScope.user.admin eq false}">
                <form action="<c:url value="/controller"/>" method="post" class="form-inline">
                    <input type="hidden" name="command" value="playlist-create">
                    <div class="form-group mx-sm-3 mb-2" style="padding-top: 8px">
                        <input type="text" class="form-control" name="name" minlength="2" maxlength="50"
                               required="required" placeholder="<fmt:message key="label.new.playlist.name"/>">
                    </div>
                    <input type="submit" class="btn btn-secondary mb-2" name="submit"
                           value="<fmt:message key="button.playlist.create"/> ">
                </form>
                <hr>
            </c:if>
            <c:if test="${ sessionScope.user.admin eq true }">
                <form action="<c:url value="/controller"/>" method="post" class="form-inline">
                    <input type="hidden" name="command" value="playlist-create">
                    <label for="selectForm"><fmt:message key="label.access"/>::</label>
                    <select name="access" id="selectForm" class="form-control">
                        <option value="true"><fmt:message key="option.personal"/></option>
                        <option value="false"><fmt:message key="option.public"/></option>
                    </select>
                    <div class="form-group mx-sm-3 mb-2" style="border-top-width: 8px; margin-top: 8px">
                        <input type="text" class="form-control" style="padding-top: 8px" name="name" minlength="2" maxlength="50"
                               required="required" placeholder="<fmt:message key="label.new.playlist.name"/>">
                    </div>
                    <input type="submit" class="btn btn-secondary mb-2" style="margin-top: 8px" name="submit"
                           value="<fmt:message key="button.playlist.create"/> ">
                </form>
                <hr>
            </c:if>
            <c:if test="${ not empty requestScope.playlists }"><fmt:message key="label.playlists.total"/> :: ${ requestScope.size } </c:if>
            <table>
                <c:forEach var="playlist" items="${ requestScope.playlists }">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${ playlist.personal }">
                                    <span class="badge badge-secondary" style="margin-bottom: 12px"><fmt:message key="badge.private"/></span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-secondary" style="margin-bottom: 12px"><fmt:message key="badge.public"/></span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="playlist-detail">
                                <input type="hidden" name="id" value="${ playlist.id }">
                                <input type="submit" class="btn btn-light" name="submit"
                                       value="<c:out value="${ playlist.name }"/>">
                            </form>
                        </td>
                        <td>
                            <form class="form-inline">
                                <div class="form-group">
                                    <span class="badge badge-info">
                                        <fmt:message key="badge.duration"/>::<ctg:duration playlist="${ playlist }"/>
                                    </span>
                                </div>
                                <div class="form-group">
                                    <span class="badge badge-info">
                                        <fmt:message key="badge.quantity"/>::<c:out value="${ playlist.getSize() }"/></span>
                                </div>
                                <div class="form-group">
                                    <span class="badge badge-info">
                                        <fmt:message key="badge.genre"/>::<c:out value="${ playlist.getMostPopularGenreName() }"/></span>
                                </div>
                            </form>
                        </td>
                        <c:if test="${ sessionScope.user.admin eq true }">
                            <td>
                                <form action="<c:url value="/controller"/>" method="post">
                                    <input type="hidden" name="command" value="change-access">
                                    <input type="hidden" name="id" value="${ playlist.id }">
                                    <input type="submit" class="btn btn-outline-info btn-sm" name="submit"
                                           value="<fmt:message key="button.update.access"/>">
                                </form>
                            </td>
                        </c:if>
                        <td>
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="remove-my-playlist">
                                <input type="hidden" name="id" value="${ playlist.id }">
                                <input type="submit" class="btn btn-danger btn-sm" name="submit"
                                       value="<fmt:message key="button.remove"/>">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/primary-logo.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
