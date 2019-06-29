<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="ru_RU" scope="session" />
<fmt:setBundle basename="pagecontent" var="rb" />
<html><head>
    <title><fmt:message key="label.title" bundle="${ rb }" /></title>
</head>
<body>
<fmt:message key="label.welcome" bundle="${ rb }" />
<hr/>
<fmt:message key="footer.copyright" bundle="${ rb }" />
</body></html>