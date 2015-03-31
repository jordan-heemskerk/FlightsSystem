import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class GetPassengers extends HttpServlet {



  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String flight = request.getParameter("flight").split(":")[0];
   
    String sql = " SELECT * "+
" FROM Passenger" +
" WHERE passportNumber IN (SELECT passenger" +
                       "  FROM onArrival"+
                       "  WHERE arrivalId = '" +flight + "'" +
                       "  UNION " +
                       "  SELECT passenger "+
                        " FROM onDepart "+
                        " WHERE departureId = '" + flight + "')";
 
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "<html><head><title>Find Flights by Place</title></head><body><table border='1'><tr><td>Passport Number</td><td>Name</td><td>Date of Birth</td><td>Place of Birth</td><td>Citizenship</td></tr>";
    try {
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(sql);

        while(res.next()) {
            resp += "<tr><td>" + res.getString("passportnumber") + "</td>" +
                     "<td>" + res.getString("passengername") + "</td>" + 
                     "<td>" + res.getString("DateOfBirth") + "</td>" +
                     "<td>" + res.getString("PlaceOfBirth") + "</td>" +
                    "<td>" + res.getString("Citizenship") + "</td>" +
                        "</tr>";
        }

        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }
    resp+= "</table><p><a href='http://localhost:8085/servlet/GetPassengersForm'>Go Back</a></p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
