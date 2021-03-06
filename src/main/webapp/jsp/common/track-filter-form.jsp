<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        var today = new Date();
        var day=today.getDate()>9?today.getDate():"0"+today.getDate(); // format should be "DD" not "D" e.g 09
        var month=(today.getMonth()+1)>9?(today.getMonth()+1):"0"+(today.getMonth()+1);
        var year=today.getFullYear();

        $("#releaseDateTo").attr('max', year + "-" + month + "-" + day);
        $("#releaseDate").attr('max', year + "-" + month + "-" + day);
    });
</script>
<html>
<body>
<form action="<c:url value="/controller"/>" method="post" class="needs-validation" novalidate>
    <input type="hidden" name="command" value="filter-track">
    <input type="hidden" name="current" value="0">
    <input type="hidden" name="order" value="marker">
    <div class="form-group">
        <label for="inputTrackname"><fmt:message key="label.filter.trackname"/></label>
        <input type="text" class="form-control" id="inputTrackname" pattern="(?U).{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$"
               name="trackname" placeholder="<fmt:message key="placeholder.track.name"/>" value="${ requestScope.filter.trackName }"/>
        <div class="invalid-feedback">
            <fmt:message key="violation.trackname"/>
        </div>
    </div>
    <div class="form-group">
        <label for="inputSingername"><fmt:message key="label.filter.singername"/></label>
        <input type="text" name="singer" pattern="(?U).{1,30}" class="form-control" id="inputSingername" value="${ requestScope.filter.singerName}"
               placeholder="<fmt:message key="placeholder.singer"/>">
    </div>
    <div class="form-group">
        <label for="releaseDate"><fmt:message key="label.releasedate.from.to"/></label>
        <input type="date" class="form-control" id="releaseDate" name="releaseFrom" value="${ requestScope.filter.fromDate}">
        <div class="invalid-feedback">
            <fmt:message key="violation.release.filter"/>
        </div>
    </div>
    <div class="form-group">
        <label for="releaseDateTo"></label>
        <input type="date" class="form-control" id="releaseDateTo" name="releaseTo" value="${requestScope.filter.toDate}">
        <div class="invalid-feedback">
            <fmt:message key="violation.release.filter"/>
        </div>
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
