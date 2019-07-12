<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<c:set var="page" value="/jsp/admin/update-track.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.updatetrack"/></title></head>
<body>
<c:import url="../locale-form.jsp"/>
<c:import url="../header.jsp"/>
<c:import url="../library-form.jsp"/>
<hr/>
<fmt:message key="message.update"/> ${ entity.uuid }.
<c:if test="${ not empty violations }">
    <ctg:violations violations="${ violations }"/>
</c:if>
${ updateResult }
<form action="controller" method="post">
    <input type="hidden" name="command" value="update-track">
    <input type="hidden" name="uuid" value="${ entity.uuid }">
    <input type="hidden" name="id" value="${ entity.id }">
    <br>
    <label>
        <fmt:message key="label.track.name"/>
        <input type="text" name="trackname" pattern="(?U).{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$" value="${ entity.name }">
    </label>
    <br>
    <label>
        <fmt:message key="label.singers"/>
        <c:forEach var="singer" items="${ entity.singers}">
            <input type="text" name="singer" pattern="(?U).{1,30}" value="${ singer.name }">
        </c:forEach>
        <input type="text" name="singer" placeholder="<fmt:message key="placeholder.addsinger"/>">
    </label>
    <br>
    <label>
        <fmt:message key="label.authors"/>
        <c:forEach var="author" items="${ entity.authors}">
            <input type="text" name="author" pattern="(?U).{1,30}" value="${ author.name }">
        </c:forEach>
        <input type="text" name="author" placeholder="<fmt:message key="placeholder.addauthor"/>">
    </label>
    <br>
    <label>
        <fmt:message key="label.genre"/>
        <select name="genre">
            <option selected="selected">${ entity.genre.title }</option>
            <option value="pop"><fmt:message key="option.pop"/></option>
            <option value="rock"><fmt:message key="option.rock"/></option>
            <option value="blues"><fmt:message key="option.blues"/></option>
            <option value="country"><fmt:message key="option.country"/></option>
            <option value="electronic"><fmt:message key="option.electronic"/></option>
            <option value="folk"><fmt:message key="option.folk"/></option>
            <option value="hip-hop"><fmt:message key="option.hip-hop"/></option>
            <option value="latin"><fmt:message key="option.latin"/></option>
            <option value="r&b"><fmt:message key="option.rnb"/></option>
            <option value="soul"><fmt:message key="option.soul"/></option>
            <option value="instrumental"><fmt:message key="option.instrumental"/></option>
            <option value="lounge"><fmt:message key="option.lounge"/></option>
            <option value="disco"><fmt:message key="option.disco"/></option>
            <option value="chanson"><fmt:message key="option.chanson"/></option>
            <option value="retro"><fmt:message key="option.retro"/></option>
            <option value="funk"><fmt:message key="option.funk"/></option>
        </select>
    </label>
    <br>
    <label>
        <fmt:message key="label.releasedate"/>
        <input type="date" name="releasedate" value="${ entity.releaseDate}"/>
    </label>
    <br>
    <input type="submit" name="submit" value="<fmt:message key="button.update"/>">
</form>
<br>
<hr/>
</body>
</html>