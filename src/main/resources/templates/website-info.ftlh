<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <meta charset="UTF-8">
    <title>Monitoring ${website.domain}</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<H3>WEBMON | ${website.domain} Information</H3>

<div>
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


    <div class="info-block">
        <div class="info-left-list">
            <div class="list-web-items">

                <#list websites as website>
                    <ul>

                        <div class="web-show-inline">
                            <li class="list-li">
                                <a class="a-list" href="/website/${website.id}">${website.domain}</a>
                            </li>
                        </div>
                    </ul>
                <#else>
                    <h3>No domains added</h3>
                </#list>
            </div>

        </div>

        <div class="info-right-details">

            <div>
<#--                <#list images as img>-->
<#--                    <img class="preview-img" alt="Image" src="/images/${img.id}" height="160px"/><br/><br/>-->
<#--                </#list>-->
            </div>

            <div class="info-right-line">

                <div class="info-item">
                    <div class="line-label">Domain: </div>
                    <div class="line-value">${website.domain}</div>
                </div>

                <div class="info-item">
                    <div class="line-label">Notes: </div>
                    <div class="line-value">${website.notes}</div>
                </div>

                <div class="info-item">
                    <div class="line-label">IP: </div>
                    <div class="line-value">${website.ip}</div>
                </div>

                <div class="info-item">
                    <div class="line-label">Status: </div>
                    <div class="line-value">${website.status}</div>
                </div>

                <div class="info-item">
                    <div class="line-label">Id: </div>
                    <div class="line-value">${website.id}</div>
                </div>

                <div class="info-item-rem">
                    <form action="/website/remove/${website.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="rem-in-info" type="submit" value="Remove"/>
                    </form>
                </div>

            </div>

        </div>
    </div>

</div>

</body>
</html>