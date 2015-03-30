import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class SchedulePassengerForm extends HttpServlet {
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
  
    String sql_pass = "SELECT * FROM Passenger";
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
        resp = "<html>\n" +
        "<head><title>Schedule a Passenger</title></head>\n" +
        "<body>\n<h1>Schedule a Passenger</h1><form method='GET' action='/servlet/SchedulePassenger'>Choose an flight: <select name='flight'>" +
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

    resp += "</select><br /> Passenger: <select name='passenger'>";

    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql_pass);
        while (res.next()) {
            resp += "<option value='" + res.getString("passportnumber").trim() +"'>" + res.getString("passportnumber").trim() + ": " + res.getString("passengername").trim() + "</option>";
        }
        st.close();
    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }
    resp += "</select><br />";
    resp += "Checking a bag <input type='checkbox' name='bag' /> with weight(lbs) <input type='text' name='weight' />";

    resp += "<br /><input type='submit' value='Schedule Passenger' /><br />";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    resp += "</form></body></html>";


    out.println(docType + resp);

  }
}
