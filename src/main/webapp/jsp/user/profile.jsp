<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/user/profile.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.profile"/></title>
</head>
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
<div class="container w-100 bg-light">
    <hr/>
    <div class="row">
        <div class="col-1"></div>
        <div class="col-3">
            <div class="img">
                <img src="music/${ sessionScope.user.photoPath }" alt="${ sessionScope.user.login }"
                     class="rounded-circle"
                     width="200" height="200">
            </div>
            <h4><fmt:message key="profile.overview"/></h4>
            <h3><c:out value="${ sessionScope.user.login }"/></h3>
            <c:if test="${ sessionScope.user.admin eq false }">
                <fmt:parseDate value="${ sessionScope.user.paidPeriod }" pattern="yyyy-MM-dd" var="parsedDate"
                               type="date"/>
                <fmt:formatDate value="${ parsedDate }" var="newParsedDate" type="date" pattern="dd.MM.yyyy"/>
                <div class="alert alert-info" role="alert">
                    <span><fmt:message key="label.paid.period"/>: ${ newParsedDate }</span>
                </div>
                <form action="<c:url value="/controller"/>" method="get">
                    <input type="hidden" name="command" value="to-payment">
                    <input type="submit" class="btn btn-success" name="submit"
                           value="<fmt:message key="button.extend"/>">
                </form>
                <form action="<c:url value="/controller"/>" method="get">
                    <input type="hidden" name="command" value="payment-history">
                    <input type="hidden" name="login" value="${ sessionScope.user.login }">
                    <input type="submit" class="btn btn-info" name="submit"
                           value="<fmt:message key="button.payment.history"/>">
                </form>
            </c:if>

        </div>
        <div class="col-8">
            <h3><fmt:message key="message.profile"/></h3>
            <p class="text-info"><c:out value="${ requestScope.process }"/></p>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>

            <form action="<c:url value="/controller"/>" enctype="multipart/form-data" method="post"
                  class="needs-validation" novalidate>
                <label>
                    <fmt:message key="label.profile.photo"/>
                </label>
                <input type="hidden" name="command" value="upload-img"/>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <input type="submit" class="btn btn-dark" name="submit"
                               value="<fmt:message key="button.update"/>">
                    </div>
                    <div class="custom-file">
                        <input type="file" class="form-control custom-file-input" name="imgpath"
                               accept="image/jpeg" required id="inputGroupFile01"
                               aria-describedby="inputGroupFileAddon01"/>
                        <label class="custom-file-label" for="inputGroupFile01">
                            <fmt:message key="label.file.choose"/>
                        </label>
                    </div>
                    <script type="application/javascript">
                        $('input[type="file"]').change(function (e) {
                            var fileName = e.target.files[0].name;
                            $('.custom-file-label').html(fileName);
                        });
                    </script>
                </div>
            </form>
            <form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="update-firstname">
                <label>
                    <fmt:message key="label.profile.firstname"/>
                </label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}"
                           value="${ sessionScope.user.firstname }" required="required">
                    <div class="input-group-append">
                        <input type="submit" class="btn btn-dark" name="submit"
                               value="<fmt:message key="button.update"/>">
                    </div>
                    <div class="invalid-feedback">
                        <fmt:message key="violation.firstname"/>
                    </div>
                </div>
            </form>
            <form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="update-lastname">
                <label>
                    <fmt:message key="label.profile.lastname"/>
                </label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}"
                           value="${ sessionScope.user.lastname }" required="required">
                    <div class="input-group-append">
                        <input type="submit" class="btn btn-dark" name="submit"
                               value="<fmt:message key="button.update"/>">
                    </div>
                    <div class="invalid-feedback">
                        <fmt:message key="violation.lastname"/>
                    </div>
                </div>
            </form>
            <form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="update-birthdate">
                <label>
                    <fmt:message key="label.profile.birthdate"/>
                </label>
                <div class="input-group mb-3">
                    <input type="date" id="datefield" class="form-control" name="birthdate" required="required"
                           value="${ sessionScope.user.birthDate }">
                    <div class="input-group-append">
                        <input type="submit" class="btn btn-dark" name="submit"
                               value="<fmt:message key="button.update"/>">
                    </div>
                    <div class="invalid-feedback">
                        <fmt:message key="violation.birthdate"/>
                    </div>
                </div>
                <c:import url="../common/maxbirthdate.jsp"/>
                <script>
                    validateDateField("datefield");
                </script>
            </form>
            <label style="text-align: right; margin-right: 15px; border-top-width: 15px">
                <fmt:message key="label.profile.gender"/>
            </label>
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="update-gender">
                <select name="gender" class="selectpicker" data-container="body">
                    <c:if test="${ sessionScope.user.gender eq 'MALE' }">
                        <option selected="selected" value="male">
                            <fmt:message key="option.male"/>
                        </option>
                        <option value="female"><fmt:message key="option.female"/></option>
                    </c:if>
                    <c:if test="${ sessionScope.user.gender eq 'FEMALE' }">
                        <option selected="selected" value="female">
                            <fmt:message key="option.female"/>
                        </option>
                        <option value="male"><fmt:message key="option.male"/></option>
                    </c:if>
                </select>
                <input type="submit" class="btn btn-dark" name="submit" value="<fmt:message key="button.update"/>">
            </form>
            <hr/>
            <form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="change-password">
                <div class="form-group w-25">
                    <div class="col-xs-1">
                        <label for="inputOldPass"></label>
                        <input type="password" id="inputOldPass" class="form-control" aria-describedby="oldPassHelp"
                               name="oldpassword"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                               required="">
                        <small id="oldPassHelp" class="form-text text-muted"><fmt:message
                                key="label.profile.password.old"/></small>
                        <div class="invalid-feedback">
                            <fmt:message key="violation.password"/>
                        </div>
                    </div>
                    <div class="col-xs-1">
                        <label for="inputNewPass"></label>
                        <input type="password" id="inputNewPass" class="form-control" aria-describedby="newPassHelp"
                               name="newpassword"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                               required="">
                        <small id="newPassHelp" class="form-text text-muted"><fmt:message
                                key="label.profile.password.new"/>
                        </small>
                        <div class="invalid-feedback">
                            <fmt:message key="violation.password"/>
                        </div>
                    </div>
                    <div class="col-xs-1">
                        <label for="inputRePass"></label>
                        <input type="password" id="inputRePass" class="form-control" aria-describedby="newRePassHelp"
                               name="newpasswordCheck"
                               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+={};:><.,/?`~±§-])(?=[^\r\n\t\f\v]+$).{8,20}$"
                               required="">
                        <small id="newRePassHelp" class="form-text text-muted"><fmt:message
                                key="label.profile.password.re"/>
                        </small>
                        <div class="invalid-feedback">
                            <fmt:message key="violation.password"/>
                        </div>
                    </div>
                    <div class="col-xs-2">
                        <input type="submit" class="btn btn-dark" name="submit"
                               value="<fmt:message key="button.change.password"/>">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<br>
<c:import url="../common/footer.jsp"/>
</body>
</html>
