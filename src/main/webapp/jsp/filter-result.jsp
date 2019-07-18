<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/admin/filter-result.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title><fmt:message key="label.filter.result"/></title>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1"></div>
        <div class="col-10">
            <c:import url="header.jsp"/>
        </div>
        <div class="col-1">
            <img class="img-responsive" src="music/img/epam-logo.svg" width="100" height="60" alt="">
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
            <h4><fmt:message key="label.filter.result"/></h4>
            <ctg:show-tracks tracks="${ entities }" admin="${ user.admin }" commandValue="filter-track"
                             moreCommandValue="track-detail"
                             nextUnavailable="${ nextunavailable }" previousUnavailable="${ previousunavailable }"/>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="music/img/login-page-image.svg" alt="music app">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
