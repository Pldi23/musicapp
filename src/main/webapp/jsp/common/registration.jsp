<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/common/registration.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
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
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container container-fluid bg-light">
    <div class="row">
        <div class="col-8">
            <h2><fmt:message key="registration.message"/></h2>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-warning"><c:out value="${ requestScope.process }"/></p>
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="register"/>
                <div class="form-group">
                    <label for="inputLogin"><fmt:message key="label.enter.login"/></label>
                    <input type="text" class="form-control" id="inputLogin" aria-describedby="loginHelp"
                           name="login"
                           pattern="^[(\w)-]{4,20}" required="" placeholder="<fmt:message key="placeholder.login"/>"
                           title="<fmt:message key="prescription.login"/>"/>
                    <small id="loginHelp" class="form-text text-muted"><fmt:message key="prescription.login"/></small>
                </div>
                <div class="form-group">
                    <label for="inputPassword"><fmt:message key="label.enter.password"/></label>
                    <input type="password" class="form-control" id="inputPassword" name="password"
                           pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required=""
                           placeholder="<fmt:message key="placeholder.password"/>"
                           title="<fmt:message key="prescription.password"/>"/>
                    <small id="passwordHelp" class="form-text text-muted"><fmt:message
                            key="prescription.password"/></small>
                </div>
                <div class="form-group">
                    <label for="inputBirthDate"><fmt:message key="label.enter.dateofbirth"/></label>
                    <input type="date" name="birthdate" required="required" value="1970-01-01" min="1900-01-01"
                           title="<fmt:message key="prescription.dateofbirth"/>" class="form-control"
                           id="inputBirthDate"/>
                    <small id="birthDateHelp" class="form-text text-muted"><fmt:message
                            key="prescription.dateofbirth"/></small>
                </div>
                <div class="form-group">
                    <label for="inputGender"><fmt:message key="label.gender"/></label>
                    <select name="gender" id="inputGender" class="form-control">
                        <option value="male"><fmt:message key="option.male"/></option>
                        <option value="female"><fmt:message key="option.female"/></option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="inputFirstname"><fmt:message key="label.enter.firstname"/></label>
                    <input id="inputFirstname" class="form-control" type="text" name="firstname"
                           pattern="[A-Za-zА-Яа-яЁё]{2,30}" required=""
                           placeholder="<fmt:message key="placeholder.firstname"/>"
                           title="<fmt:message key="title.firstname"/>"/>
                </div>
                <div class="form-group">
                    <label for="inputLastname"><fmt:message key="label.enter.lastname"/></label>
                    <input id="inputLastname" class="form-control" type="text" name="lastname"
                           pattern="[A-Za-zА-Яа-яЁё]{2,30}" required=""
                           placeholder="<fmt:message key="placeholder.lastname"/>"
                           title="<fmt:message key="title.lastname"/>"/>
                </div>
                <div class="form-group">
                    <label for="inputEmail"><fmt:message key="label.email"/></label>
                    <input id="inputEmail" class="form-control" type="email" name="email" required=""
                           placeholder="<fmt:message key="placeholder.email"/>"
                           title="<fmt:message key="title.email"/>"/>

                </div>
                <div class="form-group">
                    <input type="submit" name="submit" class="btn btn-outline-dark"
                           value="<fmt:message key="button.register"/>">

                </div>
            </form>
        </div>
        <div class="col-4">
            <img src="<c:url value="/resources/login-page-image.svg"/>" alt="music app"
                 width="320" height="320">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
<script src="../../maxdate.js"></script>
</body>
</html>