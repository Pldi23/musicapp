<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/admin-main.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.welcome"/></title></head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
            <c:import url="../locale-form.jsp"/>
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
        <div class="col-4">
            <h3><fmt:message key="label.welcome"/></h3>
        </div>
        <div class="col-8">
            <h3><fmt:message key="label.suggestions"/></h3>
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
            <ctg:print-tracks head="false" tracks="${ requestScope.tracks }"/>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
            <c:import url="../search-form.jsp"/>
        </div>
    </div>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="to-upload-track">--%>
<%--    <input type="submit" name="submit" value="<fmt:message key="button.upload.track"/>">--%>
<%--</form>--%>
<%--<c:import url="../library/library-form.jsp"/>--%>

<%--<form action="controller" method="get">--%>
<%--    <input type="hidden" name="command" value="to-library">--%>
<%--    <input type="submit" name="submit" value="<fmt:message key="button.library"/>">--%>
<%--</form>--%>

<%--<form action="controller" method="post">--%>
<%--    <input type="hidden" name="command" value="query"/>--%>
<%--    <h3>Remove track</h3>--%>
<%--    <label>--%>
<%--        query track for removing :--%>
<%--        <input type="text" name="searchrequest" required="" placeholder="track name"/>--%>
<%--    </label>--%>
<%--    <input type="submit" name="submit" value="query">--%>
<%--</form>--%>
<%--<br>--%>
<%--<table>--%>
<%--    <c:if test="${not empty tracks}">--%>
<%--        <c:out value="Tracks"/>--%>
<%--    </c:if>--%>
<%--    <c:forEach var="track" items="${tracks}" varStatus="status">--%>
<%--        <tr>--%>
<%--            <td><c:out value="${ track.id }"/></td>--%>
<%--            <td><c:out value="${ track.name }"/></td>--%>
<%--            <td><c:out value="${ track.singers }"/></td>--%>
<%--            <td><c:out value="${ track.genre }"/></td>--%>
<%--            <td>--%>
<%--                <audio controls>--%>
<%--                    <source src="music/${track.uuid}" type="audio/mpeg">--%>
<%--                </audio>--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <form method="get" action="controller">--%>
<%--                    <input type="hidden" name="command" value="areyousure">--%>
<%--                    <input type="hidden" name="uuid" value="${ track.uuid }">--%>
<%--                    <input type="hidden" name="name" value="${ track.name }">--%>
<%--                    <input type="submit" name="submit" value="remove">--%>
<%--                </form>--%>
<%--            </td>--%>
<%--            <td>--%>
<%--                <form method="get" action="controller">--%>
<%--                    <input type="hidden" name="command" value="toupdatetrack" >--%>
<%--                    <input type="hidden" name="uuid" value="${ track.uuid }">--%>
<%--                    <input type="hidden" name="name" value="${ track.name }">--%>
<%--                    <input type="submit" name="submit" value="update">--%>
<%--                </form>--%>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--</table>--%>
<%--${removeResult}--%>
<%--${updateResult}--%>
