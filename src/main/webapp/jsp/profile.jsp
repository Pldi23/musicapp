<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/profile.jsp" scope="request"/>
<html>
<head>
    <title><fmt:message key="label.profile"/></title>
</head>
<body>
<c:import url="locale-form.jsp"/>
<c:import url="header.jsp"/>
    <label>
        you could update your profile here ${ user }
    </label>
</body>
</html>
