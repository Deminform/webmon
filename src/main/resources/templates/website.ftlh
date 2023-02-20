<!DOCTYPE html>
<html>
<head>
    <title>WEBMON</title>
</head>
<body>
<h4>Website List</h4>

<form action="/" method="get">
    Search by domain: <label>
        <input type="text" name="domain">
    </label><br/>
    <input type="submit" value="Search">

</form>

<#list websites as website>
    <div>
        <p><b>${website.domain}</b> | status is: ${website.status} | <a href="/website/${website.id}">More info...</a></p>
    </div>
<#else>
    <h3>No domains added</h3>
</#list>
<hr>
<h3>Add new website</h3>
<form action="/website/create" method="post" enctype="multipart/form-data">
    Domain: <label>
        <input type="text" name="domain"/>
    </label><br/><br/>
    Screenshot / Photo #1 <input type="file" name="file1"><br/><br/>
    Screenshot / Photo #2 <input type="file" name="file2"><br/>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Add website"/>
</form>

</body>
</html>


