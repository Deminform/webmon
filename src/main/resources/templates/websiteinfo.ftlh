<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <meta charset="UTF-8">
    <title>WebMonitor</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body style="background: #101010; color: #d2d2d2">
<h1>WEBMON</h1><hr>
<h4>${website.domain} Information</h4>


<#list images as img>
    <img alt="Image" src="/images/${img.id}" height="160px"/><br/><br/>
</#list>
<b>Domain: </b>${website.domain}<br/>
<b>Notes: </b>${website.notes}<br/>
<b>IP: </b>${website.ip}<br/>
<b>Status: </b>${website.status}<br/>
<b>id: </b>${website.id}<br/>

<form action="/website/remove/${website.id}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Remove"/>
</form>

<br/><br/>
<a href="/">Homepage</a>
<br/><br/>
<a href="/demo">Demo page</a><br/><br/>
<br/><br/>
<a href="/website">Website Monitoring</a><br/><br/>
<br/><br/>
<form action="/user/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="logout"/>
</form>
</body>
</html>