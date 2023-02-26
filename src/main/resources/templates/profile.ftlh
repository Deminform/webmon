<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<h1>Profile</h1>
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