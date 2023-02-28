<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<h3>Profile</h3>
<div class="menu-line">
    <div class="menu-item"><a href="/">Homepage</a></div>
    <div class="menu-item"><a href="/auth">Profile</a></div>
    <div class="menu-item"><a href="/website">Website Monitoring</a></div>
    <div class="menu-item"><a href="/website">
            <form action="/user/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="logout"/>
            </form>
        </a></div>
</div>

<p><b>Email:</b> ${user.email}</p>
<p><b>First Name:</b> ${user.firstname}</p>
<p><b>Last Name:</b> ${user.lastname}</p>
<br/><br/>
<form action="/user/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="logout"/>
</form>

<br/><br/>

</body>
</html>