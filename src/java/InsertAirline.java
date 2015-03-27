import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertAirline extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String name = request.getParameter("name");
    String code = request.getParameter("code");
    String web = request.getParameter("web");

    String insert = "INSERT INTO Airline(airline_name, code, website) " +
                    "VALUES ( '" + name + "','" + code + "','" + web + "' )";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
        st.executeUpdate(insert);
        st.close();

        resp = "<html>\n" +
        "<head><title>Insert Airline</title></head>\n" +
        "<body>\n" +
        "<p>Successfully added airline: <b>" + name + "</b>. <a href='/insert/airline.html'>Go back</a></p>\n" +
        "</body></html>";

    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
