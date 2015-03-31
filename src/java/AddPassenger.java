import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Arrays;

public class AddPassenger extends HttpServlet {

  private String formatTime(String time) {
        return "TO_DATE('" + time.trim() + "'" +
        ", 'yyyy:mm:dd')";
  }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {


    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String passportnumber = request.getParameter("passportnumber");
    String passengername = request.getParameter("passengername");
    String dateofbirth = request.getParameter("dateofbirth");
    String placeofbirth = request.getParameter("placeofbirth");
    String citizenship = request.getParameter("citizenship");
    String [] typeArray = request.getParameterValues("type");
    String seniorcost = "400";
    String infantcost = "0";
    String specialcost = "600";
    String economycost = "500";
    String firstclasscost = "850";

    String passenger = "INSERT INTO Passenger(passportnumber, passengername, dateofbirth, placeofbirth, citizenship) " +
                    "VALUES ( '" + passportnumber + "','" + passengername + "'," + formatTime(dateofbirth) + ",'" + placeofbirth + "','" + citizenship + "' )";
   
    Connection conn = ConnectionManager.getInstance().getConnection();

    String resp = "";
    try {
        Statement st = conn.createStatement();
	st.executeUpdate(passenger);
	if(typeArray.length > 0) {
   		String type = Arrays.toString(typeArray); 
		if (type.indexOf("senior") > 0) {
		    String age = request.getParameter("seniorage");	
		    String senior = "INSERT INTO Senior(passportnumber, cost, age) " +
			    "VALUES ( '" + passportnumber + "','" + seniorcost + "','" + age + "' )";
		    st.executeUpdate(senior);
		} 
		if (type.indexOf("infant") > 0) {
		    String age = request.getParameter("infantage");	
		    String infant = "INSERT INTO Infant(passportnumber, cost, age) " +
			    "VALUES ( '" + passportnumber + "','" + infantcost + "','" + age + "' )";
		    st.executeUpdate(infant);
		} 
		if (type.indexOf("specialneeds") > 0) {
		    String equipment = request.getParameter("equipment");	
		    String specialneeds = "INSERT INTO SpecialNeeds(passportnumber, cost, equipment) " +
			    "VALUES ( '" + passportnumber + "','" + specialcost + "','" + equipment + "' )";
		    st.executeUpdate(specialneeds);
		} 
		if (type.indexOf("economy") > 0) {
		    String economy = "INSERT INTO economy(passportnumber, cost) " +
			    "VALUES ( '" + passportnumber + "','" + economycost + "' )";
		    st.executeUpdate(economy);
		} 
		if (type.indexOf("firstclass") > 0) {
		    String meal = request.getParameter("meal");	
		    String firstclass = "INSERT INTO FirstClass(passportnumber, cost, meal) " +
			    "VALUES ( '" + passportnumber + "','" + firstclasscost + "','" + meal + "' )";
		    st.executeUpdate(firstclass);
		} 
	}
        st.close();


    } catch (SQLException e) {
         resp = "Encountered error: " + e; 
    }

    resp += "<html><head><title>Successfully inserted</title></head>" +
            "<body><p>Successfully inserted new passenger <b>" +
            "</b>. <a href='/insert/addPassenger.html'>Go Back</a>.</p></body></html>";

    String docType =
      "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 " +
      "Transitional//EN\">\n";
    out.println(docType + resp);

  }
}
