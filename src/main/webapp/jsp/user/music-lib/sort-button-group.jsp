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
        <ctg:command-form commandValue="sort-track-by-name" submitValue="button.sort.track.name"/>
    </div>
    <div class="btn-group mr-2" role="group" aria-label="Second group">
        <ctg:command-form commandValue="sort-track-by-genre" submitValue="button.sort.track.genre"/>
    </div>
    <c:if test="${ sessionScope.user.admin eq true}">
        <div class="btn-group" role="group" aria-label="Third group">
            <ctg:command-form commandValue="sort-track-by-id" submitValue="button.sort.track.id"/>
        </div>
    </c:if>
    <div class="btn-group mr-2" role="group" aria-label="Fourth group">
        <ctg:command-form commandValue="sort-track-by-length" submitValue="button.sort.track.length"/>
    </div>
</div>
</body>
</html>
