<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Please login</h2>
<form action = "controller" method = "post">
    <input type="hidden" name="command" value="login"/>
    <label>
        Enter Username
        <input type="text" name="login"/>
    </label>
    <br>
        Enter Password
        <input type="text" name="password"/>
    </label>
    <br>
    <input type="submit" name="submit" value="Login">
    <br/>
    ${errorLoginPassMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
</form>
</body>
</html>
