<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="page" value="/jsp/common/login.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.main"/></title>
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
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container container-fluid bg-light">
    <div class="row">
        <div class="col-8">
            <p class="text-warning">${ requestScope.validatorMessage }</p>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-warning">${requestScope.errorLoginPassMessage}</p>
            <form action="<c:url value="/controller"/>" method="post">
                <div class="form-group">
                    <input type="hidden" name="command" value="login"/>
                    <label for="exampleInputEmail1"><fmt:message key="label.enter.login"/></label>
                    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                           name="login"
                           pattern="^[(\w)-]{4,20}" required="" placeholder="<fmt:message key="placeholder.login"/>"
                           title="<fmt:message key="prescription.login"/>"/>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1"><fmt:message key="label.enter.password"/></label>
                    <input type="password" class="form-control" id="exampleInputPassword1" name="password"
                           pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required=""
                           placeholder="<fmt:message key="placeholder.password"/>"
                           title="<fmt:message key="prescription.password"/>"/>

                </div>
                <div class="form-group">
                    <input type="submit" name="submit" class="btn btn-outline-dark"
                           value="<fmt:message key="button.login"/> ">
                </div>
            </form>
        </div>
        <div class="col-4">
            <img src="<c:url value="/resources/login-page-image.svg"/>" alt="music app"
                 width="320" height="320">
        </div>
    </div>
</div>
<hr>
<fmt:message var="label" key="label.new.arrivals" scope="page"/>
<ctg:print-tracks head="true" tracks="${ requestScope.tracks }" label="${ label }"/>
<c:import url="footer.jsp"/>
</body>
</html>
