<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html><head><title>Welcome</title></head>
<body>
<h3>Welcome</h3>
<hr/>
${userFirstName}, hello!
your role: ${role}
<hr/>
<a href="controller?command=logout">Logout</a>
</body></html>