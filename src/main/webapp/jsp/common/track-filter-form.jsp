<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<body>
<form action="<c:url value="/controller"/>" method="post">
    <input type="hidden" name="command" value="filter-track">
    <input type="hidden" name="offset" value="0">
    <input type="hidden" name="order" value="marker">
    <div class="form-group">
        <label for="inputTrackname"><fmt:message key="label.filter.trackname"/></label>
        <input type="text" class="form-control" id="inputTrackname" pattern="(?U).{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$"
               name="trackname" placeholder="<fmt:message key="placeholder.track.name"/>"/>
    </div>
    <div class="form-group">
        <label for="inputSingername"><fmt:message key="label.filter.singername"/></label>
        <input type="text" name="singer" pattern="(?U).{1,30}" class="form-control" id="inputSingername" value="${ requestScope.singer}"
               placeholder="<fmt:message key="placeholder.singer"/>">
    </div>
    <div class="form-group">
        <label for="releaseDate"><fmt:message key="label.releasedate.from.to"/></label>
        <input type="date" class="form-control" id="releaseDate" name="releaseFrom" value="${ requestScope.releaseFrom}">
    </div>
    <div class="form-group">
        <label for="releaseDateTo"></label>
        <input type="date" class="form-control" id="releaseDateTo" name="releaseTo" value="${requestScope.releaseTo}">
    </div>
    <div class="form-group">
        <label for="inputGenre"><fmt:message key="label.filter.genre"/></label>
        <select name="genre" class="form-control" id="inputGenre">
            <option selected="selected"></option>
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
    </div>
    <div class="form-group">
        <input type="submit" name="submit" class="btn btn-dark"
               value="<fmt:message key="button.filter"/>">
    </div>
</form>
</body>
</html>
