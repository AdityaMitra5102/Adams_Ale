//Dependency: Apache Tomcat9
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class MyServlet extends HttpServlet
{
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
                try{
                String lat=request.getParameter("lat");
                String lon=request.getParameter("long");
                String rqt="getLoc?lat="+lat+"&long="+lon+"&EOF";
                String resp=new Server().parseRequest(rqt);
                resp="https://google.com/maps?q="+resp;
                resp="<HTML><script>window.location.href=\""+resp+"\";</script></HTML>";
                response.setContentType("text/html");
                PrintWriter out=response.getWriter();
                out.println(resp);
                }catch(Exception excep){}
        }
}