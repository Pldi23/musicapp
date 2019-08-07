<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/jsp/common/verification.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head><title><fmt:message key="label.welcome"/></title></head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1"></div>
        <div class="col-10">
            <c:import url="header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr/>
<div class="container container-fluid bg-light">
    <div class="row">
        <div class="col-8">
            <c:if test="${ sessionScope.user.admin eq true }">
                <form action="<c:url value="/controller"/>" method="get">
                   <input type="hidden" name="command" value="to-admin">
                    <input type="submit" class="btn btn-outline-secondary" name="submit" value="<fmt:message key="button.to.site"/>">
                </form>
            </c:if>
            <div class="alert alert-info" role="alert">
                <span><fmt:message key="message.emailsent"/> ${ requestScope.email }.</span>
                <span><fmt:message key="message.verification"/></span>
            </div>
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>