import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class GetDemo extends HttpServlet {
    
   protected void processRequest(HttpServletRequest request, 
					HttpServletResponse response)
    throws ServletException, IOException {
	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("uname");
        String password = request.getParameter("userpw");

        

        out.println("<HTML>");
        out.println("<HEAD><TITLE>GetDemo Output</TITLE></HEAD>");
        out.println("<BODY>");
        out.println("Hello " + username + "<br>");
        out.println("Your password was: " + password + "<br>");
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();

    }
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
        processRequest(request, response);
    }
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
   public String getServletInfo() {  return "Short description"; }
}
