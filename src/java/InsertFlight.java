import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertFlight extends HttpServlet {

    private String formatTime(String time) {
        return "TO_DATE('" + time.trim() + ":00'" +
        ", 'hh24:mi:ss')";
    }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String airline = request.getParameter("airline");
    String plane = request.getParameter("plane");
    String source = request.getParameter("source");
    String destination = request.getParameter("destination");
    String origin = request.getParameter("origin");
    String time = request.getParameter("time");
    String num = request.getParameter("num");

    String flights = "INSERT INTO Flight(num, src, destination) " +
                    "VALUES ( '" + num + "'," +
                    "'" + source + "'," +
                    "'" + destination + "')";
    String incoming = "INSERT INTO IncomingFlight (num, plannedarrival) " +
                      "VALUES ( '" + num + "'," +
                      "" + formatTime(time) + ")";

    String outgoing = "INSERT INTO OutgoingFlight (num, planneddeparture) " +
                      "VALUES ( '" + num + "'," +
                      "" + formatTime(time) + ")";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
        st.executeUpdate(flights);
        if (origin.equals("incoming")) {
            st.executeUpdate(incoming);
        } 

        if (origin.equals("outgoing")) {
            st.executeUpdate(outgoing);
        }

        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "<html><head><title>Successfully inserted</title></head>" +
            "<body><p>Successfully inserted new flight <b>" + airline + num +
            "</b>. <a href='/servlet/InsertFlightForm'>Go Back</a>.</p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
