import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.oreilly.servlet.MultipartRequest;

public class UploadServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) 

    throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        MultipartRequest m = new MultipartRequest(request,System.getProperty("catalina.base") + "/webapps/photogallery/images");
        //response.setStatus(302);
        //response.sendRedirect("main");
        out.println("memes");
        }

    public void doGet(HttpServletRequest request, HttpServletResponse response)

    throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = isLoggedIn(request);
        
        if (!isLoggedIn) {
            response.setStatus(302);
            response.sendRedirect("login");
        }

        else {
            PrintWriter writer = response.getWriter();
            writer.append("<!DOCTYPE html>\r\n")
                .append("<html>\r\n")
                .append("    <head>\r\n")
                .append("        <title>File Upload Form</title>\r\n")
                .append("    </head>\r\n")
                .append("    <body>\r\n");
            //writer.append(addHeader(session));
            writer.append("<h1>Upload file</h1>\r\n");
            writer.append("<form method=\"POST\" action=\"upload\" ")
                .append("enctype=\"multipart/form-data\">\r\n");
            writer.append("<input type=\"file\" name=\"fileName\"/><br/><br/>\r\n");
            //writer.append("Caption: <input type=\"text\" name=\"caption\"<br/><br/>\r\n");
            //writer.append("<br />\n");
            //writer.append("Date: <input type=\"date\" name=\"date\"<br/><br/>\r\n");
            writer.append("<br />\n");
            writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n");
            writer.append("</form>\r\n");
            writer.append("</body>\r\n").append("</html>\r\n");
        }
    }
        
    private boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session == null || !req.isRequestedSessionIdValid()) {
            return false;
        }
        else {
            return true;
        }

    }
    
    private String addLandingPage(HttpSession session) {

        StringBuilder sb = new StringBuilder();
        sb.append("<div id = \'header\' style=\"display:flex; justify-content:center;\">");

        sb.append("<form action=\'upload\' method = \'GET\'>");
        sb.append("<input type=\'submit\' value=\'UPLOAD\'/>");
        sb.append("</form>");

        sb.append("<form action=\'gallery\' method = \'GET\'>");
        sb.append("<input type=\'submit\' value=\'GALLERY\'/>");
        sb.append("</form>");

        sb.append("<form action=\'search\' method = \'GET\'>");
        sb.append("<input type=\'submit\' value=\'SEARCH\'/>");
        sb.append("</form>");

        sb.append("<form action=\'logout\' method = \'GET\'>");
        sb.append("<input type=\'submit\' value=\'LOGOUT\'/>");
        sb.append("</form>");

        sb.append("<div>");
        sb.append("Logged in as : " + session.getAttribute("USER_ID"));
        sb.append("</div>");

        sb.append("</div>");

        return sb.toString();
    }

}