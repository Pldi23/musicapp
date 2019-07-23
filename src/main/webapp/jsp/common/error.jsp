<%@ page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.error"/></title>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
        </div>
        <div class="col-10">
            <c:import url="header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <div class="alert alert-warning" role="alert">
                <h4 class="alert-heading"><fmt:message key="label.sorry"/></h4>
                <hr>
                <p><fmt:message key="message.error"/></p>
                <hr>
                <p class="mb-0"><c:out value="message:: ${ requestScope.throwable.toString() }"/></p>
                <c:if test="${ empty requestScope.throwable  }">
                    <p class="mb-0"><c:out value="status code:: ${ pageContext.errorData.statusCode }"/></p>
                    <p class="mb-0"><c:out value="URI:: ${ pageContext.errorData.requestURI } "/></p>
                    <p class="mb-0"><c:out value="Servlet:: ${ pageContext.errorData.servletName } "/></p>
                    <p class="mb-0"><c:out value="Cause:: ${ pageContext.errorData.throwable } "/></p>
                </c:if>
            </div>
        </div>
        <div class="col-2">
            <c:import url="search-form.jsp"/>
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
