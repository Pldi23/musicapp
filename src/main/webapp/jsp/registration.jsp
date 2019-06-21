<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<h2>Please, submit registration form. Заполните анкету на русском</h2>
${errorRegistrationFormMessage}
<form action = "controller" method = "post" >
    <input type="hidden" name="command" value="register"/>
    <label>
        Your login * :
        <input type="text" name="login" pattern="^[(\w)-]{4,20}" required="true" placeholder="login"
        title="Login must be minimum 4, maximum 20 symbols, and contain only latin letter, numbers, and punctuation symbols like '-' and '_'"/>
    </label>
    <br>
    <label>
        Your password * :
        <input type="password" name="password" pattern="[A-Za-z0-9!@#$%^&*()_+={};:><.,/?`~±§-]{8,20}" required="true" placeholder="password"
        title="Password must be minimum 8, maximum 20 symbols, and contain at least 1 number, 1 latin uppercase letter, 1 latin lowercase letter, 1 punctuation. Only latin letters available, spaces are unavailable"/>
    </label>
    <br>
        <label>
            Your date of birth * :
            <input type="date" name="birthdate" title="You must be older than 6 years"/>
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
             <input type="text" name="firstname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required="true" placeholder="Firstname"
             title="First name must contain minimum 2 and maximum 30 letters"/>
        </label>
    <br>
        <label>
              Your last name * :
              <input type="text" name="lastname" pattern="[A-Za-zА-Яа-яЁё]{2,30}" required="true" placeholder="Lastname"
              title="Last name must contain minimum 2 and maximum 30 letters"/>
        </label>
    <br>
        <label>
              Your e-mail * :
              <input type="email" name="email" required="true" placeholder="e-mail" title="E-mail example johndoe@domainsample.com"/>
        </label>
    <br>
    <input type="submit" name="submit" value="Register">
    <br/>
</form>
</body>
</html>