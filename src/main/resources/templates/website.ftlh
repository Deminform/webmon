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
    <div class="menu-item"><a href="/auth/signin">Profile</a></div>
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
    <div class="web-show-stat" id="add-item-module">
        <h3 id="add-site">Add new website</h3>
        <form id="add-site-form" class="add-site-form" action="/website/create" method="post" enctype="multipart/form-data" onsubmit="return validateForm(event)">
            <label>Add new website</label>
            <input class="add-site-input-field" type="text" name="domain" placeholder="Domain"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input class="add-site-input-button" type="submit" value="Add website"/>
            <span class="error-message"></span>
        </form>
    </div>

    <#list websites as website>

        <div class="web-show-stat" id="${website.status}">

            <div class="header-web-show">
                <H4><b>${website.domain}</b></H4>
            </div>
            <div class="body-web-show">
                <div id="image-web-line">
                    <img class="image-preview" id="mini-view" src="data:image/png;base64,${website.screenshot}" alt="${website.domain}_screenshot"/>
                </div>
                <div id="body-web-line">
                    <div id="label">
                        Status:
                    </div>
                    <div id="value" class="${website.status}">
                        ${website.status}
                    </div>
                </div>

                <div id="body-web-line">
                    <div id="label">
                        IP:
                    </div>
                    <div id="value">
                        ${website.ip}
                    </div>
                </div>

                <div id="body-web-line">
                    <div id="label">
                        Location:
                    </div>
                    <div id="value">
                        ${website.location}
                    </div>
                </div>

                <div id="body-web-line">
                    <div id="label">
                        Delay:
                    </div>
                    <div id="value">
                        ${website.delayResponse}
                    </div>
                </div>

                <div id="body-web-line">
                    <div id="label">
                        Load time:
                    </div>
                    <div id="value">
                        ${website.loadTime}
                    </div>
                </div>

                <div id="body-web-line">
                    <div id="label">
                        Last update:
                    </div>
                    <div id="value">


                        <span class="calc-date" id="time-${website.id}">${website.lastUpdate}</span>


                    </div>
                </div>

            </div>

            <div class="bottom-web-show">

                <div class="part-info">
                    <a href="/website/${website.domain}">More info...</a>
                </div>

                <div class="part-update">
                    <form action="/website/update-local/${website.domain}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input class="update-in" type="submit" value="Update"/>
                    </form>
                </div>


                <div class="part-remove">
                    <form action="/website/remove/${website.domain}" method="post">
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

    function validateForm(event) {
        var domainInput = document.forms["add-site-form"]["domain"].value;
        var errorMessage = document.querySelector(".error-message");

        if (domainInput == "") {
            errorMessage.textContent = "Please enter a domain";
            event.preventDefault();
            return false;
        } else {
            errorMessage.textContent = "";
            return true;
        }
    }





</script>


</body>
</html>