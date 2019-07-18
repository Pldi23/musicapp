<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="page" value="/jsp/main.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.welcome"/></title>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
            <c:import url="locale-form.jsp"/>
        </div>
        <div class="col-10">
            <c:import url="header.jsp"/>
        </div>
        <div class="col-1">
            <img src="music/img/epam-logo.svg" width="100" height="60" alt="">
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
            <c:import url="track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <ctg:top-tracks head="false"/>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="music/img/login-page-image.svg" alt="music app">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>