<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Locale</title>
</head>
<body>
<form action="controller" method="get">
    <input type="hidden" name="command" value="set-locale">
    <input type="hidden" name="page" value="${ requestScope.page }">
    <select name="locale" onchange="submit()">
        <option selected="selected">${ not empty locale ? locale : pageContext.request.locale }</option>
        <option value="en_US">EN</option>
        <option value="ru_RU">RU</option>
        <option value="ru_BY">BY</option>
    </select>
</form>
</body>
</html>