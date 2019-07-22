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
            <div class="img"><img src="music/img/${ sessionScope.user.photoPath }" alt="${ sessionScope.user.login }" class="rounded-circle"
                                  width="200" height="200">
            </div>
            <h4><c:out value="${ sessionScope.user.login }"/></h4>
            <h3><fmt:message key="profile.overview"/></h3>
        </div>
        <div class="col-8">
            <h3><fmt:message key="message.profile"/></h3>
            <p class="text-info">${ requestScope.process }</p>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>

            <form action="<c:url value="/controller"/>" enctype="multipart/form-data" method="post">
                <label>
                    <fmt:message key="label.profile.photo"/>
                    <input type="hidden" name="command" value="upload-img"/>
                    <input type="file" name="imgpath" accept="image/jpeg"/>
                    <input type="submit" class="btn btn-dark" name="submit" value="<fmt:message key="button.update"/>">
                </label>
            </form>
            <form action="<c:url value="/controller"/>" method="post">
                <label>
                    <fmt:message key="label.profile.firstname"/>
                    <input type="hidden" name="command" value="update-firstname">
                    <input type="text" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" value="${ sessionScope.user.firstname }" required="required">
                    <input type="submit" class="btn btn-dark" name="submit" value="<fmt:message key="button.update"/>">
                </label>
            </form>
            <form action="<c:url value="/controller"/>" method="post">
                <label>
                    <fmt:message key="label.profile.lastname"/>
                    <input type="hidden" name="command" value="update-lastname">
                    <input type="text" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" value="${ sessionScope.user.lastname }" required="required">
                    <input type="submit" class="btn btn-dark" name="submit" value="<fmt:message key="button.update"/>">
                </label>
            </form>
            <form action="<c:url value="/controller"/>" method="post">
                <label>
                    <fmt:message key="label.profile.gender"/>
                    <input type="hidden" name="command" value="update-gender">
                    <select name="gender">
                        <option selected="selected">${ sessionScope.user.gender }</option>
                        <option value="male"><fmt:message key="option.male"/></option>
                        <option value="female"><fmt:message key="option.female"/></option>
                    </select>
                    <input type="submit" class="btn btn-dark" name="submit" value="<fmt:message key="button.update"/>">
                </label>
            </form>
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="update-birthdate">
                <label>
                    <fmt:message key="label.profile.birthdate"/>
                    <input type="date" name="birthdate" required="required" value="1970-01-01">
                    <input type="submit" class="btn btn-dark" name="submit" value="<fmt:message key="button.update"/>">
                </label>
            </form>
            <hr/>
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="change-password">
                <div class="form-group w-25">
                    <div class="col-xs-1">
                        <label for="inputOldPass"></label>
                        <input type="password" id="inputOldPass" class="form-control" aria-describedby="oldPassHelp"
                               name="oldpassword"
                               pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="">
                        <small id="oldPassHelp" class="form-text text-muted"><fmt:message
                                key="label.profile.password.old"/></small>
                    </div>
                    <div class="col-xs-1">
                        <label for="inputNewPass"></label>
                        <input type="password" id="inputNewPass" class="form-control" aria-describedby="newPassHelp"
                               name="newpassword"
                               pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="">
                        <small id="newPassHelp" class="form-text text-muted"><fmt:message
                                key="label.profile.password.new"/>
                        </small>
                    </div>
                    <div class="col-xs-1">
                        <label for="inputRePass"></label>
                        <input type="password" id="inputRePass" class="form-control" aria-describedby="newRePassHelp"
                               name="newpasswordCheck"
                               pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="">
                        <small id="newRePassHelp" class="form-text text-muted"><fmt:message
                                key="label.profile.password.re"/>
                        </small>
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
