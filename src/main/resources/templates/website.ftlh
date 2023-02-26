<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>WEBMON</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<h4>Website List</h4>

<form action="/website" method="get">
    Search by domain: <label> Some text </label>
        <input type="text" name="domain">
    <input type="submit" value="Search">

    <br/>

</form>
<br/><br/>
<div class="web-show-stat-container">
    <#list websites as website>
        <div class="web-show-stat">
            <p><b>${website.domain}</b>
                <br/>
                status is: ${website.status}
                <br/>
                <a href="/website/${website.id}">More info...</a></p>
            <form action="/website/remove/${website.id}" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Remove"/>
            </form>
        </div>
    <#else>
    <h3>No domains added</h3>
    </#list>
</div>



<br/><br/>

<h3>Add new website</h3><br/>

<form action="/website/create" method="post" enctype="multipart/form-data">
    Domain: <label>Some text</label>
        <input type="text" name="domain"/>
    Screenshot #1 <input type="file" name="file1">
    Screenshot #2 <input type="file" name="file2">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Add website"/>
</form>

<br/><br/>
<a href="/">Homepage</a>
<br/><br/>
<a href="/demo">Demo page</a><br/><br/>
<br/><br/>
<a href="/website">Website Monitoring</a><br/><br/>

</body>
</html>