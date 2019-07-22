<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" scope="session"/>
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/add-admin.jsp" scope="request"/>
<html>
<head>
    <title><fmt:message key="label.admin"/></title>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
            <img src="<c:url value="/resources/users-icon.svg"/>" width="100" height="60" alt="">
        </div>
        <div class="col-10">
            <c:import url="../../common/header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="user-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-warning"><c:out value="${ requestScope.process }"/></p>
            <form action = "<c:url value="/controller"/>" method = "post" >
                <input type="hidden" name="command" value="register-admin"/>
                <label>
                    <fmt:message key="label.enter.login"/> :
                    <input type="text" name="login" pattern="^[(\w)-]{4,20}" required placeholder="<fmt:message key="placeholder.login"/>"
                           title="<fmt:message key="prescription.login"/>"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.enter.password"/> :
                    <input type="password" name="password" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="" placeholder="<fmt:message key="placeholder.password"/>"
                           title="<fmt:message key="prescription.password"/>"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.enter.dateofbirth"/>
                    <input type="date" name="birthdate" required="required" value="1970-01-01" title="<fmt:message key="prescription.dateofbirth"/>"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.gender"/>
                    <select name="gender">
                        <option value="male"><fmt:message key="option.male"/></option>
                        <option value="female"><fmt:message key="option.female"/></option>
                    </select>
                </label>
                <br>
                <label>
                    <fmt:message key="label.enter.firstname"/>
                    <input type="text" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required="" placeholder="<fmt:message key="placeholder.firstname"/>"
                           title="<fmt:message key="title.firstname"/>"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.enter.lastname"/>
                    <input type="text" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required placeholder="<fmt:message key="placeholder.lastname"/>"
                           title="<fmt:message key="title.lastname"/>"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.email"/>
                    <input type="email" name="email" required placeholder="<fmt:message key="placeholder.email"/>" title="<fmt:message key="title.email"/>"/>
                </label>
                <br>
                <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.register"/>">
                <br/>
            </form>
        </div>
        <div class="col-2">
        </div>
    </div>
</div>
<c:import url="../../common/footer.jsp"/>
</body>
</html>