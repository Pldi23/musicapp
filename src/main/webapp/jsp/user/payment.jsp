<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="page" value="/jsp/user/payment.jsp" scope="request"/>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.payment"/></title>
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
            <div class="alert alert-primary" role="alert">
                <h4 class="alert-heading"><fmt:message key="label.payment.request"/></h4>
                <hr>
                <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
                <p class="text-warning"><c:out value="${ requestScope.process }"/></p>
                <form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
                    <input type="hidden" name="command" value="pay">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="amount" id="radios1" value="0.99">
                        <label class="form-check-label" for="radios1">
                            <fmt:message key="plan.light"/>
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="amount" id="radios2" value="2.99">
                        <label class="form-check-label" for="radios2">
                            <fmt:message key="plan.medium"/>
                        </label>
                    </div>
                    <div class="form-check disabled">
                        <input class="form-check-input" type="radio" name="amount" id="exampleRadios3"
                               value="4.99" checked>
                        <label class="form-check-label" for="exampleRadios3">
                            <fmt:message key="plan.long"/>
                        </label>
                    </div>
                    <br>
                    <div class="form-group">
                        <label for="cardNumber">
                            <fmt:message key="label.card.number"/>
                        </label>
                        <div class="input-group">
                            <input type="text" name="cardNumber" class="form-control" id="cardNumber" placeholder="Valid Card Number 16 digits"
                                   required autofocus pattern="[0-9]{16,16}"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-7 col-md-7">
                            <div class="form-group">
                                <label for="expiryMonth">
                                    <fmt:message key="label.expiry.date"/>
                                </label>
                                <div class="row">
                                    <div class="col-xs-8 col-lg-8 pl-ziro">
                                        <input type="month" name="expiryDate" class="form-control" id="expiryMonth" placeholder="MM-YYYY"
                                               required/>
                                        <script>
                                            function validateMaxYear(fieldId) {
                                                $(document).ready(function () {
                                                    var today = new Date();
                                                    var month = (today.getMonth() + 1) > 9 ? (today.getMonth() + 1) : "0" + (today.getMonth() + 1);
                                                    var year = today.getFullYear() + 5;
                                                    $("#" + fieldId).attr('max', year  + "-" + month);
                                                });
                                            }
                                            validateMaxYear("expiryMonth")
                                        </script>
                                        <script>
                                            function validateMinYear(fieldId) {
                                                $(document).ready(function () {
                                                    var today = new Date();
                                                    var month = (today.getMonth() + 1) > 9 ? (today.getMonth() + 1) : "0" + (today.getMonth() + 1);
                                                    var year = today.getFullYear();
                                                    $("#" + fieldId).attr('min', year  + "-" + month);
                                                });
                                            }
                                            validateMinYear("expiryMonth")
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 col-md-5 pull-right">
                            <div class="form-group">
                                <label for="cvCode">
                                    <fmt:message key="label.cvc"/>
                                </label>
                                <input type="password" name="cvCode" class="form-control" id="cvCode" placeholder="CV" required pattern="[0-9]{3,3}"/>
                            </div>
                        </div>
                    </div>
                    <input type="submit" class="btn btn-success btn-lg" name="submit" value="<fmt:message key="button.pay"/>">
                </form>
            </div>
        </div>
        <div class="col-2">
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
</html>
