<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<hr/>
<hr/>
<div class="container w-100 bg-light">
    <div class="row">
        <div class="col-md-12">
            <fmt:message key="footer.copyright"/>
            <fmt:message key="build.timestamp"/>
        </div>
    </div>
</div>
</body>
</html>

