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
            <p class="text-warning"><c:out value="${ requestScope.validatorMessage }"/></p>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-warning"><c:out value="${requestScope.errorLoginPassMessage}"/></p>
            <c:if test="${ not empty requestScope.process }">
                <div class="alert alert-info" role="alert">
                    <span><c:out value="${ requestScope.process }"/></span>
                </div>
            </c:if>
            <form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
                <div class="form-group">
                    <input type="hidden" name="command" value="login"/>
                    <label for="exampleInputEmail1"><fmt:message key="label.enter.login"/></label>
                    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                           name="login"
                           pattern="^[(\w)-]{4,20}" required="" placeholder="<fmt:message key="placeholder.login"/>"
                           title="<fmt:message key="prescription.login"/>" />
                    <div class="invalid-feedback">
                        <fmt:message key="prescription.login"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1"><fmt:message key="label.enter.password"/></label>
                    <input type="password" class="form-control" id="exampleInputPassword1" name="password"
                           pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                           required=""
                           placeholder="<fmt:message key="placeholder.password"/>"
                           title="<fmt:message key="prescription.password"/>"/>
                    <div class="invalid-feedback">
                        <fmt:message key="prescription.password"/>
                    </div>

                </div>
                <div class="form-group">
                    <input type="submit" name="submit" class="btn btn-outline-dark"
                           value="<fmt:message key="button.login"/> ">
                </div>
            </form>
        </div>
        <div class="col-4">
            <img src="<c:url value="/resources/primary-logo.svg"/>" alt="music app"
                 width="320" height="320">
        </div>
    </div>
</div>
<hr>
<div class="container container-fluid bg-light">
    <div class="row">
        <div class="col-8">
            <h3 class="text-info"><fmt:message key="label.new.arrivals"/></h3>
            <c:import url="print-tracks.jsp"/>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
