<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" scope="session"/>
<fmt:setBundle basename="pagecontent" />
<html><head><title><fmt:message key="label.track.remove.confirmation"/></title></head>
<body>
<c:import url="../library/library-master.jsp"/>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <fmt:message key="message.remove.confirm"/><c:out value="'${ requestScope.entity.name }'"/><fmt:message key="message.database.remove"/>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="remove">
                <input type="hidden" name="id" value="${ requestScope.entity.id }">
                <input type="hidden" name="entityType" value="${ requestScope.entityType }">
                <input type="submit" class="btn btn-outline-danger" name="submit" value="<fmt:message key="button.finally.remove"/> ">
            </form>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="remove-cancel">
                <input type="hidden" name="id" value="${ requestScope.entity.id }">
                <input type="hidden" name="entityType" value="${ requestScope.entityType }">
                <input type="submit" class="btn btn-outline-secondary" name="submit" value="<fmt:message key="button.cancel"/>">
            </form>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
            <c:import url="../common/search-form.jsp"/>
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>