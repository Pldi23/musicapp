<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<div class="container-fluid bg-light">
    <div class="row justify-content-center">
        <div class="center">
            <div class="col-12">
                <div class="btn-toolbar" role="toolbar" style="padding-top: 15px">
                    <div class="btn-group mr-2" role="group" aria-label="First group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="show-all-tracks">
                            <input type="hidden" name="current" value="0">
                            <input type="submit" name="submit" value="<fmt:message key="button.showalltracks"/>"
                                   class="btn btn-secondary">
                        </form>
                    </div>
                    <div class="btn-group mr-2" role="group" aria-label="Second group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="show-all-playlists">
                            <input type="hidden" name="current" value="0">
                            <input type="submit" name="submit" value="<fmt:message key="button.showallplaylists"/>"
                                   class="btn btn-secondary">
                        </form>
                    </div>
                    <div class="btn-group mr-2" role="group" aria-label="Third group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="show-all-musicians">
                            <input type="hidden" name="current" value="0">
                            <input type="submit" name="submit" value="<fmt:message key="button.showallmusicians"/>"
                                   class="btn btn-secondary">
                        </form>
                    </div>
                    <c:if test="${ sessionScope.user.admin eq true }">
                        <div class="btn-group mr-2" role="group" aria-label="Fourth group">
                            <form action="<c:url value="/controller"/>" method="post">
                                <input type="hidden" name="command" value="to-upload-track">
                                <input type="submit" name="submit" value="<fmt:message key="button.upload.track"/>"
                                       class="btn btn-secondary">
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
