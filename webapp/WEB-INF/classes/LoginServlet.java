import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isLoggedIn = isLoggedIn(request);
		if (isLoggedIn) {
			response.setStatus(302);
			response.sendRedirect("main");
		} else {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>\n" + "<head><title>" + "Login" + "</title></head>\n" + "<body>\n"
					+ "<h1 align=\"center\">" + "Login" + "</h1>\n" + "<form action=\"login\" method=\"POST\">\n"
					+ "Username: <input type=\"text\" name=\"user_id\">\n" + "<br />\n"
					+ "Password: <input type=\"password\" name=\"password\" />\n" + "<br />\n"
					+ "<input type=\"submit\" value=\"Sign in\" />\n" + "</form>\n"
					+ "</form>\n" + "</body>\n</html\n");
					
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		
		
		//Check if the user is already logged in
		if (session == null) {

			response.setContentType("text/html");

			String title = "Logged in as: ";
			String username = request.getParameter("user_id");
			String password = request.getParameter("password");

			if (isAuthenticated(username, password)) {
				session = request.getSession(true);
				session.setAttribute("USER_ID", username);
				title += session.getAttribute("USER_ID");

				response.setStatus(302);
				response.sendRedirect("main");		
			} else {
				
				response.setStatus(302);
				response.sendRedirect("login");				
			}
		} 
		else {
			response.setStatus(302);
			response.sendRedirect("main");

		}

	}

	private boolean isAuthenticated(String u, String p) {
		

		return true;

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
