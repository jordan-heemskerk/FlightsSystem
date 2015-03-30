import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AddStatus extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String type = request.getParameter("type");
    String id = request.getParameter("id");
    String info = request.getParameter("info");

    String arrival = "INSERT INTO ArrivalStatus(id, info) " +
                    "VALUES ( '" + id + "','" + info + "' )";
   
    String departure = "INSERT INTO DepartureStatus(id, info) " +
                    "VALUES ( '" + id + "','" + info + "' )";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
        if (type.equals("arrival")) {
            st.executeUpdate(arrival);
        } 

        if (type.equals("departure")) {
            st.executeUpdate(departure);
        }

        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "<html><head><title>Successfully inserted</title></head>" +
            "<body><p>Successfully inserted new status <b>" +
            "</b>. <a href='/insert/addStatus.html'>Go Back</a>.</p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
