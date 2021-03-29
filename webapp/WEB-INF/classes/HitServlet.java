import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;

public class HitServlet extends HttpServlet {
  private int mCount;
 
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
      

      HttpSession session = request.getSession(true);
      if (request.getParameterMap().containsKey("userid")) {
           
                 session.setAttribute("userid", request.getParameter("userid"));
                 session.setAttribute("count", 0);
           
      }
      
		String userid  =  (String)session.getAttribute("userid"); 
		String caption =  (String)session.getAttribute("caption");
		String location =  (String)session.getAttribute("location");
		String TimeX =  (String)session.getAttribute("TimeX");
      // Set response content type.
      response.setContentType("text/html");
      response.setCharacterEncoding("UTF-8");
 
      PrintWriter out = response.getWriter();

    if ( userid == null) {  
		out.println("<html>\n" +
            "<body>\n" + 
            "<form action=\"/midp/hits\" method=\"GET\">\n" +
            "First Name: <input type=\"text\" name=\"userid\">\n"   +
            "<br />\n" +
            "Last Name: <input type=\"text\" name=\"password\" />\n"   +
            "<input type=\"submit\" value=\"Submit\" />\n"
            + 
            "</form>\n</body>\n</html\n");
    } 
	else {
        Integer count = (Integer)session.getAttribute("count");
        count = count + 1;
        session.setAttribute("count", count);         
        out.println("<html>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");
        out.println("<p>You are logged in as: " + userid.toUpperCase() + "</p>");
        out.println("<div>");
        out.println("<form action='/midp/hits' method='GET'>");
		out.println("<div class='image'>");
		String temp = (String)session.getAttribute("alt");
        if (count == 1 ) {
            out.println("<img id='changeImg' src='./images/a.jpg' name='IMAGE' value='IMAGE' alt='MEMEs' height='42' width='42'>");
        }
		else {
            out.println("<img id='changeImg' src='./images/b.jpg' alt='notMEMES' height='42' width='42'>");
        }
		
        out.println("<div class='changeImg'>");
		out.println(request.getParameter("temp"));
        out.println("<br>");
		out.println(request.getParameter("potato"));
        out.println("<br>");	
        out.println("<div class='button'>");          
        out.println("<button class='button' value='IMAGE' id='prev'>Prev</button>");
		out.println("<button class='button' name='potato' value='123' id='snap'>Snap</button>");
        out.println("<button class='button' id='next'>Next</button>");
		
		out.println("<br />\n" +
            "<form action=\"/midp/hits\" method=\"GET\">\n" +
            
			"Caption: <input type=\"text\" name=\"caption\">\n"   +
            "<br />\n" +
			
			"Location: <input type=\"text\" name=\"location\">\n"   +
            "<br />\n" +
			
            "Time: <input type=\"text\" name=\"TimeX\" />\n"   +
			"<br />\n" +
			"<input type=\"submit\" value=\"search\" />\n"
            + 
            "</form>\n</body>\n</html\n");
		
        out.println("</div></div><br>");
        out.println("</form>");
        out.println("</body></html>");
        out.close();
    }
 }
}