<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.user.db"/></title>
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
<div class="container w-100 bg-light">
    <div class="row">
        <div class="col-2">
        </div>
        <div class="col-8">
            <div class="btn-toolbar" role="toolbar">
                <div class="btn-group mr-2" role="group" aria-label="First group">
                    <form action="<c:url value="/controller"/>" method="get">
                        <input type="hidden" name="command" value="to-register-admin">
                        <input type="submit" name="submit" value="<fmt:message key="button.admin.new"/>"
                               class="btn btn-secondary">
                    </form>
                </div>
            </div>
        </div>
        <div class="col-2">
        </div>
    </div>
</div>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="user-filter-form.jsp"/>
        </div>
        <div class="col-9">
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <div class="table-responsive-sm">
            <table class="table table-hover table-sm text-center" style="height: 100px;">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="option.user"/></th>
                    <th scope="col"><fmt:message key="label.role"/></th>
                    <th scope="col"><fmt:message key="placeholder.email"/></th>
                    <th scope="col"><fmt:message key="label.profile.registrationdate"/></th>
                    <th scope="col"><fmt:message key="label.playlists"/></th>
                    <th scope="col"><fmt:message key="label.status"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entity" items="${ requestScope.entities }">
                    <tr class="table-bg-light">
                        <td>
                            <form action="<c:url value="/controller"/>" method="post" class="align-middle">
                                <input type="hidden" name="command" value="user-detail">
                                <input type="hidden" name="id" value="${ entity.login }">
                                <input type="submit" class="btn btn-link float-left" name="submit" value="${ entity.login }">
                            </form>
                        </td>
                        <td class="align-middle">
                            <c:if test="${ entity.admin eq true }">
                                <span class="badge badge-info "><fmt:message key="role.admin"/></span>
                            </c:if>
                            <c:if test="${ entity.admin eq false }">
                                <span class="badge badge-secondary"><fmt:message key="role.user"/></span>
                            </c:if>
                        </td class="align-middle">
                        <td class="align-middle">
                            <span class="badge badge-secondary"><c:out value="${ entity.email }"/></span>
                        </td>
                        <td class="align-middle">
                            <span class="badge badge-secondary">
                                <c:out value="${ entity.registrationDate }"/></span>
                        </td>
                        <td class="align-middle">
                            <span class="badge badge-secondary"><fmt:message key="label.playlists.total"/>::
                                <c:out value="${ entity.getPlaylistsQuantity() }"/></span>
                        </td>
                        <td class="align-middle">
                            <c:if test="${ entity.active eq true }">
                                <span class="badge badge-success"><fmt:message key="status.active"/></span>
                            </c:if>
                            <c:if test="${ entity.active eq false }">
                                <span class="badge badge-danger"><fmt:message key="status.nonactive"/></span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>

            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item"><c:if test="${ requestScope.previousunavailable eq 'false' }">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                            <input type="hidden" name="direction" value="previous">
                            <input type="hidden" name="current" value="${ requestScope.current }">
                            <c:if test="${ requestScope.pageCommand eq 'filter-user'}">
                                <input type="hidden" name="login" value="${ requestScope.filter.login }">
                                <input type="hidden" name="role" value="${ requestScope.filter.role }">
                                <input type="hidden" name="firstname" value="${ requestScope.filter.firstname }">
                                <input type="hidden" name="lastname" value="${ requestScope.filter.lastname }">
                                <input type="hidden" name="email" value="${ requestScope.filter.email }">
                                <input type="hidden" name="birthDateFrom"
                                       value="${ requestScope.filter.birthdateFrom }">
                                <input type="hidden" name="birthDateTo" value="${ requestScope.filter.birthdateTo }">
                                <input type="hidden" name="createdFrom"
                                       value="${ requestScope.filter.registrationFrom }">
                                <input type="hidden" name="createdTo" value="${ requestScope.filter.regisrationTo }">
                            </c:if>
                            <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.previous"/>">
                        </form>
                    </c:if></li>
                    <li class="page-item">
                        <c:if test="${ requestScope.nextunavailable eq 'false'}">
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                                <input type="hidden" name="direction" value="next">
                                <input type="hidden" name="current" value="${ requestScope.current }">
                                <c:if test="${ requestScope.pageCommand eq 'filter-user'}">
                                    <input type="hidden" name="login" value="${ requestScope.filter.login }">
                                    <input type="hidden" name="role" value="${ requestScope.filter.role }">
                                    <input type="hidden" name="firstname" value="${ requestScope.filter.firstname }">
                                    <input type="hidden" name="lastname" value="${ requestScope.filter.lastname }">
                                    <input type="hidden" name="email" value="${ requestScope.filter.email }">
                                    <input type="hidden" name="birthDateFrom"
                                           value="${ requestScope.filter.birthdateFrom }">
                                    <input type="hidden" name="birthDateTo" value="${ requestScope.filter.birthdateTo }">
                                    <input type="hidden" name="createdFrom"
                                           value="${ requestScope.filter.registrationFrom }">
                                    <input type="hidden" name="createdTo" value="${ requestScope.filter.regisrationTo }">
                                </c:if>
                                <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.next"/>">
                            </form>
                        </c:if>
                    </li>
                </ul>
            </nav>
            <c:if test="${ not empty requestScope.entities and not empty requestScope.size[1] }">
                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                        <c:forEach var="element" items="${ requestScope.size }" varStatus="loop">
                            <c:choose>
                                <c:when test="${ element eq requestScope.current }">
                                    <li class="page-item active" aria-current="page">
                                        <form action="<c:url value="/controller"/>" method="post">
                                            <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                                            <input type="hidden" name="direction" value="direct">
                                            <input type="hidden" name="current" value="${ element }">
                                            <c:if test="${ requestScope.pageCommand eq 'filter-user'}">
                                                <input type="hidden" name="login" value="${ requestScope.filter.login }">
                                                <input type="hidden" name="role" value="${ requestScope.filter.role }">
                                                <input type="hidden" name="firstname" value="${ requestScope.filter.firstname }">
                                                <input type="hidden" name="lastname" value="${ requestScope.filter.lastname }">
                                                <input type="hidden" name="email" value="${ requestScope.filter.email }">
                                                <input type="hidden" name="birthDateFrom"
                                                       value="${ requestScope.filter.birthdateFrom }">
                                                <input type="hidden" name="birthDateTo" value="${ requestScope.filter.birthdateTo }">
                                                <input type="hidden" name="createdFrom"
                                                       value="${ requestScope.filter.registrationFrom }">
                                                <input type="hidden" name="createdTo" value="${ requestScope.filter.regisrationTo }">
                                            </c:if>
                                            <input type="submit" class="page-link" name="submit"
                                                   value="${ element }">
                                        </form>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item">
                                        <form action="<c:url value="/controller"/>" method="post">
                                            <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                                            <input type="hidden" name="direction" value="direct">
                                            <input type="hidden" name="current" value="${ element }">
                                            <c:if test="${ requestScope.pageCommand eq 'filter-user'}">
                                                <input type="hidden" name="login" value="${ requestScope.filter.login }">
                                                <input type="hidden" name="role" value="${ requestScope.filter.role }">
                                                <input type="hidden" name="firstname" value="${ requestScope.filter.firstname }">
                                                <input type="hidden" name="lastname" value="${ requestScope.filter.lastname }">
                                                <input type="hidden" name="email" value="${ requestScope.filter.email }">
                                                <input type="hidden" name="birthDateFrom"
                                                       value="${ requestScope.filter.birthdateFrom }">
                                                <input type="hidden" name="birthDateTo" value="${ requestScope.filter.birthdateTo }">
                                                <input type="hidden" name="createdFrom"
                                                       value="${ requestScope.filter.registrationFrom }">
                                                <input type="hidden" name="createdTo" value="${ requestScope.filter.regisrationTo }">
                                            </c:if>
                                            <input type="submit" class="page-link" name="submit"
                                                   value="${ element }">
                                        </form>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>
<c:import url="../../common/footer.jsp"/>
</body>
</html>
