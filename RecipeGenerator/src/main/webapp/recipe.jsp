<%-- 
    Document   : recipe
    Created on : Jun 20, 2021, 3:03:21 AM
    Author     : Hamza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="style.css">
    </head>

    <body>
        <div class="heading">
            <h1>Recipe Generator</h1>
        </div>

        <div style="overflow:auto">
            <div class="menu">
                <h2>Ingredients List</h2>
                <!-- <div class="menu-ingredient"> -->
                <form method="GET" action="apiServlet" name="GenData">
                    <input type="hidden" value="<%
                        if (request.getAttribute("Email") != null) {
                            out.print(request.getAttribute("Email"));
                        } else {
                            out.println("Email id null");
                        }
                           %>" name="Email" >
                    <input type="text" class="ingredient" value="" name = "i1" placeholder="Ingredient 1" />
                    <input type="text" class="ingredient" value="" name = "i2" placeholder="Ingredient 2" />
                    <input type="text" class="ingredient" value="" name = "i3" placeholder="Ingredient 3" />
                    <input type="text" class="ingredient" value="" name = "i4" placeholder="Ingredient 4" />
                    <input type="text" class="ingredient" value="" name = "i5" placeholder="Ingredient 5" />
                    <input class="btn" type ="submit" name="GenData" value="Get Recipes"/>


                </form>
                <!-- </div> -->
            </div>

            <div class="main">
                <div class="button_tab">
                    <h2><%
                            out.print(request.getAttribute("Email"));%></h2>
                    <button class="recommended_recipes" onclick="getRecData()">Recommended Recipes</button>
                    <button class="view_fav" onclick="getFavData()">View Favorite Recipes</button>
                </div>
            </div>

            <div class="right">
                <div class="view_recipe" id="GenData" >
                    <%
                        if (request.getAttribute("GenDataFinal") != null) {
                            out.print(request.getAttribute("GenDataFinal"));
                        }
                    %>
                </div>
                <div class="view_recipe" id="FavData" style="display: none;">
                    <%
                        if (request.getAttribute("FavDataFinal") != null) {
                            out.print(request.getAttribute("FavDataFinal"));
                        }
                    %>
                </div>
                <div class="view_recipe" id="RecData" style="display: none;">
                    <%
                        if (request.getAttribute("RecDataFinal") != null) {
                            out.print(request.getAttribute("RecDataFinal"));
                        }
                    %>

                </div>

            </div>

        </div>

    </body>

</html>
<%

%>


<script>
    function getFavData() {
        document.getElementById("GenData").style.display = "none";
        document.getElementById("RecData").style.display = "none";
        document.getElementById("FavData").style.display = "block";
        ;
    }
    function getRecData() {
        document.getElementById("GenData").style.display = "none";
        document.getElementById("RecData").style.display = "block";
        document.getElementById("FavData").style.display = "none";
        ;
    }


</script>