<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty sessionScope.locale ? sessionScope.locale : pageContext.request.locale }"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head><title><fmt:message key="label.updatetrack"/></title></head>
<body>
<c:import url="../music-lib/topbar.jsp"/>
<hr/>
<div class="container-fluid bg-light">
    <div class="row">
        <div class="col-2">
            <c:import url="../common/track-filter-form.jsp"/>
        </div>
        <div class="col-8">
            <fmt:message key="message.update"/><c:out value="${ requestScope.entity.uuid }."/>
            <p class="text-warning"><ctg:violations violations="${ requestScope.violations }"/></p>
            <p class="text-info"><c:out value="${ requestScope.updateResult }"/></p>
            <form action="<c:url value="/controller"/>" method="post"
                  class="needs-validation" novalidate>
                <input type="hidden" name="command" value="update-track">
                <input type="hidden" name="uuid" value="${ requestScope.entity.uuid }">
                <input type="hidden" name="id" value="${ requestScope.entity.id }">
                <div class="container">
                    <div class="row">
                        <div class="col-4 ">
                            <label>
                                <fmt:message key="label.track.name"/>
                            </label>
                            <input type="text" class="form-control" name="trackname" required=""
                                   pattern=".{1,30}(?<!(.mp3)|(.wav)|(.audio)|(.format))$"
                                   placeholder="<fmt:message key="placeholder.track.name"/>"
                                   value="${ requestScope.entity.name }"/>
                            <div class="invalid-feedback">
                                <fmt:message key="violation.trackname"/>
                            </div>


                        </div>
                        <div class="col-4">
                            <div class="form-group">
                                <label>
                                    <fmt:message key="label.releasedate"/>
                                </label>
                                <input class="form-control" type="date" id="datefield" name="releasedate" required
                                       value="${ requestScope.entity.releaseDate}"/>
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
                                    <option selected="selected">${ requestScope.entity.genre.title }</option>
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
                                <c:forEach var="singer" items="${ requestScope.entity.singers}">
                                    <input type="text" class="form-control" style="margin-top: 5px" name="singer"
                                           pattern=".{1,30}" value="${ singer.name }">
                                </c:forEach>
                                <input type="text" class="form-control" pattern=".{1,30}" style="margin-top: 5px"
                                       name="singer" placeholder="<fmt:message key="placeholder.addsinger"/>">
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
                                <c:forEach var="author" items="${ requestScope.entity.authors}">
                                    <input type="text" class="form-control" style="margin-top: 5px" name="author"
                                           pattern="(?U).{1,30}" value="${ author.name }">
                                </c:forEach>
                                <input type="text" class="form-control" pattern=".{1,30}" style="margin-top: 5px"
                                       name="author" placeholder="<fmt:message key="placeholder.addauthor"/>">
                                <div class="invalid-feedback">
                                    <fmt:message key="violation.author"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <input type="submit" class="btn btn-outline-dark" name="submit"
                       value="<fmt:message key="button.update"/>">
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
</html>