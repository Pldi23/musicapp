<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/users.jsp" scope="request"/>
<html>
<head>
    <title><fmt:message key="label.users"/></title>
</head>
<body>

</body>
</html>
