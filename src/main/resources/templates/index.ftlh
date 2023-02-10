<!DOCTYPE html>
<html>
<head>
    <title>WEBMON</title>
</head>
<body>
<h4>Home Page</h4>
<a href="/auth">Sign Up</a><br/>
<a href="/auth">Sign In</a><br/>

<form action="/" method="get">
    Search by domain: <label>
        <input type="text" name="domain">
    </label><br/>
    <input type="submit" value="Search">

</form>
<br/><br/>
<#list websites as website>
    <div>
        <p><b>${website.domain}</b> | status is: ${website.status} | <a href="/website/${website.id}">More info...</a></p>
    </div>
<#else>
    <h3>No domains added</h3>
</#list>

<br/><br/>

<h3>Add new website</h3><br/>
<form action="/website/create" method="post" enctype="multipart/form-data">
    Domain: <label>
        <input type="text" name="domain"/>
    </label><br/><br/>
    Screenshot / Photo #1 <input type="file" name="file1"><br/><br/>
    Screenshot / Photo #2 <input type="file" name="file2"><br/>
    <input type="submit" value="Add website"/>
</form>

<#--<br/><br/>-->
<#--<h4>SignUp</h4><br/>-->
<#--<form action="/signup" method="post">-->
<#--    First name: <label>-->
<#--        <input type="text" name="firstname">-->
<#--    </label><br/><br/>-->
<#--    Last name: <label>-->
<#--        <input type="text" name="lastname">-->
<#--    </label><br/><br/>-->
<#--    Email: <label>-->
<#--        <input type="email" name="email">-->
<#--    </label><br/><br/>-->
<#--    Password: <label>-->
<#--        <input type="password" name="password">-->
<#--    </label><br/><br/>-->
<#--    &lt;#&ndash;    <input type="hidden" name="_csrf" value="${_csrf.token}">&ndash;&gt;-->
<#--    <input type="submit" value="SignUp"/>-->
<#--</form>-->

<#--<br/><br/>-->
<#--<h4>SignIn</h4>-->
<#--<form class="signin-form" action="/signin" method="post">-->
<#--    Email: <input type="email" name="email"><br/><br/>-->
<#--    Password: <input type="password" name="password"><br/><br/>-->
<#--    <input type="submit" value="SignIn"/>-->
<#--</form>-->

</body>
</html>