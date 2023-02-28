<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>WEBMON</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<h3>Website List</h3>

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

<div class="search-result">
    <form class="search-form-main" action="/website" method="get">
        Search by domain: <label> Some text </label>
        <input class="search-input" type="text" name="domain">
        <input type="submit" value="Search">
    </form>
</div>

<div class="web-show-stat-container">
    <#list websites as website>

        <div class="web-show-stat">

            <div class="header-web-show">
                <H4><b>${website.domain}</b></H4>
            </div>
            <div class="body-web-show">
                <div id="body-web-line">Status: ${website.status}</div>
                <div id="body-web-line">IP: ${website.ip}</div>
                <div id="body-web-line">Delay: ${website.delayResponse}</div>
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

    <div class="web-show-stat" id="add-item-module">
        <h3>Add new website</h3>
        <form action="/website/create" method="post" enctype="multipart/form-data">
            <label>Some text</label>
            <input type="text" name="domain" placeholder="Domain"/>
<#--            Screenshot #1 <input type="file" name="file1">-->
<#--            Screenshot #2 <input type="file" name="file2">-->
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Add website"/>
        </form>
    </div>

</div>

</body>
</html>