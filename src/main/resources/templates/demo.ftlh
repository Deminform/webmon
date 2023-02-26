<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>Demo</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<h4>Website List</h4>

<form action="/website" method="get">
    Search by domain: <label>
        <input type="text" name="domain">
    </label><br/>
    <input type="submit" value="Search">
</form>

<br/><br/>

<#list websites as website>
    <div class="web-show-stat">
        <p><b>${website.domain}</b> | status is: ${website.status} | <a href="/website/${website.id}">More info...</a> </p>
    </div>

    <form action="/website/remove/${website.id}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Remove"/>
    </form>

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
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Add website"/>
</form>

<form action="/user/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="logout"/>
</form>
<br/><br/>
<a href="/">Homepage</a>

</body>
</html>