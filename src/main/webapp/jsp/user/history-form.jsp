<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<form action="<c:url value="/controller"/>" method="get">
    <input type="hidden" name="command" value="to-tracks-history">
    <input type="submit" class="btn btn-outline-secondary fa fa-lg" name="submit" value="&#xf1da; <fmt:message key="button.history"/>">
</form>
</body>
</html>
