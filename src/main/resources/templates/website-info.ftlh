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
            <div class="list-web-items">

                <#list websites as website>
                    <ul>

                        <div class="web-show-inline">
                            <li class="list-li">
                                <a class="a-list" href="/website/${website.domain}">${website.domain}</a>
                            </li>
                        </div>
                    </ul>
                <#else>
                    <h3>No domains added</h3>
                </#list>
            </div>

        </div>

        <div class="info-right-details">

            <div class="info-right-line">

                <div id="image-web-line">
                    <img class="image-preview" src="data:image/png;base64,${website.screenshot}" alt="${website.domain}_screenshot"/>
                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Domain: </div>
                    <div class="line-value"><a href="https://${website.domain}" target="_blank">${website.domain}</a></div>
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
                    <div class="line-label" id="details">Last update: </div>
                    <div class="calc-date line-value" id="time-${website.id}">${website.lastUpdate}</div>

                </div>

                <div class="info-item">
                    <div class="line-label" id="details">Id: </div>
                    <div class="line-value">${website.id}</div>
                </div>

                <div class="info-item-rem">

                    <form action="/website/update/${website.domain}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="update-in-info" type="submit" value="Update"/>
                    </form>

                    <form action="/website/remove/${website.domain}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="remove-in-info" type="submit" value="Remove"/>
                    </form>


                </div>

            </div>

        </div>
    </div>

</div>

<script>
    window.onload = function () {
        var websites = document.querySelectorAll('.calc-date');
        for (var i = 0; i < websites.length; i++) {
            var website = websites[i];
            var lastUpdateStr = website.textContent.trim();
            var lastUpdateDate = new Date(lastUpdateStr);
            var now = new Date();
            var diff = Math.abs(now - lastUpdateDate) / 1000;

            var hours = Math.floor(diff / 3600);
            diff -= hours * 3600;
            var minutes = Math.floor(diff / 60);

            var timeString = "";
            if (hours > 0) {
                timeString += hours + " hour" + (hours > 1 ? "s" : "") + ", ";
            }
            if (minutes > 0 || timeString === "") {
                timeString += minutes + " minute" + (minutes > 1 ? "s" : "");
            }

            website.textContent = timeString + " ago";
        }
    };
</script>

</body>
</html>