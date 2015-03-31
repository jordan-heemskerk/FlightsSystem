import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DeleteFlightForm extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String sql_flights = "SELECT *" +
                         "FROM" + 
                         "(" +
                         " SELECT *" +
                         "  FROM Operates JOIN Flight ON Operates.FLIGHTNUMBER=Flight.NUM" +
                         " ) T2 JOIN (" +
                         " SELECT FLIGHTNUMBER, arrivedate as flightdate, arriveid as id " +
                         " FROM Incoming JOIN Arrivals ON Arrivals.ARRIVEID=Incoming.ARRIVALID" +
                         " UNION" +
                         " SELECT FLIGHTNUMBER, departdate as flightdatei, departureid as id " +
                         " FROM Outgoing JOIN Departures ON Outgoing.DepartureId=Departures.departid" +
                         ") T1 ON T1.FLIGHTNUMBER = T2.FLIGHTNUMBER";
  
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
        resp = "<html>\n" +
        "<head><title>Delete Flight</title></head>\n" +
        "<body>\n<h1>Delete Flight</h1><form method='GET' action='/servlet/DeleteFlight'>Choose an flight: <select name='flight'>" +
        "";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql_flights);
        while (res.next()) {
            resp += "<option value='" + res.getString("id").trim() +":"+ res.getString("flightnumber") +"'>" + res.getString("airlinecode").trim() + res.getString("flightnumber").trim() + ": " + res.getString("src") + " to " + res.getString("destination") + " ON " + res.getString("flightdate") + "</option>";
        }
        st.close();
    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "</select><br /><input type='submit' value='Delete Flight' /><br />";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    resp += "</form></body></html>";


    out.println(docType + resp);

  }
}
