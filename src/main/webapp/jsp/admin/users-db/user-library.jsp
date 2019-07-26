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
                    <th scope="col">Username</th>
                    <th scope="col">Role</th>
                    <th scope="col">Email</th>
<%--                    <th scope="col">Gender</th>--%>
<%--                    <th scope="col">Birth date</th>--%>
                    <th scope="col">Registration date</th>
                    <th scope="col">Playlists</th>
                    <th scope="col">Status</th>
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
<%--                            <span class="badge badge-secondary"><c:out value="${ entity.email }"/></span>--%>
                            <span class="badge badge-secondary"><c:out value="${ entity.email }"/></span>
                        </td>
<%--                        <td>--%>
<%--                            <c:if test="${ entity.gender eq 'MALE' }">--%>
<%--                                <span class="badge badge-secondary"><fmt:message key="option.male"/></span>--%>
<%--                            </c:if>--%>
<%--                            <c:if test="${ entity.gender eq 'FEMALE' }">--%>
<%--                                <span class="badge badge-secondary"><fmt:message key="option.female"/></span>--%>
<%--                            </c:if>--%>
<%--                        </td>--%>
<%--                        <td>--%>
<%--                            <span class="badge badge-secondary">--%>
<%--                                <c:out value="${ entity.birthDate }"/></span>--%>
<%--                        </td>--%>
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
            <c:if test="${ requestScope.nextunavailable eq 'false'}">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                    <input type="hidden" name="direction" value="next">
                    <input type="hidden" name="login" value="${ requestScope.login }">
                    <input type="hidden" name="role" value="${ requestScope.role }">
                    <input type="hidden" name="firstname" value="${ requestScope.firstname }">
                    <input type="hidden" name="lastname" value="${ requestScope.lastname }">
                    <input type="hidden" name="email" value="${ requestScope.email }">
                    <input type="hidden" name="birthDateFrom" value="${ requestScope.birthDateFrom }">
                    <input type="hidden" name="birthDateTo" value="${ requestScope.birthDateTo }">
                    <input type="hidden" name="createdFrom" value="${ requestScope.createdFrom }">
                    <input type="hidden" name="createdTo" value="${ requestScope.createdTo }">
                    <input type="submit" class="btn btn-outline-dark" name="submit" value="next">
                </form>
            </c:if>
            <c:if test="${ requestScope.previousunavailable eq 'false' }">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                    <input type="hidden" name="direction" value="previous">
                    <input type="hidden" name="login" value="${ requestScope.login }">
                    <input type="hidden" name="role" value="${ requestScope.role }">
                    <input type="hidden" name="firstname" value="${ requestScope.firstname }">
                    <input type="hidden" name="lastname" value="${ requestScope.lastname }">
                    <input type="hidden" name="email" value="${ requestScope.email }">
                    <input type="hidden" name="birthDateFrom" value="${ requestScope.birthDateFrom }">
                    <input type="hidden" name="birthDateTo" value="${ requestScope.birthDateTo }">
                    <input type="hidden" name="createdFrom" value="${ requestScope.createdFrom }">
                    <input type="hidden" name="createdTo" value="${ requestScope.createdTo }">
                    <input type="submit" class="btn btn-outline-dark" name="submit" value="previous">
                </form>
            </c:if>
        </div>
    </div>
</div>
<c:import url="../../common/footer.jsp"/>
</body>
</html>
