<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>
<script src="https://kit.fontawesome.com/38d16960f7.js"></script>


<script>
    $(function () {
        $("audio").on("play", function () {
            $("audio").not(this).each(function (index, audio) {
                audio.pause();
            });
        });
    });
</script>
<script>
    $(function () {
        $("audio").on("ended", function () {
            var nextId = parseInt(this.id) + 1;
            if (nextId >= $("audio").length) {
                nextId = 0;
            }
            $("audio")[nextId].play();
        })
    })
</script>
<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
<script type="text/javascript">
    function setCookie(key, value) {
        var current = getCookie('current');
        if (typeof current == 'undefined' || current >= 10) {
            current = 1;
        }
        var keyName = key + current++;
        var expires = new Date();
        expires.setTime(expires.getTime() + (1 * 24 * 60 * 60 * 1000));
        document.cookie = 'current' + '=' + current + ';expires=' + expires.toUTCString();
        document.cookie = keyName + '=' + value + ';expires=' + expires.toUTCString();
    }
    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
    }
</script>
<div class="container w-100 bg-light fixed-top">
    <div class="row text-middle">
        <div class="col-md-12">
            <c:choose>
                <c:when test="${ requestScope.page eq '/jsp/common/registration.jsp' || requestScope.page eq '/jsp/common/verification.jsp'}">
                    <div class="buttons-wrapper d-flex float-md-right" style="height: 60px">
                        <a class="navbar-brand" href="#"
                           style="margin-right: 10px; padding-top: 0; padding-bottom: 0">
                            <form action="<c:url value="/controller"/>" method="get" class="navbar-brand">
                                <input type="hidden" name="command" value="to-main">
                                <img src="<c:url value="/resources/secondary-logo.svg"/>"
                                     alt="music app"
                                     width="55" height="55" onclick="submit()">
                            </form>
                        </a>
                        <h4 class="mr-3 nav-item nav-link" style="padding-left: 0; padding-right: 0"><fmt:message key="label.registration.form"/></h4>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-login">
                                <input type="submit" name="submit" value="<fmt:message key="button.tologin"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" style="padding-left: 0; padding-right: 0"
                              class="mr-3 nav-item nav-link form-inline needs-validation" novalidate>
                            <div class="form-group">
                                <input type="hidden" name="command" value="search"/>
                                <input type="hidden" name="offset" value="0">
                                <label>
                                    <fmt:message key="label.search"/>:
                                    <input class="form-control mr-sm-2" type="text" name="searchrequest" required
                                           pattern=".{1,10}"
                                           placeholder="<fmt:message key="placeholder.search"/>"
                                           title="<fmt:message key="violation.search.request"/>"/>
                                    <input type="submit" name="submit" value="<fmt:message key="button.search"/>"
                                           class="btn btn-secondary btn-sm">
                                </label>
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ requestScope.page eq '/jsp/common/login.jsp' }">
                    <div class="buttons-wrapper d-flex float-md-right" style="height: 60px">
                        <a class="navbar-brand" href="#"
                           style="margin-right: 10px; padding-top: 0; padding-bottom: 0">
                            <form action="<c:url value="/controller"/>" method="get">
                                <input type="hidden" name="command" value="to-main">
                                <img src="<c:url value="/resources/secondary-logo.svg"/>"
                                     alt="music app"
                                     width="55" height="55" onclick="submit()">
                            </form>
                        </a>
                        <h3 class="mr-3 nav-item nav-link" style="padding-left: 0; padding-right: 0"><fmt:message key="label.login.sentence"/></h3>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-registr"/>
                                <input type="submit" name="submit" value="<fmt:message key="button.registration"/> "
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" style="padding-left: 0; padding-right: 0"
                              class="mr-3 nav-item nav-link form-inline needs-validation" novalidate>
                            <div class="form-group">
                                <input type="hidden" name="command" value="search"/>
                                <input type="hidden" name="offset" value="0">
                                <label>
                                    <fmt:message key="label.search"/>:
                                    <input class="form-control mr-sm-2" type="text" name="searchrequest" required
                                           pattern=".{1,10}"
                                           placeholder="<fmt:message key="placeholder.search"/>"
                                           title="<fmt:message key="violation.search.request"/>"/>
                                    <input type="submit" name="submit" value="<fmt:message key="button.search"/>"
                                           class="btn btn-secondary btn-sm">
                                </label>
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ empty sessionScope.user }">
                    <div class="buttons-wrapper d-flex float-md-right" style="height: 60px">
                        <a class="navbar-brand" href="#"
                           style="margin-right: 10px; padding-top: 0; padding-bottom: 0">

                            <form action="<c:url value="/controller"/>" method="get" class="navbar-brand">
                                <input type="hidden" name="command" value="to-main">
                                <img src="<c:url value="/resources/secondary-logo.svg"/>"
                                     alt="music app"
                                     width="55" height="55" onclick="submit()">
                            </form>
                        </a>
                        <h4 class="mr-3 nav-item nav-link" style="padding-left: 0; padding-right: 0"><fmt:message key="label.hello"/></h4>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-login">
                                <input type="submit" name="submit" value="<fmt:message key="button.tologin"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-registr"/>
                                <input type="submit" name="submit" value="<fmt:message key="button.registration"/> "
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" style="padding-left: 0; padding-right: 0"
                              class="mr-3 nav-item nav-link form-inline needs-validation" novalidate>
                            <div class="form-group">
                                <input type="hidden" name="command" value="search"/>
                                <input type="hidden" name="offset" value="0">
                                <label>
                                    <fmt:message key="label.search"/>:
                                    <input class="form-control mr-sm-2" type="text" name="searchrequest" required
                                           pattern=".{1,10}"
                                           placeholder="<fmt:message key="placeholder.search"/>"
                                           title="<fmt:message key="violation.search.request"/>"/>
                                    <input type="submit" name="submit" value="<fmt:message key="button.search"/>"
                                           class="btn btn-secondary btn-sm">
                                </label>
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ sessionScope.user.admin eq true}">
                    <div class="buttons-wrapper d-flex float-md-right" style="height: 60px">
                        <a class="navbar-brand" href="#"
                           style="margin-right: 10px; padding-top: 0; padding-bottom: 0">
                            <form action="<c:url value="/controller"/>" method="get">
                                <input type="hidden" name="command" value="to-main">
                                <img src="<c:url value="/resources/secondary-logo.svg"/>"
                                     alt="music app"
                                     width="55" height="55" onclick="submit()">
                            </form>
                        </a>
                        <h4 class="mr-3 nav-item nav-link" style="padding-left: 0; padding-right: 0">${ sessionScope.user.firstname }
                            <span
                                class="badge badge-info "><fmt:message key="role.admin"/>
                            </span></h4>
                        <div class="mr-3 nav-item nav-link" style="padding-left: 0; padding-right: 0">
                            <button class="dropdown-toggle mr-3 nav-link form-control btn btn-light btn-sm text-center"
                                    id="navbarDropdown" type="submit" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false"
                                    style="padding-top: 2px; padding-bottom: 2px">
                                <fmt:message key="button.users.db"/>
                            </button>
                            <div class="dropdown-menu" aria-labelledby="navbarDropdown"
                                 style="background-color: #f7f7f7">
                                <form action="<c:url value="/controller"/>" method="post" class="dropdown-item">
                                    <input type="hidden" name="command" value="to-user-library">
                                    <input type="hidden" name="current" value="0">
                                    <input type="submit" name="submit" class="btn btn-light btn-sm"
                                           value="<fmt:message key="button.users.look"/>">
                                </form>
                                <form action="<c:url value="/controller"/>" method="get" class="dropdown-item">
                                    <input type="hidden" name="command" value="to-register-admin">
                                    <input type="submit" name="submit" class="btn btn-light btn-sm"
                                           value="<fmt:message key="button.admin.new"/>">
                                </form>
                            </div>
                        </div>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-library">
                                <input type="submit" name="submit" value="<fmt:message key="button.library"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
                            <div class="form-group">
                                <input type="hidden" name="command" value="user-playlists">
                                <input type="submit" name="submit" value="<fmt:message key="button.playlists.my"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-profile">
                                <input type="submit" name="submit" value="<fmt:message key="button.profile"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
                            <div class="form-group">
                                <input type="hidden" name="command" value="logout">
                                <input type="submit" name="submit" value="<fmt:message key="button.logout"/>"
                                       class="form-control btn btn-secondary btn-sm">
                            </div>
                        </form>
                    </div>
                </c:when>
                <c:when test="${ sessionScope.user.admin eq false}">
                    <div class="buttons-wrapper d-flex float-md-right" style="height: 60px">
                        <a class="navbar-brand" href="#"
                           style="margin-right: 10px; padding-top: 0; padding-bottom: 0">
                            <form action="<c:url value="/controller"/>" method="get">
                                <input type="hidden" name="command" value="to-main">
                                <img src="<c:url value="/resources/secondary-logo.svg"/>"
                                     alt="music app"
                                     width="55" height="55" onclick="submit()">
                            </form>
                        </a>
                        <h4 class="mr-3 nav-item nav-link" style="padding-left: 0; padding-right: 0">${ sessionScope.user.firstname }, <span
                                class="badge badge-info"><fmt:message key="role.user"/></span></h4>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-library">
                                <input type="submit" name="submit" value="<fmt:message key="button.library"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
                            <div class="form-group">
                                <input type="hidden" name="command" value="user-playlists">
                                <input type="submit" name="submit" value="<fmt:message key="button.playlists.my"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="get" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
                            <div class="form-group">
                                <input type="hidden" name="command" value="to-profile">
                                <input type="submit" name="submit" value="<fmt:message key="button.profile"/>"
                                       class="form-control btn btn-light btn-sm">
                            </div>
                        </form>
                        <form action="<c:url value="/controller"/>" method="post" class="mr-3 nav-item nav-link"
                              style="padding-left: 0; padding-right: 0">
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


