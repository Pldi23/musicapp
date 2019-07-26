<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head><title><fmt:message key="label.musicianlibrary"/></title></head>
<body>
<c:import url="topbar.jsp"/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <div class="btn-toolbar" role="toolbar">
                <div class="btn-group mr-2" role="group" aria-label="First group">
                    <form action="<c:url value="/controller"/>" method="post">
                        <input type="hidden" name="command" value="sort-musician-by-name">
                        <input type="hidden" name="current" value="0">
                        <input type="submit" name="submit" class="btn btn-outline-dark"
                               value="<fmt:message key="button.sort.musician.name"/>">
                    </form>
                </div>
                <c:if test="${ sessionScope.user.admin eq true}">
                    <div class="btn-group" role="group" aria-label="Second group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="sort-musician-by-id">
                            <input type="hidden" name="current" value="0">
                            <input type="submit" name="submit" class="btn btn-outline-dark"
                                   value="<fmt:message key="button.sort.musician.id"/>">
                        </form>
                    </div>
                </c:if>
            </div>
            <c:if test="${ not empty requestScope.entities }">
                <fmt:message key="label.musicians"/>
                <table>
                    <tbody>
                    <c:forEach var="musician" items="${ requestScope.entities }">

                        <tr class="table-bg-light">
                            <c:if test="${ sessionScope.user.admin eq true}">
                                <td><c:out value="${ musician.id }"/></td>
                            </c:if>
                            <td>
                                <form action="<c:url value="/controller"/>" method="post">
                                    <input type="hidden" name="command" value="musician-detail">
                                    <input type="hidden" name="id" value="${ musician.id }">
                                    <input type="submit" class="btn btn-light" name="submit" value="${ musician.name }">
                                </form>
                            </td>
                            <td><span class="badge badge-info">
                                <fmt:message key="badge.quantity"/>::<c:out
                                    value="${ requestScope.trackssize[musician.id] }"/>
                            </span></td>
                            <td><span class="badge badge-info">
                                <fmt:message key="label.filter.genre"/>::<c:out
                                    value="${ requestScope.genre[musician.id] }"/>
                            </span></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:import url="pagination-bar.jsp"/>
            </c:if>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
