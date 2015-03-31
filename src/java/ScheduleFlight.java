import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ScheduleFlight extends HttpServlet {


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


    private String getTime(String flight) {
        String sql = "";
        boolean incoming = false;
        if (isIncoming(flight)) {
            incoming = true;
            sql = "SELECT plannedArrival FROM IncomingFlight WHERE num='" + flight + "'";  
        } else {
            sql = "SELECT plannedDeparture FROM OutgoingFlight num='" + flight + "'";
        }
    
        Connection conn = ConnectionManager.getInstance().getConnection();

        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                if (incoming) return res.getString("plannedArrival");
                else return res.getString("plannedDeparture");
            }

            st.close();


        } catch (SQLException e) {
            return false;
        }


    }

    private String formatDate(String date) {
        return "TO_DATE('" + date.trim() + "'" +
        ", 'dd/mm/yyyy hh24:mi:ss')";
    }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String flightnum = request.getParameter("flight");
    String id = request.getParameter("id");
    String gate = request.getParameter("gate");
    String date = request.getParameter("date");
   
    String association = "";
    String info = "";i

    String time = getTime(flightnum);
    
    date = date + time.split(" ")[1];
    if (isIncoming(flightnum)) {
        association = "INSERT INTO Incoming (flightnumber, arrivalid) " +
                      "VALUES ('" + flightnum + "'," +
                      "'" + id + "')";
        info = "INSERT INTO Arrivals (gate, arrivedate, arriveid) " +
               "VALUES ('" + gate + "'," +
               "" + formatDate(date) + "," +
               "" + id + ")";
    } else {
        association = "INSERT INTO Outgoing (flightnumber, departid) " +
                      "VALUES ('" + flightnum + "'," +
                      "'" + id + "')";
        info = "INSERT INTO Departures (gate, arrivedate, departid) " +
               "VALUES ('" + gate + "'," +
               "" + formatDate(date) + "," +
               "" + id + ")";
    }
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
        st.executeUpdate(info);
        st.executeUpdate(association);

        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "<html><head><title>Successfully inserted</title></head>" +
            "<body><p>Successfully scheduled new flight on <b>" + date +
            "</b>. <a href='/servlet/ScheduleFlightForm'>Go Back</a>.</p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
