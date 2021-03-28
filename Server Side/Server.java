/**
 * Request from Tester side: HTTP GET request <IP>:8080/appendDB?lat=<LATITUDE>&long=<LONGITUDE>&EOF
 * Request from User side: TCP Socket Port=8080 getLoc?lat=<LATITUDE>&long=<LONGITUDE>&EOF
 */
import java.io.*;
import java.util.*;
import java.net.*;
class Server
{

    String parseRequest(String response)throws Exception
    {
        if(response.contains("appendDB"))
        {
            HttpReq firebase=new HttpReq();
            response=response.substring(response.indexOf("appendDB"),response.indexOf("EOF")-1);
            response=response.substring(response.indexOf("?")+1);
            double lat=Double.parseDouble(response.substring(response.indexOf("=")+1, response.indexOf("&")));
            double lon=Double.parseDouble(response.substring(response.lastIndexOf("=")+1));
            System.out.println(lat+","+lon);
            firebase.sendPOST(new Location(lat,lon));
            return "";
        }
        else if(response.contains("getLoc"))
        {
            response=response.substring(response.indexOf("getLoc"),response.indexOf("EOF")-1);
            response=response.substring(response.indexOf("?")+1);
            double lat=Double.parseDouble(response.substring(response.indexOf("=")+1, response.indexOf("&")));
            double lon=Double.parseDouble(response.substring(response.lastIndexOf("=")+1));
            Location origin=new Location(lat,lon);
            Location dest=new Maps().getNearest(origin,new Data().parseDb());
            String res=dest.toString();
            return res;
        }
        return response;
    }

    void network()throws Exception
    {
        ServerSocket ss=new ServerSocket(8080);//Port 8080
        Socket s=ss.accept();
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream ps=new PrintStream(s.getOutputStream());
        String req="";
        int ln='\u0000';
        req=br.readLine();
        System.out.println("Outside loop");
        System.out.println(req);
        String resp=parseRequest(req);
        ps.print(resp);
        ps.flush();
        ps.close();
        br.close();
        s.close();
        ss.close();
    }
}