<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/jsp/main.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html><head><title><fmt:message key="label.welcome"/></title></head>
<body>
<c:import url="locale-form.jsp"/>
<c:import url="header.jsp"/>
<h3><fmt:message key="label.welcome"/></h3>
<hr/>
</body></html>