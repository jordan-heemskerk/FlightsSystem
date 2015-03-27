import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertPlaneModel extends HttpServlet {
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String code = request.getParameter("code");
    String cap = request.getParameter("capacity");

    String insert = "INSERT INTO PlaneModel(code, capacity) " +
                    "VALUES ( '" + code + "'," + cap + " )";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
        st.executeUpdate(insert);
        st.close();

        resp = "<html>\n" +
        "<head><title>Insert PlaneModel</title></head>\n" +
        "<body>\n" +
        "<p>Successfully added plane model: <b>" + code + "</b>. <a href='/insert/planeModel.html'>Go back</a></p>\n" +
        "</body></html>";

    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
