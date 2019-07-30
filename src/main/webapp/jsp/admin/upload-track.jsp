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

            <h3><fmt:message key="label.upload.track"/></h3>
            <form action="<c:url value="/controller"/>" enctype="multipart/form-data" method="post"
                  class="needs-validation" novalidate>
                <input type="hidden" name="command" value="upload-track"/>
                <div class="container">
                    <div class="row">
                        <div class="col-12">
                            <div class="input-group mb-3">
                                <div class="custom-file">
                                    <input type="file" class="form-control custom-file-input" name="mediapath"
                                           accept=".mp3" required id="inputGroupFile01"
                                           aria-describedby="inputGroupFileAddon01"/>
                                    <label class="custom-file-label" for="inputGroupFile01">
                                        <fmt:message key="label.file.choose"/>
                                    </label>
                                    <div class="invalid-feedback">
                                        <fmt:message key="violation.format"/>
                                    </div>
                                </div>
                                <script type="application/javascript">
                                    $('input[type="file"]').change(function (e) {
                                        var fileName = e.target.files[0].name;
                                        $('.custom-file-label').html(fileName);
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4 ">
                            <label>
                                <fmt:message key="label.track.name"/>
                            </label>
                            <input type="text" class="form-control" name="trackname" required=""
                                   pattern=".{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$"
                                   placeholder="<fmt:message key="placeholder.track.name"/>"/>
                            <div class="invalid-feedback">
                                <fmt:message key="violation.trackname"/>
                            </div>


                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label>
                                    <fmt:message key="label.releasedate"/>
                                </label>
                                <input class="form-control" type="date" id="datefield" name="releasedate" required/>
                                <div class="invalid-feedback">
                                    <fmt:message key="violation.futurerelease"/>
                                </div>
                            </div>
                            <c:import url="../common/maxdate.jsp"/>
                            <script>
                                validateDateField("datefield");
                            </script>
                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label>
                                    <fmt:message key="label.genre"/>
                                </label>
                                <br>
                                <select name="genre" class="custom-select">
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
                        </div>
                    </div>
                    <div class="row justify-content-center">
                        <div class="col-6" style="padding-left: 50px">
                            <div class="form-group">
                                <label>
                                    <fmt:message key="label.singers"/>
                                </label>
                                <input type="text" class="form-control" name="singer" required=""
                                       pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.singer"/>1" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="singer" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.singer"/>2" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="singer" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.singer"/>3" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="singer" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.singer"/>4" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="singer" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.singer"/>5" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="singer" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.singer"/>6" style="margin-top: 5px"/>
                                <div class="invalid-feedback">
                                    <fmt:message key="violation.singer"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-6" style="padding-left: 50px">
                            <div class="form-group">
                                <label>
                                    <fmt:message key="label.authors"/>
                                </label>
                                <input type="text" class="form-control" name="author" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.author"/>1" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="author" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.author"/>2" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="author" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.author"/>3" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="author" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.author"/>4" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="author" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.author"/>5" style="margin-top: 5px"/>
                                <input type="text" class="form-control" name="author" pattern=".{1,30}"
                                       placeholder="<fmt:message key="placeholder.author"/>6" style="margin-top: 5px"/>
                                <div class="invalid-feedback">
                                    <fmt:message key="violation.author"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="submit" class="btn btn-outline-dark" name="submit"
                       value="<fmt:message key="button.upload.track"/>">
                <br/>
            </form>
        </div>
        <div class="col-2">
            <img class="img-fluid" src="<c:url value="/resources/primary-logo.svg"/>" alt="music app">
            <c:import url="../common/search-form.jsp"/>
        </div>
    </div>
</div>
<c:import url="../common/footer.jsp"/>
</body>
<script>src = "maxdate.js"</script>
</html>