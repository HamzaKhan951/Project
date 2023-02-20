/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hamza
 */
public class apiServlet extends HttpServlet {

   ArrayList <String> ingredients = new ArrayList<>();
   UserRecipe UR;
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            response.setContentType("text/html");  
            String email = (String) request.getAttribute("Email");
            
            UR = new UserRecipe(email);
            
            if(email==null){
                email = (String) request.getParameter("Email");
                UR = new UserRecipe(email);
                if(email==null){
                    out.print("email is null");
                }
            }
            
            if (request.getParameter("GenData") != null ) {
                
                ingredients.add(request.getParameter("i1"));
                ingredients.add(request.getParameter("i2"));
                ingredients.add(request.getParameter("i3"));
                ingredients.add(request.getParameter("i4"));
                ingredients.add(request.getParameter("i5"));
                
                String data = GenData(ingredients);
                UR.add_to_freq(ingredients);
                UR.getGenData(data);
                
                data = GenData(UR.FreqIng);
                UR.getRecData(data);
                //out.println(data + "data retrived" + ingredients.get(0) + " </br>" + UR.FavRecipe.get(0) + " </br>"  + UR.GenDataFinal);
                request.setAttribute("Email", email);
                request.setAttribute("GenDataFinal", UR.GenDataFinal);
                request.setAttribute("FavDataFinal", UR.FavDataFinal);
                request.setAttribute("RecDataFinal", UR.RecDataFinal);
                RequestDispatcher rd = request.getRequestDispatcher("recipe.jsp");
                rd.forward(request, response);
            }
            else if (request.getParameter("AddFav") != null ){
                String id = request.getParameter("id");
                out.print(UR.Email + "data added / removed " + UR.FavDataFinal);
                UR.addFav(id);
                String data = "";
                data = GenData(UR.FreqIng);
                UR.getRecData(data);
                request.setAttribute("email", email);
                request.setAttribute("GenDataFinal", UR.GenDataFinal);
                request.setAttribute("FavDataFinal", UR.FavDataFinal);
                request.setAttribute("RecDataFinal", UR.RecDataFinal);
                RequestDispatcher rd = request.getRequestDispatcher("recipe.jsp");
                rd.forward(request, response);
                //out.print(UR.Email + "data added / removed " + UR.FavDataFinal);
                
            }
            else{
                String data = "";
                data = GenData(UR.FreqIng);
                UR.getRecData(data);
                request.setAttribute("email", email);
                request.setAttribute("GenDataFinal", UR.GenDataFinal);
                request.setAttribute("FavDataFinal", UR.FavDataFinal);
                request.setAttribute("RecDataFinal", UR.RecDataFinal);
                RequestDispatcher rd = request.getRequestDispatcher("recipe.jsp");
                rd.forward(request, response);
            }
            
        }
    }

    String GenData(ArrayList <String> ingredients){
        
        int i = 0;
        String qry = "https://api.spoonacular.com/recipes/findByIngredients?ingredients=";
        boolean x = true;
        while (i < 5 && i<ingredients.size()) {
            //ingredients.get(i).replaceAll("\\s+","");
            
            if (!ingredients.get(i).equals("")) {
                if (x == true) {
                    qry = qry + ingredients.get(i);
                    x = false;
                }
                else {
                    qry = qry + ",+" + ingredients.get(i);
                } 
            }
            i++;
        }
        
        qry = qry + "&ranking=2&number=5&apiKey=dab1c331b016493681a18bfcc75420b3"; ///change apikey here and in class Recipe
        
        return qry;
        
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try {
           processRequest(request, response);
       } catch (SQLException ex) {
           Logger.getLogger(apiServlet.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try {
           processRequest(request, response);
       } catch (SQLException ex) {
           Logger.getLogger(apiServlet.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
