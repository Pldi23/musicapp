<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<div class="container w-100 bg-light fixed-top">
    <div class="row">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${ requestScope.page eq '/jsp/registration.jsp' || requestScope.page eq '/jsp/verification.jsp'}">
                    <div class="buttons-wrapper d-flex float-md-right">
                        <img src="<c:url value="/resources/image2vector.svg"/>" class="mr-3 nav-item nav-link" alt="music app"
                             width="55" height="55">
                        <h3 class="mr-3 nav-item nav-link"><fmt:message key="label.registration.form"/></h3>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-login">
                                <input type="submit" name="submit" value="<fmt:message key="button.tologin"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" class="mr-3 nav-item nav-link form-inline">
                            <div class="form-group">
                                <input type="hidden" name="command" value="search"/>
                                <label>
                                    <fmt:message key="label.search"/>:
                                    <input class="form-control mr-sm-2" type="text" name="searchrequest" required pattern=".{1,10}"
                                           placeholder="<fmt:message key="placeholder.search"/>"/>
                                </label>
                                <input type="hidden" name="offset" value="0">
                                <input type="submit" name="submit" value="<fmt:message key="button.search"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ requestScope.page eq '/jsp/login.jsp' }">
                    <div class="buttons-wrapper d-flex float-md-right">
                        <img src="<c:url value="/resources/image2vector.svg"/>" class="mr-3 nav-item nav-link" alt="music app"
                             width="55" height="55">
                        <h3 class="mr-3 nav-item nav-link"><fmt:message key="label.login.sentence"/></h3>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-registr"/>
                                <input type="submit" name="submit" value="<fmt:message key="button.registration"/> "
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" class="mr-3 nav-item nav-link form-inline">
                            <div class="form-group">
                                <input type="hidden" name="command" value="search"/>
                                <label>
                                    <fmt:message key="label.search"/>:
                                    <input class="form-control mr-sm-2" type="text" name="searchrequest" required pattern=".{1,10}"
                                           placeholder="<fmt:message key="placeholder.search"/>"/>
                                </label>
                                <input type="hidden" name="offset" value="0">
                                <input type="submit" name="submit" value="<fmt:message key="button.search"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ empty sessionScope.user }">
                    <div class="buttons-wrapper d-flex float-md-right">
                        <img src="<c:url value="/resources/image2vector.svg"/>" class="mr-3 nav-item nav-link" alt="music app"
                             width="55" height="55">
                        <h3 class="mr-3 nav-item nav-link"><fmt:message key="label.hello"/></h3>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-login">
                                <input type="submit" name="submit" value="<fmt:message key="button.tologin"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-registr"/>
                                <input type="submit" name="submit" value="<fmt:message key="button.registration"/> "
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" class="mr-3 nav-item nav-link form-inline">
                            <div class="form-group">
                                <input type="hidden" name="command" value="search"/>
                                <label>
                                    <fmt:message key="label.search"/>:
                                    <input class="form-control mr-sm-2" type="text" name="searchrequest" required pattern=".{1,10}"
                                           placeholder="<fmt:message key="placeholder.search"/>"/>
                                </label>
                                <input type="hidden" name="offset" value="0">
                                <input type="submit" name="submit" value="<fmt:message key="button.search"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ sessionScope.user.admin eq true}">
                    <div class="buttons-wrapper d-flex float-md-right">
                        <img src="<c:url value="/resources/image2vector.svg"/>" class="mr-3 nav-item nav-link" alt="music app"
                             width="55" height="55">
                        <h3 class="mr-3 nav-item nav-link">${ sessionScope.user.firstname }, <fmt:message key="role.admin"/></h3>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-library">
                                <input type="submit" name="submit" value="<fmt:message key="button.library"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-user-library">
                                <input type="hidden" name="offset" value="0">
                                <input type="submit" name="submit" value="<fmt:message key="button.users.db"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-profile">
                                <input type="submit" name="submit" value="<fmt:message key="button.profile"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="logout">
                                <input type="submit" name="submit" value="<fmt:message key="button.logout"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ sessionScope.user.admin eq false}">
                    <div class="buttons-wrapper d-flex float-md-right">
                        <img src="<c:url value="/resources/image2vector.svg"/>" class="mr-3 nav-item nav-link" alt="music app"
                             width="55" height="55">
                        <h3 class="mr-3 nav-item nav-link">${ sessionScope.user.firstname }, <fmt:message key="role.user"/></h3>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-library">
                                <input type="submit" name="submit" value="<fmt:message key="button.library"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="user-playlists">
                                <input type="submit" name="submit" value="<fmt:message key="button.playlists.my"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-profile">
                                <input type="submit" name="submit" value="<fmt:message key="button.profile"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="logout">
                                <input type="submit" name="submit" value="<fmt:message key="button.logout"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
