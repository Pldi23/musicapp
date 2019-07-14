<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<c:set var="page" value="/jsp/admin/filter-result.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title><fmt:message key="label.filter.result"/> </title>
</head>
<body>
<c:import url="header.jsp"/>
<hr/>
<c:import url="search-form.jsp"/>
<h2>
    <fmt:message key="label.filter.result"/>
</h2>
<c:import url="track-filter-form.jsp"/>
<br>
<ctg:show-tracks tracks="${ entities }" admin="${ user.admin }" commandValue="show-all-tracks" moreCommandValue="track-detail"
                 nextUnavailable="${ nextunavailable }" previousUnavailable="${ previousunavailable }"/>
</body>
</html>
