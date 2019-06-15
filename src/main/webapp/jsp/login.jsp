<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Please login</h2>
<form action = "/controller" method = "post">
    <input type="hidden" name="command" value="doLogin"/>
    <label>
        Enter Username
        <input type="text" name="login"/>
    </label>
    <br>
        Enter Password
        <input type="password" name="password"/>
    </label>
    <br>
    <input type="submit" name="submit" value="Login">
</form>
</body>
</html>
