import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class SchedulePassenger extends HttpServlet {


    private boolean isIncoming(String flightnum) {
        boolean toReturn = false;
        Connection conn = ConnectionManager.getInstance().getConnection();

        String sql = "SELECT * FROM IncomingFlight WHERE num=" + flightnum;

        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                toReturn = true;
            }

            st.close();


        } catch (SQLException e) {
            return false;
        }

        return toReturn;
    }

    private String formatDate(String date) {
        return "TO_DATE('" + date.trim() + "'" +
        ", 'dd/mm/yyyy')";
    }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String id = request.getParameter("flight").split(":")[0];
    String flight = request.getParameter("flight").split(":")[1];
    String passenger = request.getParameter("passenger");
    String bag = request.getParameter("bag");
    String weight = request.getParameter("weight");
    
    String on = "";
    if (!isIncoming(flight)) {
        on = "INSERT INTO OnDepart (departureid, passenger) "+
               "VALUES ('" + id + "'," +
               "'" + passenger + "')";
    } else {
        on = "INSERT INTO OnArrival (arrivalid, passenger) "+
               "VALUES ('" + id + "'," +
               "'" + passenger + "')";
    }
 
    String baggage = "INSERT INTO Baggage (flightnumber, passenger, weight)" +
    "VALUES ('" + flight + "'," +
    "'" + passenger + "'," +
    "'" + weight + "')";

    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
        st.executeUpdate(on);
        
        if (bag.equals("on")) st.executeUpdate(baggage);

        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "<html><head><title>Successfully inserted</title></head>" +
            "<body><p>Successfully scheduled new passenger on <b>" + flight +
            "</b>. <a href='/servlet/SchedulePassengerForm'>Go Back</a>.</p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
