import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Baggage extends HttpServlet {



  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String flight = request.getParameter("flight").split(":")[1];
    String pass = request.getParameter("pass");
   
String sql  = "SELECT * " +
              " FROM Baggage " +
              " WHERE passenger = '" + pass+ "' AND flightNumber = '" + flight +  "'";

 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "<html><head><title>Find Baggage for Passenger</title></head><body><table border='1'><tr><td>Flight Number</td><td>Passport Number</td><td>Weight</td></tr>";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql);

        while(res.next()) {
            resp += "<tr><td>" + res.getString("flightnumber") + "</td>" +
                     "<td>" + res.getString("passenger") + "</td>" + 
                     "<td>" + res.getString("weight") + "</td>" +
                        "</tr>";
        }

        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }
    resp+= "</table><p><a href='http://localhost:8085/servlet/BaggageForm'>Go Back</a></p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
