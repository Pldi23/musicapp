<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/jsp/information.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html><head><title>Sorry</title></head>
<body>
<hr/>
<fmt:message key="message.information"/>
<br>
<a href="${pageContext.request.contextPath}">
    <fmt:message key="button.back"/>
</a>
<hr/>
</body>
</html>