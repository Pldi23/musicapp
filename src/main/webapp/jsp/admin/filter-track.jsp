<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/track-library.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title><fmt:message key="label.tracklibrary"/></title>
</head>
<body>
<c:import url="../locale-form.jsp"/>
<c:import url="../header.jsp"/>
<c:import url="../library-form.jsp"/>
<c:import url="../track-filter-form.jsp"/>
<ctg:show-tracks sortCommandValue="filter-track" tracks="${entities}" removeCommandValue="to-remove-track"
                 updateCommandValue="to-update-track" nextUnavailable="${ nextunavailable }"
                 previousUnavailable="${ previousunavailable }"/>
</body>
</html>
