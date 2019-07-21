<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/musician.jsp" scope="request"/>
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
            <c:import url="../header.jsp"/>
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
            <c:import url="../track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <p class="text-warning">${ requestScope.violations }</p>
            <p class="text-info">${ requestScope.process }</p>
            <fmt:message key="label.id"/> <c:out value="${ requestScope.entity.id }"/>
            <form action="controller" method="post">
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
            <c:import url="../search-form.jsp"/>
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
        </div>
    </div>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
