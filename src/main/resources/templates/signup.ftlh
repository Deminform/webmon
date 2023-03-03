<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="icon" href="data:,">
    <title>Authentication page</title>
        <link rel="stylesheet" type="text/css" href="/static/css/styles.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

<div class="top-nav">
    <a href="/">Home</a>
</div>


<div class="container">



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
                <div class="password-input-container">
                    <input type="password" name="password" placeholder="Password:" />
                    <span class="material-icons password-icon" id="show-password-checkbox">visibility_off</span>
                </div>
            </div>

<#--            <div class="form-field">-->
<#--                <label>Show password:</label>-->
<#--                <input type="checkbox" id="show-password-checkbox">-->
<#--            </div>-->

            <div class="form-field">
                <div class="password-message"></div>
            </div>




            <div class="empty-space"></div>
            <div class="form-field">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <input type="submit" value="SignUp" />
            </div>
        </form>
    </div>
</div>

<script>
    const passwordInput = document.querySelector('input[name="password"]');
    const passwordMessage = document.querySelector('.password-message');

    passwordInput.addEventListener('input', function() {
        const password = passwordInput.value;
        let passwordStrength = 0;

        // Проверка на длину
        if (password.length >= 8) {
            passwordStrength += 1;
        }
        // Проверка на наличие цифр
        if (password.match(/\d/)) {
            passwordStrength += 1;
        }
        // Проверка на наличие букв в разных регистрах
        if (password.match(/[a-z]/) && password.match(/[A-Z]/)) {
            passwordStrength += 1;
        }

        // Выводим сообщение и меняем цвет
        if (passwordStrength === 0) {
            passwordMessage.innerText = "Password is too weak";
            passwordMessage.style.color = "red";
        } else if (passwordStrength === 1) {
            passwordMessage.innerText = "Password could be stronger";
            passwordMessage.style.color = "orange";
        } else if (passwordStrength === 2) {
            passwordMessage.innerText = "Password is moderately strong";
            passwordMessage.style.color = "yellow";
        } else if (passwordStrength === 3) {
            passwordMessage.innerText = "Password is strong";
            passwordMessage.style.color = "green";
        }
    });

    const showPasswordCheckbox = document.getElementById('show-password-checkbox');

    showPasswordCheckbox.addEventListener('change', function () {
        if (showPasswordCheckbox.checked) {
            passwordInput.type = 'text';
        } else {
            passwordInput.type = 'password';
        }
    });

    const passwordIcon = document.querySelector('.password-icon');

    passwordIcon.addEventListener('click', function() {
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            passwordIcon.innerText = 'visibility';
            passwordIcon.classList.add('visible');
        } else {
            passwordInput.type = 'password';
            passwordIcon.innerText = 'visibility_off';
            passwordIcon.classList.remove('visible');
        }
    });

</script>

</body>
</html>
