
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(urlPatterns = ("/userServlet"))
public class userServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String a = request.getParameter("email");
            String b = request.getParameter("password");
            
            //user = new users(a,b);
            out.println(a + ", "+ b );
            
        }
        catch(Exception e){
            out.println("ERROR");
        }
    }
}
