<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/admin-main.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.welcome"/></title></head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
            <c:import url="../common/locale-form.jsp"/>
        </div>
        <div class="col-10">
            <c:import url="../common/header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-4">
            <h3><fmt:message key="label.welcome"/></h3>
        </div>
        <div class="col-8">
            <h3><fmt:message key="label.suggestions"/></h3>
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <ctg:print-tracks head="false" tracks="${ requestScope.tracks }"/>
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