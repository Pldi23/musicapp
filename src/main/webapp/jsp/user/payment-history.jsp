<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/user/payment-history.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.payment.history"/></title>
    <style type="text/css">
        .my-custom-scrollbar {
            position: relative;
            height: 300px;
            overflow: auto;
        }

        .table-wrapper-scroll-y {
            display: block;
        }
    </style>
</head>
<body>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-1">
            <c:import url="../common/locale-form.jsp"/>
        </div>
        <div class="col-10">
            <c:import url="../common/header.jsp"/>
        </div>
        <div class="col-1">
            <img src="<c:url value="/resources/epam-logo.svg"/>" width="100" height="60" alt="">
        </div>
    </div>
</div>
<hr>
<div class="container w-100 bg-light">
    <div class="row">
        <div class="col-2">
        </div>
        <div class="col-8">
            <form action="<c:url value="/controller"/>" method="get">
                <input type="hidden" name="command" value="to-payment">
                <input type="submit" class="btn btn-success" name="submit"
                       value="<fmt:message key="button.extend"/>">
            </form>
            <fmt:parseDate value="${ sessionScope.user.paidPeriod }" pattern="yyyy-MM-dd" var="parsedDate"
                           type="date"/>
            <fmt:formatDate value="${ parsedDate }" var="newParsedDate" type="date" pattern="dd.MM.yyyy"/>
            <div class="alert alert-info" role="alert">
                <span><fmt:message key="label.paid.period"/>: ${ newParsedDate }</span>
            </div>
            <c:if test="${ not empty requestScope.entities }">
                <fmt:message key="label.payments"/>
                <div class="table-wrapper-scroll-y my-custom-scrollbar">
                    <table class="table table-bordered table-striped mb-0" style="">
                        <thead>
                        <tr>
                            <th scope="col"><c:out value="#"/></th>
                            <th scope="col"><fmt:message key="table.amount"/></th>
                            <th scope="col"><fmt:message key="table.card"/></th>
                            <th scope="col"><fmt:message key="table.date"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="payment" items="${ requestScope.entities }">
                            <tr class="table-bg-light">
                                <td>
                                    <c:out value="${ payment.id}"/>
                                </td>
                                <td>
                                    <c:out value="${ payment.amount }"/>
                                </td>
                                <td>
                                    <c:out value="${ payment.cardNumber}"/>
                                </td>
                                <td>
                                    <fmt:parseDate value="${ payment.dateTime }" pattern="yyyy-MM-dd"
                                                   var="parsedDate"
                                                   type="date"/>
                                    <fmt:formatDate value="${ parsedDate }" var="newParsedDate" type="date"
                                                    pattern="dd.MM.yyyy"/>
                                    <c:out value="${ newParsedDate }"/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
        <div class="col-2">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
