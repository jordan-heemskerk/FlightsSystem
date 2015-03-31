import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DeleteFlight extends HttpServlet {

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


  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String flight = request.getParameter("flight").split(":")[0];
    String flight2 = request.getParameter("flight").split(":")[1];

    String sql1 = "";
    String sql2 = "";
    String sql3 = "";
    String sql4 = "";
    if (isIncoming(flight2)) {
        sql1 = "DELETE FROM Incoming WHERE ArrivalId='" + flight + "'";
         sql2 =            "DELETE FROM Arrivals WHERE ArriveId='" + flight + "'";
        sql3 = "DELETE FROM ArrivalHasStatus WHERE ArrivalId='" + flight + "'";
        sql4 = "DELETE FROM OnArrival WHERE ArrivalId='" + flight + "'";
    } else  {
        sql1 = "DELETE FROM Outgoing WHERE DepartureId='" + flight + "'";
            sql2 =  "DELETE FROM Departures WHERE DepartId='" + flight + "'";
        sql3 = "DELETE FROM DepartureHasStatus WHERE DepartureId='" + flight + "'";
        sql4 = "DELETE FROM OnDepart WHERE DepartureId='" + flight + "'";
    }
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "<html><head>Deletion</head><body>";
    try {
        Statement st = conn.createStatement();
  
        st.executeUpdate(sql1);
        st.executeUpdate(sql3);
        st.executeUpdate(sql4);
      st.executeUpdate(sql2);
        st.close();
        resp += "<p>Success, deleted flight ID=" + flight + "</p>";

    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }
    resp += "<p><a href='/servlet/DeleteFlightForm'>Go Back</a></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
