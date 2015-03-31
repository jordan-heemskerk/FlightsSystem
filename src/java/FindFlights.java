import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FindFlights extends HttpServlet {



  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String airline = request.getParameter("airline");
   
    String sql = " SELECT * FROM Flight " +
                 " WHERE num IN (SELECT flightNumber " +
                 " FROM Operates " +
                 " WHERE airlineCode IN (SELECT code " +
                 " FROM Airline " +
                 " WHERE airline_name='" + airline + "'))";

 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "<html><head><title>Find Flights by Airline</title></head><body><table border='1'><tr><td>Flight Number</td><td>From</td><td>To</td></tr>";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql);

        while(res.next()) {
            resp += "<tr><td>" + res.getString("num") + "</td>" +
                     "<td>" + res.getString("src") + "</td>" + 
                     "<td>" + res.getString("destination") + "</td></tr>";
        }

        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }
    resp+= "</table><p><a href='http://localhost:8085/servlet/FindFlightsForm'>Go Back</a></p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
