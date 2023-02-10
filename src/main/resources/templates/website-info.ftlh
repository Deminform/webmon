<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebMonitor</title>
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


<hr>
<h3>Remove this domain from my list</h3>
<form action="/website/remove/${website.id}" method="post">
    <input type="submit" value="Remove"/>
</form>
</body>
</html>