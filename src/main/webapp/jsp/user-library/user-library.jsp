<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <c:import url="../header.jsp"/>
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
            <c:import url="user-filter-form.jsp"/>
        </div>
        <div class="col-10">
            <c:out value="${ requestScope.violations }"/>
            <table>
                <tbody>
                <c:forEach var="entity" items="${ requestScope.entities }">
                    <tr class="table-bg-light">
                        <td>
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="user-detail">
                                <input type="hidden" name="id" value="${ entity.login }">
                                <input type="submit" class="btn btn-link" name="submit" value="${ entity.login }">
                            </form>
                        </td>
                        <td>
                            <c:if test="${ entity.admin eq true }">
                                <span class="badge badge-secondary"><fmt:message key="role.admin"/></span>
                            </c:if>
                            <c:if test="${ entity.admin eq false }">
                                <span class="badge badge-secondary"><fmt:message key="role.user"/></span>
                            </c:if>
                        </td>
                        <td>
                            <span class="badge badge-secondary"><c:out value="${ entity.email }"/></span>
                        </td>
                        <td>
                            <span class="badge badge-secondary"><c:out value="${ entity.gender }"/></span>
                        </td>
                        <td>
                            <span class="badge badge-secondary"><c:out value="${ entity.birthDate }"/></span>
                        </td>
                        <td>
                            <span class="badge badge-secondary"><c:out value="${ entity.registrationDate }"/></span>
                        </td>
                        <td>
                            <span class="badge badge-secondary"><fmt:message key="label.playlists.total"/>::
                                <c:out value="${ entity.getPlaylistsQuantity() }"/></span>
                        </td>
                        <td>
                            <c:if test="${ entity.active eq true }">
                                <span class="badge badge-secondary"><fmt:message key="status.active"/></span>
                            </c:if>
                            <c:if test="${ entity.active eq false }">
                                <span class="badge badge-secondary"><fmt:message key="status.nonactive"/></span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
