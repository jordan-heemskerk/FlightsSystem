import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AssociateStatusForm extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String type = request.getParameter("type");
    String type2 = "";
    if (type.equals("incoming")) {
        type2="Arrival";
    } else {
        type2="Departure";
    }

    String sql_incoming = "SELECT *" +
                         "FROM" + 
                         "(" +
                         " SELECT *" +
                         "  FROM Operates JOIN Flight ON Operates.FLIGHTNUMBER=Flight.NUM" +
                         " ) T2 JOIN (" +
                         " SELECT FLIGHTNUMBER, arrivedate as flightdate, arriveid as id " +
                         " FROM Incoming JOIN Arrivals ON Arrivals.ARRIVEID=Incoming.ARRIVALID" +
                         ") T1 ON T1.FLIGHTNUMBER = T2.FLIGHTNUMBER";

    
    String sql_outgoing = "SELECT *" +
                         "FROM" + 
                         "(" +
                         " SELECT *" +
                         "  FROM Operates JOIN Flight ON Operates.FLIGHTNUMBER=Flight.NUM" +
                         " ) T2 JOIN (" +
                         " SELECT FLIGHTNUMBER, departdate as flightdate, departureid as id " +
                         " FROM Outgoing JOIN Departures ON Outgoing.DepartureId=Departures.departid" +
                         ") T1 ON T1.FLIGHTNUMBER = T2.FLIGHTNUMBER";
  
    String sql_status = "SELECT * FROM " + type2 + "Status";
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
        resp = "<html>\n" +
        "<head><title>Update Status</title></head>\n" +
        "<body>\n<h1>Update Status</h1><form method='GET' action='/servlet/AssociateStatus'>Choose aa flight: <select name='flight'>" +
        "";
    try {
        Statement st = conn.createStatement();
        ResultSet res;

        if (type.equals("incoming")) {
            res = st.executeQuery(sql_incoming);
        } else  {
            res = st.executeQuery(sql_outgoing);
        }

        while (res.next()) {
            resp += "<option value='" + res.getString("id").trim() +":"+ res.getString("flightnumber") +"'>" + res.getString("airlinecode").trim() + res.getString("flightnumber").trim() + ": " + res.getString("src") + " to " + res.getString("destination") + " ON " + res.getString("flightdate") + "</option>";
        }
        st.close();
    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "</select><br /> Status: <select name='status'>";

    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql_status);
        while (res.next()) {
            resp += "<option value='" + res.getString("id").trim() +"'>" + res.getString("info").trim() + "</option>";
        }
        st.close();
    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }
    resp += "</select><br />";
    resp += "Time (ex.23:43, 03:45)  <input type='text' name='time' /><input type='hidden' name='type' value='" + type + "'/>'";

    resp += "<br /><input type='submit' value='Update Status' /><br />";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    resp += "</form></body></html>";


    out.println(docType + resp);

  }
}
