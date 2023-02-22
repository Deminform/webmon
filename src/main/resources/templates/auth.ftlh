<!DOCTYPE html>
<html lang="en">
<head>
    <title>Authentication page</title>
</head>
<body>
<h1>SignUp</h1>
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
<h1>SignIn</h1>
<br/><form class="signing-form" action="/auth/signing" method="post">
    Email: <input type="email" name="email"><br/><br/>
    Password: <input type="password" name="password"><br/><br/>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="SignIn"/>
</form>

<br/><br/>


<form action="/user/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="logout"/>
</form>

<br/><br/>

<a href="/">Homepage</a>
<br/><br/>
<a href="/api/v1/demo-controller">Demo page</a><br/><br/>
<br/><br/>
<a href="/website">Website Monitoring</a><br/><br/>

</body>
</html>


