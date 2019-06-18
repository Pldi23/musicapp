<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Please login</h2>
<form action = "controller" method = "post">
    <input type="hidden" name="command" value="login"/>
    <label>
        Enter Username
        <input type="text" name="login" pattern="\w{4,20}" required="true" placeholder="type your login here"/>
    </label>
    <br>
        Enter Password
        <input type="password" name="password" pattern="\w{4,20}" required="true" placeholder="type your password here"/>
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
<form action="controller" method="get">
<input type="hidden" name="command" value="toregistr"/>
<br>
    <input type="submit" name="submit" value="Registration">
    <br/>
</body>
</html>
