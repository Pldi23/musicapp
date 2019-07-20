<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/library/library-track.jsp" scope="request"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head><title><fmt:message key="label.tracklibrary"/></title></head>
<body>
<c:import url="library-master.jsp"/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <c:import url="../sort-button-group.jsp"/>
            <ctg:show-tracks tracks="${ entities }" admin="${ user.admin }" removeCommandValue="to-remove-track"
                             updateCommandValue="to-update-track"
                             commandValue="show-all-tracks" nextUnavailable="${ nextunavailable }"
                             previousUnavailable="${ previousunavailable }"/>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>

${removeResult}
${updateResult}
<c:import url="../footer.jsp"/>
</body>
</html>