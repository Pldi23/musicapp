<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<div class="container container-fluid bg-light">
    <div class="row">
        <div class="col-md-12">
            <c:if test="${ empty user}">
                <div class="buttons-wrapper d-flex float-md-right">
                    <fmt:message key="label.hello"/>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="to-login">
                        <input type="submit" name="submit" value="<fmt:message key="button.tologin"/>">
                    </form>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="to-registr"/>
                        <input type="submit" name="submit" value="<fmt:message key="button.registration"/> ">
                    </form>
                    <c:import url="search-form.jsp"/>
                </div>
            </c:if>
            <c:if test="${ user.admin eq true}">
                <div class="buttons-wrapper d-flex float-md-right">
                        ${ user.firstname }, <fmt:message key="role.admin"/>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="logout">
                        <input type="submit" name="submit" value="<fmt:message key="button.logout"/>">
                    </form>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="to-profile">
                        <input type="submit" name="submit" value="<fmt:message key="button.profile"/>">
                    </form>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="to-user-library">
                        <input type="submit" name="submit" value="<fmt:message key="button.users.db"/>">
                    </form>
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="to-library">
                        <input type="submit" name="submit" value="<fmt:message key="button.library"/>">
                    </form>
                </div>
            </c:if>
            <c:if test="${ user.admin eq false}">
                <div class="buttons-wrapper d-flex float-md-right">
                        <h3 class="top-header">${ user.firstname }, <fmt:message key="role.user"/></h3>
                    <form action="controller" method="post" class="mr-3 nav-item nav-link">
                        <div class="form-group">
                            <input type="hidden" name="command" value="logout">
                            <input type="submit" name="submit" value="<fmt:message key="button.logout"/>" class="form-control btn btn-secondary btn-sm">
                        </div>
                    </form>
                    <form action="controller" method="get">
                        <div class="form-group">
                        <input type="hidden" name="command" value="to-profile">
                        <input type="submit" name="submit" value="<fmt:message key="button.profile"/>" class="form-control btn btn-primary btn-sm">
                        </div>
                    </form>
                    <form action="controller" method="get">
                        <div class="form-group">
                        <input type="hidden" name="command" value="user-playlists">
                        <input type="submit" name="submit" value="<fmt:message key="button.playlists.my"/>" class="form-control btn btn-primary btn-sm">
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</div>
