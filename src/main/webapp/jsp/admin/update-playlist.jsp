<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title>${ requestScope.entity.name }</title>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
        </div>
        <div class="col-10">
            <c:import url="../common/header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <img src="<c:url value="/resources/note.svg"/>" width="100" height="60" alt="">
        </div>
        <div class="col-8">
            <ul class="list-inline">
                <li class="list-inline-item"><h3><c:out value="${ requestScope.entity.name }"/></h3></li>
            </ul>
        </div>
        <div class="col-2">
            <img src="<c:url value="/resources/note.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-info"><c:out value="${ requestScope.process }"/></p>
            <fmt:message key="label.id"/> <c:out value="${ requestScope.entity.id }"/>
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="update-playlist">
                <input type="hidden" name="id" value="${ requestScope.entity.id }">
                <br>
                <label>
                    <fmt:message key="label.playlist.name"/>
                    <input type="text" name="name" minlength="2" maxlength="50" required="required"
                           value="${ requestScope.entity.name }">
                </label>
                <br>
                <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.update"/>">
            </form>
        </div>
        <div class="col-2">
            <c:import url="../common/search-form.jsp"/>
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
