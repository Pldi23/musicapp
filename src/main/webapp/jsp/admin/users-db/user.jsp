<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><c:out value="${ requestScope.entity.login }"/></title>
    <style type="text/css">
        .my-custom-scrollbar {
            position: relative;
            height: 200px;
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
        <div class="col-1">
            <img src="<c:url value="/resources/users-icon.svg"/>" width="100" height="60" alt="">
        </div>
        <div class="col-10">
            <c:import url="../../common/header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
        </div>
        <div class="col-8">
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-warning"><c:out value="${ requestScope.process }"/></p>
            <div class="jumbotron">
                <div class="img"><img src="music/${ requestScope.entity.photoPath }"
                                      alt="${ requestScope.entity.login }"
                                      class="rounded-circle" width="200" height="200">
                </div>
                <h1 class="display-4"><c:out value="${ requestScope.entity.login }"/></h1>
                <p class="lead"><c:out
                        value="${ requestScope.entity.firstname } ${ requestScope.entity.lastname }"/></p>
                <p class="lead">
                    <c:if test="${ requestScope.entity.admin eq true }">
                    <span class="badge badge-secondary"><fmt:message key="role.admin"/></span>
                    </c:if>
                    <c:if test="${ requestScope.entity.admin eq false }">
                    <span class="badge badge-secondary"><fmt:message key="role.user"/></span>
                    </c:if>
                    <span class="badge badge-secondary"><c:out value="${ requestScope.entity.email }"/></span>
                    <c:if test="${ requestScope.entity.gender eq 'MALE' }">
                    <span class="badge badge-secondary"><fmt:message key="option.male"/></span>
                    </c:if>
                    <c:if test="${ requestScope.entity.gender eq 'FEMALE' }">
                    <span class="badge badge-secondary"><fmt:message key="option.female"/></span>
                    </c:if>
                    <span class="badge badge-secondary"><fmt:message key="label.profile.birthdate"/>::<c:out
                            value="${ requestScope.entity.birthDate }"/></span>
                    <span class="badge badge-secondary"><fmt:message key="label.profile.registrationdate"/>::<c:out
                            value="${ requestScope.entity.registrationDate }"/></span>
                    <span class="badge badge-secondary"><fmt:message key="label.playlists.total"/>::
                                <c:out value="${ requestScope.entity.getPlaylistsQuantity() }"/></span>
                    <c:if test="${ requestScope.entity.active eq true }">
                    <span class="badge badge-success"><fmt:message key="status.active"/></span>
                    </c:if>
                    <c:if test="${ requestScope.entity.active eq false }">
                    <span class="badge badge-warning"><fmt:message key="status.nonactive"/></span>
                    </c:if>
                    <fmt:parseDate value="${ sessionScope.user.paidPeriod }" pattern="yyyy-MM-dd" var="parsedDate"
                                   type="date"/>
                    <fmt:formatDate value="${ parsedDate }" var="newParsedDate" type="date" pattern="dd.MM.yyyy"/>
                <div class="alert alert-info" role="alert">
                    <span><fmt:message key="label.paid.period"/>: ${ newParsedDate }</span>
                </div>
                </p>
                <hr class="my-4">
                <c:if test="${ not empty requestScope.entity.playlists }">
                    <fmt:message key="label.playlists"/>
                    <table>
                        <tbody>
                        <c:forEach var="playlist" items="${ requestScope.entity.playlists }">
                            <tr class="table-bg-light">
                                <c:if test="${ sessionScope.user.admin eq true}">
                                    <td><c:out value="id:: ${ playlist.id }"/></td>
                                </c:if>
                                <td>
                                    <form action="<c:url value="/controller"/>" method="post">
                                        <input type="hidden" name="command" value="playlist-detail">
                                        <input type="hidden" name="id" value="${ playlist.id }">
                                        <input type="submit" class="btn btn-outline-secondary" name="submit"
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
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${ not empty requestScope.entity.payments }">
                    <fmt:message key="label.payments"/>
                    <div class="table-wrapper-scroll-y my-custom-scrollbar">
                        <table class="table table-bordered table-striped mb-0" style="">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col"><fmt:message key="table.amount"/></th>
                                <th scope="col"><fmt:message key="table.card"/></th>
                                <th scope="col"><fmt:message key="table.date"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="payment" items="${ requestScope.entity.payments }">
                                <tr class="table-bg-light">
                                    <td>
                                        <c:out value="${ payment.id}"/>
                                    </td>
                                    <td>
                                        <c:out value="${ payment.amount }"/>
                                    </td>
                                    <td>
                                        <c:out value="${ payment.cardNumber}"/>
                                    </td>
                                    <td>
                                        <fmt:parseDate value="${ payment.dateTime }" pattern="yyyy-MM-dd"
                                                       var="parsedDate"
                                                       type="date"/>
                                        <fmt:formatDate value="${ parsedDate }" var="newParsedDate" type="date"
                                                        pattern="dd.MM.yyyy"/>
                                        <c:out value="${ newParsedDate }"/>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-2">
        </div>
    </div>
</div>
<c:import url="../../common/footer.jsp"/>
</body>
</html>
