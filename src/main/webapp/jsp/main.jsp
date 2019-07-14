<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/jsp/main.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session"/>
<fmt:setBundle basename="pagecontent" />
<html><head><title><fmt:message key="label.welcome"/></title></head>
<body>
<c:import url="locale-form.jsp"/>
<c:import url="header.jsp"/>
<hr/>
<h3><fmt:message key="label.welcome"/></h3>
<c:import url="track-filter-form.jsp"/>
<c:import url="search-form.jsp"/>
<hr/>
</body></html>