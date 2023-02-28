<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>Authentication page</title>
        <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
</head>
<body>

<div class="top-nav">
    <a href="/">Home</a>
</div>


<div class="container">
    <div class="form-wrapper left">
        <h1>SignIn</h1>
        <form class="signing-form" action="/auth/signing" method="post">
            <div class="form-field">
                <label>Email:</label>
                <input type="email" name="email" placeholder="Email:" />
            </div>
            <div class="form-field">
                <label>Password:</label>
                <input type="password" name="password" placeholder="Password:"/>
            </div>
            <div class="empty-space"></div>
            <div class="form-field">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="submit" value="SignIn" />
            </div>
        </form>
    </div>
    <div class="form-divider"></div>
    <div class="form-wrapper right">
        <h1>SignUp</h1>
        <form class="signup-form" action="/auth/signup" method="post">
            <div class="form-field">
                <label>First name:</label>
                <input type="text" name="firstname" placeholder="First name:" />
            </div>
            <div class="form-field">
                <label>Last name:</label>
                <input type="text" name="lastname" placeholder="Last name:" />
            </div>
            <div class="form-field">
                <label>Email:</label>
                <input type="email" name="email" placeholder="Email:" />
            </div>
            <div class="form-field">
                <label>Password:</label>
                <input type="password" name="password" placeholder="Password:"/>
            </div>
            <div class="empty-space"></div>
            <div class="form-field">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="submit" value="SignUp" />
            </div>
        </form>
    </div>
</div>
</body>
</html>
