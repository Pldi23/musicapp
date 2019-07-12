<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="page" value="/jsp/verification.jsp" scope="request"/>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html><head><title><fmt:message key="label.welcome"/></title></head>
<hr/>
<fmt:message key="message.emailsent"/> ${email}.
<fmt:message key="message.verification"/>
<hr/>
<form action="controller" method="get">
    <input type="hidden" name="command" value="to-login">
    <input type="submit" name="submit" value="<fmt:message key="button.tologin"/>">
</form>
<%--<script type="text/javascript">--%>
<%--    document.hiddenForm.submit();--%>
<%--</script>--%>
</body>
</html>