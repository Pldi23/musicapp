<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/profile.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.profile"/></title>
</head>
<body>
<c:import url="locale-form.jsp"/>
<c:import url="header.jsp"/>
<label>
    ${ user.firstname }, <fmt:message key="message.profile"/>
</label>
${ result }
<img src="${ user.photoPath }">
<form action="controller" enctype="multipart/form-data" method="post">
    <label>
        <fmt:message key="label.profile.photo"/>
        <input type="hidden" name="command" value="upload-img"/>
        <input type="file" name="photopath" accept="image/jpeg"/>
        <input type="submit" name="submit" value="<fmt:message key="button.update"/>">
    </label>
</form>
<form action="controller" method="post">
    <label>
        <fmt:message key="label.profile.firstname"/>
        <input type="hidden" name="command" value="update-name">
        <input type="text" name="firstname" value="${ user.firstname }" required="required">
        <input type="submit" name="submit" value="<fmt:message key="button.update"/>">
    </label>
</form>
<form action="controller" method="post">
    <label>
        <fmt:message key="label.profile.lastname"/>
        <input type="hidden" name="command" value="update-lastname">
        <input type="text" name="lastname" value="${ user.lastname }" required="required">
        <input type="submit" name="submit" value="<fmt:message key="button.update"/>">
    </label>
</form>
<form action="controller" method="post">
    <label>
        <fmt:message key="label.profile.gender"/>
        <input type="hidden" name="command" value="update-gender">
        <select name="gender" onchange="submit()">
            <option selected="selected">${ user.gender }</option>
            <option value="male"><fmt:message key="option.male"/></option>
            <option value="female"><fmt:message key="option.female"/></option>
        </select>
    </label>
</form>
<form action="controller" method="post">
    <label>
        <fmt:message key="label.profile.birthdate"/>
        <input type="date" name="birthdate" required="required" value="1970-01-01">
        <input type="submit" name="submit" value="<fmt:message key="button.update"/>">
    </label>
</form>
<form action="controller" method="post">
    <label>
        <fmt:message key="label.profile.password.old"/>
        <input type="password" name="oldpassword" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="">
        <fmt:message key="label.profile.password.new"/>
        <input type="password" name="newpassword" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="">
        <fmt:message key="label.profile.password.re"/>
        <input type="password" name="newpassword-check" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="">
        <input type="submit" name="submit" value="<fmt:message key="button.change.password"/>">
    </label>
</form>
</body>
</html>
