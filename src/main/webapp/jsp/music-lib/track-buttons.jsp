<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>--%>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<div class="btn-toolbar" role="toolbar">
    <div class="btn-group mr-2" role="group" aria-label="First group">
        <form action="<c:url value="/controller"/>" method="post">
            <input type="hidden" name="command" value="sort-track-by-name">
            <input type="hidden" name="current" value="0">
            <input type="submit" name="submit" class="btn btn-outline-dark"
                   value="<fmt:message key="button.sort.track.name"/>">
        </form>
    </div>
    <div class="btn-group mr-2" role="group" aria-label="Second group">
        <form action="<c:url value="/controller"/>" method="post">
            <input type="hidden" name="command" value="sort-track-by-genre">
            <input type="hidden" name="current" value="0">
            <input type="submit" name="submit" class="btn btn-outline-dark"
                   value="<fmt:message key="button.sort.track.genre"/>">
        </form>
    </div>
    <c:if test="${ sessionScope.user.admin eq true}">
        <div class="btn-group mr-2" role="group" aria-label="Third group">
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="sort-track-by-id">
                <input type="hidden" name="current" value="0">
                <input type="submit" name="submit" class="btn btn-outline-dark"
                       value="<fmt:message key="button.sort.track.id"/>">
            </form>
        </div>
    </c:if>
    <div class="btn-group mr-2" role="group" aria-label="Fourth group">
        <form action="<c:url value="/controller"/>" method="post">
            <input type="hidden" name="command" value="sort-track-by-length">
            <input type="hidden" name="current" value="0">
            <input type="submit" name="submit" class="btn btn-outline-dark"
                   value="<fmt:message key="button.sort.track.length"/>">
        </form>
    </div>
</div>
</body>
</html>
