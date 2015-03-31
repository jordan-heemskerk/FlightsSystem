import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AssociateStatus extends HttpServlet {


    
    private String getTime(String flight) {
        String sql = "";
        boolean incoming = false;
        if (isIncoming(flight)) {
            incoming = true;
            sql = "SELECT arrivedate FROM Arrivals  WHERE arriveid='" + flight + "'";
        } else {
            sql = "SELECT departdate  FROM Departures  WHERE departid='" + flight + "'";
        }

        Connection conn = ConnectionManager.getInstance().getConnection();

        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                if (incoming) return res.getString("arrivedate");
                else return res.getString("departdate");
            }

            st.close();


        } catch (SQLException e) {
            return e.toString();
        }

        return null;
    }




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
        ", 'yyyy-mm-dd hh24:mi:ss')";
    }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String id = request.getParameter("flight").split(":")[0];
    String flight = request.getParameter("flight").split(":")[1];
    String status = request.getParameter("status");
    String time = request.getParameter("time");
    String type = request.getParameter("type");
    String type2 = "";


    String date = getTime(id).split(" ")[0];

    time = date + " " + time + ":00";


    String sql = "";
    if (type.equals("incoming")) {
        type2 = "Arrival";
        sql ="INSERT INTO ArrivalHasStatus (statusid, time, arrivalid) " +
             "VALUES ('" + status + "'," +
            "" + formatDate(time) + "," +
               "'" + id + "')";       
    } else {
        type2 = "Departure";
        sql ="INSERT INTO DepartureHasStatus (statusid, time, departureid) " +
             "VALUES ('" + status + "'," +
            "" + formatDate(time) + "," +
               "'" + id + "')";       
    }
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
        st.executeUpdate(sql);
        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "<html><head><title>Successfully added status</title></head>" +
            "<body><p>" + time + "Status updated on <b>" + flight +
            "</b>. <a href='/servlet/AssociateStatusForm'>Go Back</a>.</p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
