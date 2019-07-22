<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.search"/></title>
</head>
<body>
<form action="<c:url value="/controller"/>" method="post">
    <input type="hidden" name="command" value="search"/>
    <input type="hidden" name="offset" value="0">
    <div class="form-group">
        <label for="inputSearch"><fmt:message key="label.search"/>:</label>
        <input type="text" class="form-control" id="inputSearch" name="searchrequest" required pattern=".{1,10}"
               placeholder="<fmt:message key="placeholder.search"/>"/>
    </div>
    <div class="form-group">
        <input type="submit" name="submit" value="<fmt:message key="button.search"/>"
               class="btn btn-secondary btn-sm">
    </div>
</form>
</body>
</html>
