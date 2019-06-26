<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html><head><title>Welcome</title></head>
<body>
<h3>Welcome</h3>
<hr/>
Admin ${adminFirstName}, hello!
Your role: ${role}
<hr/>
<a href="controller?command=logout">Logout</a>
<form action="controller" method="get">
    <input type="hidden" name="command" value="search"/>
    <label>
        Search panel
        <input type="text" name="searchrequest" placeholder="playlist, track, musician"/>
    </label>
    <input type="submit" name="submit" value="Search">
</form>
</body></html>