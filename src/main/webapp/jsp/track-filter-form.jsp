<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
    <title><fmt:message key="label.track.filter"/></title>
</head>
<body>
<form action="controller" method="get">
    <input type="hidden" name="command" value="filter-track">
    <input type="hidden" name="offset" value="0">
    <input type="hidden" name="order" value="marker">
    <fmt:message key="label.filter.trackname"/>
    <input type="text" name="trackname" value="${ trackname }">
    <fmt:message key="label.filter.singername"/>
    <input type="text" name="singer" value="${ singer}">
    <fmt:message key="label.releasedate.from.to"/>
    <input type="date" name="releaseFrom" value="${releaseFrom}">
    <input type="date" name="releaseTo" value="${releaseTo}">
    <fmt:message key="label.filter.genre"/>
    <select name="genre">
        <option selected="selected">${ genre }</option>
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
    <input type="submit" name="submit" value="<fmt:message key="button.filter"/>">
</form>
</body>
</html>
