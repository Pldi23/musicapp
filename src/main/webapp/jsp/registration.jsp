<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/registration.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title><fmt:message key="label.welcome"/></title>
</head>
<body>
<c:import url="locale-form.jsp"/>
<h2><fmt:message key="registration.message"/></h2>
<c:if test="${ not empty violations }">
    <ctg:violations violations="${ violations }"/>
</c:if>
${serviceException}
<form action = "controller" method = "post" >
    <input type="hidden" name="command" value="register"/>
    <label>
        <fmt:message key="label.enter.login"/> :
        <input type="text" name="login" pattern="^[(\w)-]{4,20}" required="true" placeholder="<fmt:message key="placeholder.login"/>"
        title="<fmt:message key="prescription.login"/>"/>
    </label>
    <br>
    <label>
        <fmt:message key="label.enter.password"/> :
        <input type="password" name="password" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="true" placeholder="<fmt:message key="placeholder.password"/>"
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
             <input type="text" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required="true" placeholder="<fmt:message key="placeholder.firstname"/>"
             title="<fmt:message key="title.firstname"/>"/>
        </label>
    <br>
        <label>
              <fmt:message key="label.enter.lastname"/>
              <input type="text" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required="true" placeholder="<fmt:message key="placeholder.lastname"/>"
              title="<fmt:message key="title.lastname"/>"/>
        </label>
    <br>
        <label>
              <fmt:message key="label.email"/>
              <input type="email" name="email" required="true" placeholder="<fmt:message key="placeholder.email"/>" title="<fmt:message key="title.email"/>"/>
        </label>
    <br>
    <input type="submit" name="submit" value="<fmt:message key="button.register"/>">
    <br/>
</form>
</body>
</html>