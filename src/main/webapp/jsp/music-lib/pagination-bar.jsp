<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<body>
<nav aria-label="Page navigation example">
    <ul class="pagination">
<%--        --------%>
        <li class="page-item">
            <c:if test="${ requestScope.previousunavailable eq 'false' }">
                <form action="<c:url value="/controller"/>" method="post">
                    <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                    <input type="hidden" name="direction" value="previous">
                    <input type="hidden" name="current" value="${ requestScope.current }">
                    <input type="submit" class="page-link" name="submit"
                           value="<fmt:message key="button.previous"/>">
                </form>
            </c:if>
        </li>
<%------------%>
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
                <c:when test="${ loop.first or loop.last or element eq 2 or element eq fn:length(requestScope.size) - 1 }">
                    <li class="page-item">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                            <input type="hidden" name="direction" value="direct">
                            <input type="hidden" name="current" value="${ element }">
                            <input type="submit" class="page-link" name="submit"
                                   value="${ element }">
                        </form>
                    </li>
                </c:when>
                <c:when test="${ not loop.first and (element eq requestScope.current - 1
                or element eq requestScope.current + 1 or element eq requestScope.current - 2
                or element eq requestScope.current + 2) }">
                    <c:if test="${ element eq requestScope.current - 2 and element > 3 }">
                        <li class="page-item">
                            <span class="page-link"><c:out value="..."/></span>
                        </li>
                    </c:if>
                    <li class="page-item">
                        <form action="<c:url value="/controller"/>" method="post">
                            <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                            <input type="hidden" name="direction" value="direct">
                            <input type="hidden" name="current" value="${ element }">
                            <input type="submit" class="page-link" name="submit"
                                   value="${ element }">
                        </form>
                    </li>
                    <c:if test="${ element eq requestScope.current + 2 and element lt fn:length(requestScope.size) - 2 }">
                        <li class="page-item">
                            <span class="page-link"><c:out value="..."/></span>
                        </li>
                    </c:if>
                </c:when>
            </c:choose>
        </c:forEach>
<%--        -------------%>
    <li class="page-item">
        <c:if test="${ requestScope.nextunavailable eq 'false'}">
            <form action="<c:url value="/controller"/>" method="post">
                <input type="hidden" name="command" value="${ requestScope.pageCommand }">
                <input type="hidden" name="direction" value="next">
                <input type="hidden" name="current" value="${ requestScope.current }">
                <input type="submit" class="page-link" name="submit"
                       value="<fmt:message key="button.next"/>">
            </form>
        </c:if>
    </li>
<%--        --------------%>
    </ul>
</nav>
</body>
</html>
