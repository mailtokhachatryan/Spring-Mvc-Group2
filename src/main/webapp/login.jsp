<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h2>Welcome to login page</h2>

<%=request.getAttribute("message") != null ? request.getAttribute("message") : ""%>


<form action="/login" method="post">
    Username <input type="text" name="email"></br>
    Password <input type="password" name="password"></br>
    <div class="checkbox_div">
        <input class="checkbox" type="checkbox" name="rememberMe">
        <span style="width: 100%">Remember me</span>
    </div>
    <input type="submit" value="login">
</form>

</body>
</html>