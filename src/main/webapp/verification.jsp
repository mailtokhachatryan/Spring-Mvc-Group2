<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Verification</title>
</head>
<body>

<h2>Verify your account</h2>

<%=request.getAttribute("message") != null ? request.getAttribute("message") : ""%>

<form action="/verify" method="post">
    Email <input type="text" name="email"></br>
    Code <input type="text" name="code"></br>
    <input type="submit" value="login">
</form>

</body>
</html>