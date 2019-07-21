<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<form action="controller" method="post">
    <input type="hidden" name="command" value="filter-user">
    <input type="hidden" name="offset" value="0">
    <input type="hidden" name="order" value="marker">
    <div class="form-group">
        <label for="inputLogin"><fmt:message key="label.filter.username"/></label>
        <input type="text" class="form-control" id="inputLogin" pattern="^[(\w)-]{4,20}" value="${ requestScope.login }"
               name="login" placeholder="<fmt:message key="placeholder.filter.login"/>"/>
    </div>
    <div class="form-group">
        <label for="inputRole"><fmt:message key="label.filter.role"/></label>
        <select name="role" class="form-control" id="inputRole">
            <option selected="selected"></option>
            <option value="admin"><fmt:message key="option.admin"/></option>
            <option value="user"><fmt:message key="option.user"/></option>
        </select>
    </div>
    <div class="form-group">
        <label for="inputFirstName"><fmt:message key="label.profile.firstname"/></label>
        <input type="text" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}"  class="form-control"
               id="inputFirstName" value="${ requestScope.firstname }"
               placeholder="<fmt:message key="placeholder.firstname"/>">
    </div>
    <div class="form-group">
        <label for="inputLastName"><fmt:message key="label.profile.lastname"/></label>
        <input type="text" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}"  class="form-control"
               id="inputLastName" value="${ requestScope.lastname }"
               placeholder="<fmt:message key="placeholder.lastname"/>">
    </div>
    <div class="form-group">
        <label for="inputEmail"><fmt:message key="label.email"/></label>
        <input id="inputEmail" class="form-control" type="email" name="email" required=""
               placeholder="<fmt:message key="placeholder.email"/>"
               title="<fmt:message key="title.email"/>"/>

    </div>
    <div class="form-group">
        <label for="birthDateFrom"><fmt:message key="label.birthdate.from.to"/></label>
        <input type="date" class="form-control" id="birthDateFrom" name="birthDateFrom" value="${ requestScope.birthDateFrom }">
    </div>
    <div class="form-group">
        <label for="birthDateTo"></label>
        <input type="date" class="form-control" id="birthDateTo" name="birthDateTo" value="${ requestScope.birthDateTo }">
    </div>
    <div class="form-group">
        <label for="createdFrom"><fmt:message key="label.created.from.to"/></label>
        <input type="date" class="form-control" id="createdFrom" name="createdFrom" value="${ requestScope.createdFrom }">
    </div>
    <div class="form-group">
        <label for="createdTo"></label>
        <input type="date" class="form-control" id="createdTo" name="createdTo" value="${ requestScope.createdTo }">
    </div>
    <div class="form-group">
        <input type="submit" name="submit" class="btn btn-dark"
               value="<fmt:message key="button.filter"/>">
    </div>
</form>
</body>
</html>
