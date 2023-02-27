<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>WEBMON</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<h4>Website List</h4>

<div>
    <a href="/">Homepage</a>
</div>


<div>
    <a href="/auth">Profile</a><br/><br/>
</div>



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

            <div class="header-web-show">
                <H4><b>${website.domain}</b></H4>
            </div>
            <div class="body-web-show">
                <p>Status: ${website.status}</p>

            </div>

            <div class="bottom-web-show">

                <div class="part-info">
                    <a href="/website/${website.id}">More info...</a>
                </div>

                <div class="part-remove">
                    <form action="/website/remove/${website.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="rem-in" type="submit" value="Remove"/>
                    </form>
                </div>

            </div>

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


</body>
</html>