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

                <div id="image-web-line">
                    <img class="image-preview" src="data:image/png;base64,${website.screenshot}" alt="${website.domain}_screenshot"/>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Domain: </div>
                    <div class="line-value">${website.domain}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Notes: </div>
                    <div class="line-value">${website.status}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">IP: </div>
                    <div class="line-value">${website.ip}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Location: </div>
                    <div class="line-value">${website.location}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Status: </div>
                    <div class="line-value">${website.delayResponse}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Load Time: </div>
                    <div class="line-value">${website.loadTime}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Id: </div>
                    <div class="line-value">${website.id}</div>
                </div>

                <div class="info-item-rem">

                    <form action="/website/update/${website.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="update-in-info" type="submit" value="Update"/>
                    </form>

                    <form action="/website/remove/${website.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="remove-in-info" type="submit" value="Remove"/>
                    </form>


                </div>

            </div>

        </div>
    </div>

</div>

</body>
</html>