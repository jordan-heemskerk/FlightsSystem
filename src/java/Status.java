import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Status extends HttpServlet {



  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String time = request.getParameter("time");
    String time_func = "TO_DATE('" + time + "', 'yyyy/mm/dd hh24:mi:ss')";
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String sql = " SELECT Departures.gate, Departures.departDate as TheDate, DepartureInfo.info" +
" FROM Departures" +
" JOIN (SELECT DepartureHasStatus.departureId as departureId, DepartureStatus.info as info" +
     " FROM DepartureStatus " +
     " JOIN DepartureHasStatus " +
     " ON DepartureStatus.id = DepartureHasStatus.statusId) DepartureInfo" +
" ON Departures.departId = DepartureInfo.departureId " +
" WHERE ABS(departDate - " + time_func + ") < 0.15" +
" UNION " +
" SELECT Arrivals.gate, Arrivals.arriveDate as TheDate, ArrivalInfo.info " +
" FROM Arrivals " +
" JOIN (SELECT ArrivalHasStatus.arrivalId as arrivalId, ArrivalStatus.info as info " +
    "  FROM ArrivalStatus " +
     " JOIN ArrivalHasStatus " +
    "  ON ArrivalStatus.id = ArrivalHasStatus.statusId) ArrivalInfo " +
 " ON Arrivals.arriveId = ArrivalInfo.arrivalId " +
 " WHERE ABS(Arrivals.arriveDate - " + time_func + ") < 0.15";

    String resp = "<html><head><title>Find Flights Status</title></head><body><table border='1'><tr><td>Gate</td><td>Date</td><td>Status</td></tr>";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql);

        while(res.next()) {
            resp += "<tr><td>" + res.getString("Gate") + "</td>" +
                     "<td>" + res.getString("TheDate") + "</td>" + 
                     "<td>" + res.getString("Info") + "</td></tr>";
        }

        st.close();


    } catch (SQLException e) {
        resp = "Encountered error: " + e; 
    }
    resp+= "</table><p><a href='http://localhost:8085/query/Status.html'>Go Back</a></p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
