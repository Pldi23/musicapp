<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>

<body>
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <li class="page-item"><c:if test="${ requestScope.previousunavailable eq 'false' }">
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                <input type="hidden" name="direction" value="previous">
                <input type="hidden" name="current" value="${ requestScope.current }">
                <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.previous"/>">
            </form>
        </c:if></li>
        <li class="page-item">
            <c:if test="${ requestScope.nextunavailable eq 'false'}">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                    <input type="hidden" name="direction" value="next">
                    <input type="hidden" name="current" value="${ requestScope.current }">
                    <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.next"/>">
                </form>
            </c:if>
        </li>
    </ul>
</nav>
<nav aria-label="Page navigation example">
    <ul class="pagination">
        <c:forEach var="element" items="${ requestScope.size }" varStatus="loop">
            <c:choose>
                <c:when test="${ element eq requestScope.current }">
                    <li class="page-item active" aria-current="page">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                            <input type="hidden" name="direction" value="direct">
                            <input type="hidden" name="current" value="${ element }">
                            <input type="submit" class="page-link" name="submit"
                                   value="${ element }">
                        </form>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                            <input type="hidden" name="direction" value="direct">
                            <input type="hidden" name="current" value="${ element }">
                            <input type="submit" class="page-link" name="submit"
                                   value="${ element }">
                        </form>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</nav>
</body>
</html>
