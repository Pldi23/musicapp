<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Please, submit registration form</h2>
<form action = "controller" method = "post">
    <input type="hidden" name="command" value="register"/>
    <label>
        Your login * :
        <input type="text" name="login" pattern="\w{4,20}" required="true" placeholder="login"/>
    </label>
    <br>
    <label>
        Your password * :
        <input type="password" name="password" pattern="\w{4,20}" required="true" placeholder="password"/>
    </label>
    <br>
        <label>
            Your date of birth * :
            <input type="date" name="birthdate"/>
        </label>
    <br>
        <label>
             Your gender * :
             <select name="gender">
               <option value="male">Male</option>
               <option value="female">Female</option>
             </select>
        </label>
    <br>
        <label>
             Your first name * :
             <input type="text" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required="true" placeholder="login"/>
        </label>
    <br>
        <label>
              Your last name * :
              <input type="text" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required="true" placeholder="login"/>
        </label>
    <br>
        <label>
              Your e-mail * :
              <input type="email" name="e-mail" required="true" placeholder="e-mail"/>
        </label>
    <br>
    <input type="submit" name="submit" value="Register">
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