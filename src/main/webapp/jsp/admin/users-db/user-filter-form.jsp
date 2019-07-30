<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<script>
    $(document).ready(function () {
        var today = new Date();
        var day=today.getDate()>9?today.getDate():"0"+today.getDate(); // format should be "DD" not "D" e.g 09
        var month=(today.getMonth()+1)>9?(today.getMonth()+1):"0"+(today.getMonth()+1);
        var year=today.getFullYear();

        $("#createdFrom").attr('max', year + "-" + month + "-" + day);
        $("#createdTo").attr('max', year + "-" + month + "-" + day);
        $("#birthDateFrom").attr('max', year + "-" + month + "-" + day);
        $("#birthDateTo").attr('max', year + "-" + month + "-" + day);
    });
</script>
<body>
<form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
    <input type="hidden" name="command" value="filter-user">
    <input type="hidden" name="current" value="0">
    <input type="hidden" name="order" value="marker">
    <div class="form-group">
        <label for="inputLogin"><fmt:message key="label.filter.username"/></label>
        <input type="text" class="form-control" id="inputLogin" pattern=".{1,20}" value="${ requestScope.login }"
               name="login" placeholder="<fmt:message key="placeholder.filter.login"/>"
               title="<fmt:message key="violation.filter"/>">
        <div class="invalid-feedback">
            <fmt:message key="violation.filter"/>
        </div>
    </div>
    <div class="form-group">
        <label for="inputRole"><fmt:message key="label.filter.role"/></label>
        <select name="role" class="form-control" id="inputRole">
            <option selected="selected"></option>
            <option value="true"><fmt:message key="option.admin"/></option>
            <option value="false"><fmt:message key="option.user"/></option>
        </select>
    </div>
    <div class="form-group">
        <label for="inputFirstName"><fmt:message key="label.profile.firstname"/></label>
        <input type="text" name="firstname" pattern=".{1,20}"  class="form-control"
               id="inputFirstName" value="${ requestScope.firstname }"
               placeholder="<fmt:message key="placeholder.firstname"/>"
               title="<fmt:message key="violation.filter"/>">
        <div class="invalid-feedback">
            <fmt:message key="violation.filter"/>
        </div>
    </div>
    <div class="form-group">
        <label for="inputLastName"><fmt:message key="label.profile.lastname"/></label>
        <input type="text" name="lastname" pattern=".{1,20}"  class="form-control"
               id="inputLastName" value="${ requestScope.lastname }"
               placeholder="<fmt:message key="placeholder.lastname"/>"
               title="<fmt:message key="violation.filter"/>">
        <div class="invalid-feedback">
            <fmt:message key="violation.filter"/>
        </div>
    </div>
    <div class="form-group">
        <label for="inputEmail"><fmt:message key="label.filter.email"/></label>
        <input id="inputEmail" class="form-control" type="text" name="email" pattern=".{1,20}"
               placeholder="<fmt:message key="placeholder.email"/>"
               title="<fmt:message key="violation.filter"/>">
        <div class="invalid-feedback">
            <fmt:message key="violation.filter"/>
        </div>

    </div>
    <div class="form-group">
        <label for="birthDateFrom"><fmt:message key="label.birthdate.from.to"/></label>
        <input type="date" class="form-control" id="birthDateFrom" name="birthDateFrom" value="${ requestScope.birthDateFrom }">
        <div class="invalid-feedback">
            <fmt:message key="violation.release.filter"/>
        </div>
    </div>
    <div class="form-group">
        <label for="birthDateTo"></label>
        <input type="date" class="form-control" id="birthDateTo" name="birthDateTo" value="${ requestScope.birthDateTo }">
        <div class="invalid-feedback">
            <fmt:message key="violation.release.filter"/>
        </div>
    </div>
    <div class="form-group">
        <label for="createdFrom"><fmt:message key="label.created.from.to"/></label>
        <input type="date" class="form-control" id="createdFrom" name="createdFrom" value="${ requestScope.createdFrom }">
        <div class="invalid-feedback">
            <fmt:message key="violation.release.filter"/>
        </div>
    </div>
    <div class="form-group">
        <label for="createdTo"></label>
        <input type="date" class="form-control" id="createdTo" name="createdTo" value="${ requestScope.createdTo }">
        <div class="invalid-feedback">
            <fmt:message key="violation.release.filter"/>
        </div>
    </div>
    <div class="form-group">
        <input type="submit" name="submit" class="btn btn-dark"
               value="<fmt:message key="button.filter"/>">
    </div>
</form>
</body>
</html>
