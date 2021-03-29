import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;

public class SearchServlet extends HttpServlet {
     

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
		if (!isLoggedIn(request)) { 
			response.setStatus(302);
			response.sendRedirect("login");
		}
      
    	response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");
    	File dir = new File("C:\\tomcat\\webapps\\photogallery\\images");

		PrintWriter out = response.getWriter();
		out.println("<html>\n" + "<head><title>" + "SEARCH" + "</title></head>\n" + "<body>\n"
					+ "<h1 align=\"center\">" + "SEARCH" + "</h1>\n" + "<form action=\"search\" method=\"POST\">\n"
					+ "Caption: <input type=\"text\" name=\"caption\">\n" + "<br />\n"
					//+ "location GPS topLeft: <input type=\"text\" name=\"topleft\" />\n" + "<br />\n"
					//+ "location GPS bottomright: <input type=\"text\" name=\"bottomright\" />\n" + "<br />\n"
					+ "<input type=\"submit\" value=\"Enter\" />\n" + "</form>\n"
					+ "</form>\n" + "</body>\n</html\n");
		
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();


		response.setContentType("text/html");

		String caption = request.getParameter("caption");

		session = request.getSession(true);
		session.setAttribute("caption", caption);

		response.setStatus(302);
		response.sendRedirect("gallery");		

	}
   
	private boolean isLoggedIn(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session == null || !req.isRequestedSessionIdValid()) {
			return false;
		}else{
			return true;
		}

	}
	

}