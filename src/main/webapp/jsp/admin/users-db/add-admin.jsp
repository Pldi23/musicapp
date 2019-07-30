<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"
               scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/admin/add-admin.jsp" scope="request"/>
<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
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
            <form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate
                  style="margin-left: 15%; margin-right: 15%">
                <input type="hidden" name="command" value="register-admin"/>
                <div class="form-group">
                    <label><fmt:message key="label.enter.login"/> </label>:
                    <input type="text" class="form-control" name="login" pattern="^[(\w)-]{4,20}" required
                           placeholder="<fmt:message key="placeholder.login"/>"
                           title="<fmt:message key="prescription.login"/>"/>

                    <div class="invalid-feedback">
                        <fmt:message key="prescription.login"/>
                    </div>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.enter.password"/></label> :
                        <input type="password" class="form-control" name="password" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}"
                               required="" placeholder="<fmt:message key="placeholder.password"/>"
                               title="<fmt:message key="prescription.password"/>"/>

                    <div class="invalid-feedback">
                        <fmt:message key="prescription.password"/>
                    </div>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.enter.dateofbirth"/></label>
                        <input id="inputBirthDate" class="form-control" type="date" name="birthdate" required="required" value="2000-01-01"
                               title="<fmt:message key="prescription.dateofbirth"/>"/>

                    <div class="invalid-feedback">
                        <fmt:message key="prescription.dateofbirth"/>
                    </div>
                    <c:import url="../../common/maxdate.jsp"/>
                    <script>
                        validateDateField("inputBirthDate");
                    </script>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.gender"/></label>
                        <select name="gender" class="custom-select my-1 mr-sm-2">
                            <option value="male"><fmt:message key="option.male"/></option>
                            <option value="female"><fmt:message key="option.female"/></option>
                        </select>

                </div>
                <div class="form-group">
                    <label><fmt:message key="label.enter.firstname"/></label>
                        <input type="text" class="form-control" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required=""
                               placeholder="<fmt:message key="placeholder.firstname"/>"
                               title="<fmt:message key="title.firstname"/>"/>

                    <div class="invalid-feedback">
                        <fmt:message key="title.firstname"/>
                    </div>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.enter.lastname"/></label>
                        <input type="text" class="form-control" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required
                               placeholder="<fmt:message key="placeholder.lastname"/>"
                               title="<fmt:message key="title.lastname"/>"/>

                    <div class="invalid-feedback">
                        <fmt:message key="title.lastname"/>
                    </div>
                </div>
                <div class="form-group">
                    <label><fmt:message key="label.email"/></label>
                        <input type="email" class="form-control" name="email" required placeholder="<fmt:message key="placeholder.email"/>"
                               title="<fmt:message key="title.email"/>"/>

                    <div class="invalid-feedback">
                        <fmt:message key="title.email"/>
                    </div>
                </div>
                <input type="submit" class="btn btn-outline-dark" name="submit"
                       value="<fmt:message key="button.register.admin"/>">
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
