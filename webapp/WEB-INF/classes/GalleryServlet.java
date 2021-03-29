import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class GalleryServlet extends HttpServlet {
      private int mCount;
      private int fileNum = 0;
      public File dir = new File("C:\\tomcat\\webapps\\photogallery\\images");


      public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(302);
			response.sendRedirect("login");	
      	}

            String cap = (String) session.getAttribute("caption");
      
            if (!isLoggedIn(request)) { 
                  response.setStatus(302);
                  response.sendRedirect("login");
            }

            
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            File dir = new File("C:\\tomcat\\webapps\\photogallery\\images");
            String[] chld = dir.list();
            ArrayList<String> filtered = new ArrayList<String>();

            if(cap != null) {
                  for (String i : chld) {
                        String name = i.substring(0,i.lastIndexOf('.'));
                        if (name.contains(cap)) {
                              filtered.add(i);
                        }
                  }
                  chld = filtered.toArray(new String[filtered.size()]);
            }

            // instead of hardcoding to the first image in the list as done below
            //save search criteria as session variable and use it to filter the files in the above array
            //check if prev or next button pressed and then rotate through the array accordingly

            //File[] files = dir.listFiles();
            //int numFiles = files.length;
            int numFiles = chld.length;

            if (fileNum >= numFiles){
                  fileNum = 0;
            }


            String img_src = chld[fileNum];
            String timeData = img_src.split("_")[0];
            String LOC = img_src.split("_")[1]; 
            String captionShow = chld[fileNum].split("_")[3];   


            
            String alt_text = "POTATOS";	 
            PrintWriter out = response.getWriter();
                  out.println("<html>");
                  out.println("<meta charset='UTF-8'>");
                  out.println("<body>");
                  out.println("<div>");
                  out.println("<form action='/photogallery/gallery' method='GET'>");
                  out.println("<div>");     
                  out.println("<img id = \"img_src\" src=./images/"+ img_src + " alt=" + alt_text + " width=200 height=150>");

                  //2020-03-8_Vancouver_caption_f
                  out.println("<div>");
                  //out.println("<br>");
                  out.println("Caption: " + captionShow);
                  out.println("</div>");
                  //out.println("</br>");

                  out.println("<div>");
                  //out.println("<br>");
                  out.println("Date: " + timeData);
                  out.println("</div>");
                  //out.println("</br>");
                  out.println("<div>");
                  //out.println("<br>");
                  out.println("Location: " + LOC);
                  out.println("</div>");
                 // out.println("</br>");

                  out.println("<div>"); 
                  out.println("<br>");
                  out.println("<div class='button'>");          
                  out.println("<button class='button' id='prev'>Prev</button>");
                  out.println("<button class='button' id='next'>Next</button>");
                  out.println("</div></div><br>");
                  out.println("</form>");
                  out.println("<div>");
                  out.println("<form action='main' method='GET'>");
                  out.println("<button class='button' id='main'>Main</button>");
                  out.println("</div><br>");
                  out.println("</form>");
                  out.println("</body></html>");
                  out.close();



            
            fileNum = fileNum + 1;

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