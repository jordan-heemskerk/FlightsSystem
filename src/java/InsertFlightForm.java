import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertFlightForm extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String sql_airlines = "SELECT airline_name, code FROM Airline";
    String sql_plane = "SELECT code FROM PlaneModel";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
        resp = "<html>\n" +
        "<head><title>Add a Flight</title></head>\n" +
        "<body>\n<h1>Add a Flight</h1><br /><form method='GET' action='/servlet/InsertFlight'>Choose an airline: <select name='airline'>" +
        "";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql_airlines);
        while (res.next()) {
            resp += "<option value='" + res.getString("code").trim() +"'>" + res.getString("airline_name") + "</option>";
        }
        st.close();
    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "</select><br /> Select plane model: <select name='plane'>";

    try { 
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql_plane);
        while (res.next()) {
            resp += "<option value='" + res.getString("code").trim() +"'>" + res.getString("code") + "</option>";
        }
        st.close();
    } catch (SQLException e) {
        resp = "Encountered error: " + e;
    }
    resp += "</select><br />";
    resp += "<input type='radio' name='origin' value='incoming' /> Incoming<br />" +
    "<input type='radio' name='origin' value='outgoing' /> Outgoing <br />" ;
    resp += "Source (ex. YYJ) <input type='text' name='source' /><br />" +
            "Destination (ex. YVR) <input type='text' name='destination' /><br />";

    resp += "Time (ex. 23:43, 03:35): <input type='text' width='5' name='time' /><br />" +
    "Number: <input type='text' name='num' /><br />" +
    "<input type='submit' value='Add Flight' /><br />";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    resp += "</form></body></html>";


    out.println(docType + resp);

  }
}
