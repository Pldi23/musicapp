<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="page" value="/jsp/admin/upload-track.jsp" scope="request"/>
<html>
<head><title><fmt:message key="label.upload.track"/></title></head>
<body>
<c:import url="../music-lib/topbar.jsp"/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-info"><c:out value="${ requestScope.addResult }"/></p>

            <form action="<c:url value="/controller"/>" enctype="multipart/form-data" method="post">
                <input type="hidden" name="command" value="upload-track"/>
                <h3><fmt:message key="label.upload.track"/></h3>
                <label>
                    <fmt:message key="label.track.path"/>
                    <input type="file" name="mediapath" accept=".mp3"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.track.name"/>
                    <input type="text" name="trackname" required=""
                           pattern="(?U).{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$"
                           placeholder="<fmt:message key="placeholder.track.name"/>"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.singers"/>
                    <input type="text" name="singer" required="" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.singer"/>1"/>
                    <input type="text" name="singer" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.singer"/>2"/>
                    <input type="text" name="singer" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.singer"/>3"/>
                    <input type="text" name="singer" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.singer"/>4"/>
                    <input type="text" name="singer" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.singer"/>5"/>
                    <input type="text" name="singer" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.singer"/>6"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.authors"/>
                    <input type="text" name="author" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.author"/>1"/>
                    <input type="text" name="author" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.author"/>2"/>
                    <input type="text" name="author" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.author"/>3"/>
                    <input type="text" name="author" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.author"/>4"/>
                    <input type="text" name="author" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.author"/>5"/>
                    <input type="text" name="author" pattern="(?U).{1,30}"
                           placeholder="<fmt:message key="placeholder.author"/>6"/>
                </label>
                <br>
                <label>
                    <fmt:message key="label.genre"/>
                    <select name="genre">
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
                    <input type="date" id="datefield" name="releasedate"/>
                </label>
                <br>
                <br>
                <input type="submit" class="btn btn-outline-dark" name="submit" value="<fmt:message key="button.upload.track"/>">
                <br/>
            </form>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/login-page-image.svg"/>" alt="music app">
            <c:import url="../common/search-form.jsp"/>
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
<script>src = "maxdate.js"</script>
</html>