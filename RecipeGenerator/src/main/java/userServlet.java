
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Hamza
 */
public class userServlet extends HttpServlet {

    private users user;
    String a = "";
    String b = "";
    String age = "";
    String b_re = "";
    String d = "";

    public void sendRedirect(String url) throws IOException {

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            if (request.getParameter("login") != null) {
                //out.println("login request </br");
                a = request.getParameter("email_1");
                b = request.getParameter("password_1");
                //out.println(a + b);
                user = new users(a, b);
                //out.println("login request sent </br");
                if (user.Success == true) {
                    out.println("User data = " + user.Email + user.Country + user.Password + user.age);
                    out.println(user.db.err);
                    // REDIRECT TO recipeServlet-------------
                    request.setAttribute("Email", a);
                    RequestDispatcher rd = request.getRequestDispatcher("apiServlet");
                    rd.forward(request, response);

                    //-----------------------
                } else {
                    out.println("User not found");
                    out.println(user.db.err);//---------------------------ERROR REDIRECT TO index.jsp
                    request.setAttribute("error", "Incorrect Email or Password");
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                    rd.forward(request, response);

                }
            } else if (request.getParameter("signin") != null) {

                a = request.getParameter("email");
                d = request.getParameter("country");
                age = request.getParameter("age");

                b = request.getParameter("password");
                b_re = request.getParameter("re_password");
                out.println("sign up request: - " + a + "----" + d + "----" + age + "----" + b + "----" + b_re);
                if (b_re.equals(b)) {
                    user = new users(a, b, d, Integer.parseInt(age));
                    if (user.Success == true) {
                        out.println("New user added");
                        out.println(user.db.err);
                        request.setAttribute("Email", a); //---------------------------LOGIN REDIECT TO recipeServlet
                        RequestDispatcher rd = request.getRequestDispatcher("apiServlet");
                        rd.forward(request, response);
                    } else {
                        out.println("User already exists");
                        out.println(user.db.err);//---------------------------ERROR REDIRECT TO index.jsp
                        request.setAttribute("error", "User already exists");
                        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                        rd.forward(request, response);

                    }
                } else {
                    out.println("Passwords do not match");//---------------------------ERROR REDIRECT TO index.jsp
                    request.setAttribute("error", "Passwords do not match");
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                    rd.forward(request, response);

                    //out.println(user.db.err);
                }
            } else {
                out.println("no signup");
            }
        } //out.println(a + ", "+ b  + c);
        catch (Exception e) {
            out.println("ERROR:- " + e);

        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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

    private Object servletContext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
