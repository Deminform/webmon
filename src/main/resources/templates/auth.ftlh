<!DOCTYPE html>
<html>
<head>
    <title>WEBMON</title>
</head>
<body>
<h1>Please SignUp</h1>
<br/>
<form action="/auth/signup" method="post">
    First name: <label>
        <input type="text" name="firstname">
    </label><br/><br/>
    Last name: <label>
        <input type="text" name="lastname">
    </label><br/><br/>
    Email: <label>
        <input type="email" name="email">
    </label><br/><br/>
    Password: <label>
        <input type="password" name="password">
    </label><br/><br/>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="SignUp"/>
</form>

<br/><br/>
<h1>Please SignUp</h1>
<br/><form class="signing-form" action="/auth/signing" method="post">
    Email: <input type="email" name="email"><br/><br/>
    Password: <input type="password" name="password"><br/><br/>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="SignIn"/>
</form>

<br/><br/>

<a href="/">Homepage</a>
<a href="/auth">SignUp / SignIn</a><br/><br/>
<a href="/api/v1/demo-controller">Demo page</a><br/><br/>
<a href="/logout">Logout</a>


<#--<#if errorMessage??>-->
<#--    <h2 style="color: orangered">${errorMessage}</h2>-->
<#--</#if>-->
</body>
</html>


