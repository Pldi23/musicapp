<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" scope="session"/>
<fmt:setBundle basename="pagecontent" />
<html><head><title><fmt:message key="label.library"/></title></head>
<body>
<c:import url="../music-lib/topbar.jsp"/>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <p class="text-info">${ requestScope.process }</p>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/primary-logo.svg"/>" alt="music app">
            <c:import url="../common/search-form.jsp"/>
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
