<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recipe Generator Sign in</title>
        <link href="app.css" rel="stylesheet" type="text/css"/>
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="Err"><%
    if(request.getAttribute("error")!=null){
        out.print(request.getAttribute("error"));
    }

%></div>
        <div class="Container" >
            <div class = "App">
                <form class = "form" method="post" name="login" action="userServlet">

                    <h1>Log In</h1>

                    <input class="input" type="email" name="email_1" id="email" placeholder="email"required>

                    <input class="input" type="password" name="password_1" id="password" placeholder="password"required>


                    <input type ="submit" name="login" class="button" id="login" value="login"/>
                </form>
            </div>
            
            <div class = "App">
                <form class = "form" method="post" name="signin" action="userServlet">
                    <h1>Sign up</h1>

                    <input class="input" type="email" name="email" id = "email_1" placeholder="email"required>

                    <input class="input" type="text" name="country" id="country_1" placeholder="country"/>

                    <input class="input" type="number" name="age" id="age_1" placeholder="age"/>

                    <input class="input" type="password" name="password" id="password_1" placeholder="password"required>

                    <input class="input" type="password" name="re_password" id="re_password_1" placeholder ="re-enter password"required>
                    <input type="submit" name="signin" class="button" id="signin" value="Sign up"/>
                </form>
            </div>
            
        </div>
    </body>
</html>

