<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Please login</h2>
${validatorMessage}
<form action = "controller" method = "post">
    <input type="hidden" name="command" value="login"/>
    <label>
        Enter Username
        <input type="text" name="login" pattern="^[(\w)-]{4,20}" required="true" placeholder="type your login here"
        title="Login must be minimum 4, maximum 20 symbols, and contain only latin letter, numbers, and punctuation symbols like '-' and '_'"/>
    </label>
    <br>
        Enter Password
        <input type="password" name="password" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="true" placeholder="type your password here"/>
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
    </form>
</body>
</html>
