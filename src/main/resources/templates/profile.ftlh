<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <meta charset="UTF-8">
    <title>Account Details</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>
<H3>Account Details</H3>

<div>
    <div class="menu-line">
        <div class="menu-item"><a href="/">Homepage</a></div>
        <div class="menu-item"><a href="/auth/signin">Profile</a></div>
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
<#--            <div class="list-web-items">-->

<#--                <#list websites as website>-->
<#--                    <ul>-->

<#--                        <div class="web-show-inline">-->
<#--                            <li class="list-li">-->
<#--                                <a class="a-list" href="/website/${website.id}">${website.domain}</a>-->
<#--                            </li>-->
<#--                        </div>-->
<#--                    </ul>-->
<#--                <#else>-->
<#--                    <h3>No domains added</h3>-->
<#--                </#list>-->
<#--            </div>-->

        </div>

        <div class="info-right-details">

            <div class="info-right-line">

                <div class="info-item">
                    <div class="line-label" id="details">First name: </div>
                    <div class="line-value">${user.firstname}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Last name: </div>
                    <div class="line-value">${user.lastname}</div>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Email: </div>
                    <div class="line-value">${user.email}</div>
                </div>

            </div>

        </div>
    </div>

</div>

</body>
</html>