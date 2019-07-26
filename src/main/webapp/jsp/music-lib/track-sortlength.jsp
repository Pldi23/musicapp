<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="pageCommand" value="sort-track-by-length" scope="request"/>
<%--<c:import url="track-master.jsp"/>--%>
<%--<%@ page pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%--<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" />--%>
<%--<fmt:setBundle basename="pagecontent" />--%>
<%--<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>--%>
<%--<html>--%>
<%--<head><title><fmt:message key="label.tracklibrary"/></title></head>--%>
<%--<body>--%>
<%--<c:import url="topbar.jsp"/>--%>
<%--<div class="container-fluid bg-light">--%>
<%--    <div class="row">--%>
<%--        <div class="col-2">--%>
<%--            <c:import url="../common/track-filter-form.jsp"/>--%>
<%--        </div>--%>
<%--        <div class="col-8">--%>
<%--            <c:import url="track-buttons.jsp"/>--%>
<%--            <ctg:track-table tracks="${ requestScope.entities }" commandValue="sort-track-by-length"--%>
<%--                             current="${ requestScope.current }" previousUnavailable="${ requestScope.previousunavailable }"--%>
<%--                             nextUnavailable="${ requestScope.nextunavailable }" pages="${ requestScope.size }"/>--%>
<%--        </div>--%>
<%--        <div class="col-2">--%>
<%--            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<c:import url="../common/footer.jsp"/>--%>
<%--</body>--%>
<%--</html>--%>
