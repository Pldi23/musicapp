<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head><title><fmt:message key="label.tracklibrary"/></title></head>
<body>
<c:import url="topbar.jsp"/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <c:import url="track-buttons.jsp"/>
            <p class="text-info">${requestScope.removeResult}</p>
            <p class="text-info">${requestScope.updateResult}</p>
            <c:set var="additionalButtons" value="${ sessionScope.user.admin }"/>
            <ctg:show-tracks tracks="${ requestScope.entities }" commandValue="sort-track-by-id"
                             nextUnavailable="${ requestScope.nextunavailable }"
                             previousUnavailable="${ requestScope.previousunavailable }" admin="${ sessionScope.user.admin }"/>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>