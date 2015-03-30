import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ScheduleFlightForm extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String sql_flights = "SELECT airlinecode, flightnumber FROM Operates";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
        resp = "<html>\n" +
        "<head><title>Schedule a Flight</title></head>\n" +
        "<body>\n<h1>Schedule a Flight</h1><form method='GET' action='/servlet/ScheduleFlight'>Choose an flight: <select name='flight'>" +
        "";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql_flights);
        while (res.next()) {
            resp += "<option value='" + res.getString("flightnumber").trim() +"'>" + res.getString("airlinecode").trim() + res.getString("flightnumber").trim() + "</option>";
        }
        st.close();
    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "</select><br />";
    resp += "ID: <input type='text' name='id' /><br />";
    resp += "Gate: <input type='text' name='gate' /><br />";
    resp += "Date (ex. DD/MM/YYYY): <input type='text' name='date' /><br />";

    resp += "<br /><input type='submit' value='Schedule Flight' /><br />";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    resp += "</form></body></html>";


    out.println(docType + resp);

  }
}
