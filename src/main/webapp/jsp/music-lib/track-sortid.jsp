<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="pageCommand" value="sort-track-by-id" scope="request"/>
<c:import url="track-master.jsp"/>