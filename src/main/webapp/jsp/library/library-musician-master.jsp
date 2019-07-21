<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/library/library-musician.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head><title><fmt:message key="label.musicianlibrary"/></title></head>
<body>
<c:import url="library-master.jsp"/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <div class="btn-toolbar" role="toolbar">
                <div class="btn-group mr-2" role="group" aria-label="First group">
                    <ctg:command-form commandValue="sort-musician-by-name" submitValue="button.sort.musician.name"/>
                </div>
                <c:if test="${ user.admin eq true}">
                    <div class="btn-group" role="group" aria-label="Second group">
                        <ctg:command-form commandValue="sort-musician-by-id" submitValue="button.sort.musician.id"/>
                    </div>
                </c:if>
            </div>
            <c:if test="${ not empty entities }">
                <fmt:message key="label.musicians"/>
                <table>
                    <tbody>
                    <c:forEach var="musician" items="${ entities }">
                        <tr class="table-bg-light">
                            <c:if test="${ user.admin eq true}">
                                <td><c:out value="${ musician.id }"/></td>
                            </c:if>
                            <td>
                                <form action="controller" method="get">
                                    <input type="hidden" name="command" value="musician-detail">
                                    <input type="hidden" name="id" value="${ musician.id }">
                                    <input type="submit" class="btn btn-light" name="submit" value="${ musician.name }">
                                </form>
                            </td>
                            <td><span class="badge badge-info">
<%--                                    <fmt:message key="badge.duration"/>::<c:out value="${ playlist.getTotalDuration() }"/>--%>
                            </span></td>
                            <td><span class="badge badge-info">
<%--                                <fmt:message key="badge.quantity"/>::<c:out value="${ playlist.getSize() }"/>--%>
                            </span></td>
                            <td><span class="badge badge-info">
<%--                                <fmt:message key="label.filter.genre"/>::<c:out value="${ playlist.getMostPopularGenre() }"/>--%>
                            </span></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${ nextunavailable eq 'false'}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                        <input type="hidden" name="direction" value="next">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="next">
                    </form>
                </c:if>
                <c:if test="${ previousunavailable eq 'false' }">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                        <input type="hidden" name="direction" value="previous">
                        <input type="submit" class="btn btn-outline-dark" name="submit" value="previous">
                    </form>
                </c:if>
            </c:if>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
${violations}
${removeResult}
${updateResult}
<c:import url="../footer.jsp"/>
</body>
</html>
