import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FindFlightsForm extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String sql_flights = "SELECT * FROM Airline";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
        resp = "<html>\n" +
        "<head><title>Find a Flight</title></head>\n" +
        "<body>\n<h1>Find a Flight</h1><form method='GET' action='/servlet/FindFlights'>Choose an airline: <select name='airline'>" +
        "";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql_flights);
        while (res.next()) {
            resp += "<option value='" + res.getString("airline_name").trim() +"'>" + res.getString("airline_name").trim() + "</option>";
        }
        st.close();
    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "</select><br />";
    resp += "<br /><input type='submit' value='Find Flights' /><br />";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    resp += "</form></body></html>";


    out.println(docType + resp);

  }
}
