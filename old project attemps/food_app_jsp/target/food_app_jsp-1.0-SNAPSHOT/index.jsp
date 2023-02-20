<%-- 
    Document   : index
    Created on : Jun 20, 2021, 1:33:24 AM
    Author     : Hamza
--%>

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
        <div class="Container" >
            <div class = "App">
                <form class = "form" action="userServlet.java" method="GET">

                    <h1>Log In</h1>

                    <input class="input" type="email" name="email" id="email" placeholder="email"/>

                    <input class="input" type="password" name="password" id="password" placeholder="password"/>


                    <input type ="submit" name="login" class="button" id="login" value="login"/>
                </form>
            </div>
            <div class = "App">
                
            </div>
        </div>
    </body>
</html>
