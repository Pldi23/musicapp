<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<div class="container w-100 bg-light">
    <div class="row">
        <div class="col-2">
        </div>
        <div class="col-8">
            <div class="btn-toolbar" role="toolbar">
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
                <c:if test="${ sessionScope.user.admin eq true }">
                    <div class="btn-group mr-2" role="group" aria-label="Sixth group">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="user-playlists">
                            <input type="submit" name="submit" value="<fmt:message key="button.playlists.admin"/>"
                                   class="btn btn-secondary">
                        </form>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-2">
        </div>
    </div>
</div>
</body>
</html>
