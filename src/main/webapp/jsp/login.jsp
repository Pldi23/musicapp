<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="page" value="/jsp/login.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<body>
<c:import url="locale-form.jsp"/>
<h2><fmt:message key="label.login.sentence"/></h2>
<%--<ctg:info-name/>--%>
${validatorMessage}
${errorLoginPassMessage}
<form action="controller" method="post">
    <input type="hidden" name="command" value="login"/>
    <label>
        <fmt:message key="label.enter.login"/>
        <input type="text" name="login" pattern="^[(\w)-]{4,20}" required="" placeholder="<fmt:message key="placeholder.login"/>"
               title="<fmt:message key="prescription.login"/>" lang="en"/>

    </label>
    <br>
    <fmt:message key="label.enter.password"/>
    <input type="password" name="password" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required=""
           placeholder="<fmt:message key="placeholder.password"/>" title="<fmt:message key="prescription.password"/>"/>
    </label>
    <input type="submit" name="submit" value="<fmt:message key="button.login"/> ">
</form>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-registr"/>
    <br>
    <input type="submit" name="submit" value="<fmt:message key="button.registration"/> ">
    <br/>
</form>
<br>
<c:import url="search-form.jsp"/>
<%--<form action="controller" method="post">--%>
<%--    <input type="hidden" name="command" value="search"/>--%>
<%--    <label>--%>
<%--        <fmt:message key="label.search"/>:--%>
<%--        <input type="text" name="searchrequest" placeholder="<fmt:message key="placeholder.search"/>"/>--%>
<%--    </label>--%>
<%--    <input type="submit" name="submit" value="<fmt:message key="button.search"/>">--%>
<%--</form>--%>
<br>
<ctg:top-tracks/>
</body>
</html>
<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="set-locale">--%>
<%--    <input type="hidden" name="page" value="${page}">--%>
<%--    <select name="locale" onchange="submit()">--%>
<%--        <option selected="selected">${ not empty locale ? locale : pageContext.request.locale }</option>--%>
<%--        <option value="en_US">EN</option>--%>
<%--        <option value="ru_RU">RU</option>--%>
<%--        <option value="ru_BY">BY</option>--%>
<%--    </select>--%>
<%--</form>--%>
