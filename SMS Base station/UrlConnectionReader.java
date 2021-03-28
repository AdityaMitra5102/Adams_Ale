import java.net.*;  
import java.util.*;
import java.io.*;  
public class UrlConnectionReader  
{  
    void init()throws Exception
    {
        Process ec=Runtime.getRuntime().exec("python gsminit.py");
        BufferedReader br=new BufferedReader(new InputStreamReader(ec.getInputStream()));
        String l="";
        while((l=br.readLine())!=null)
        {    System.out.println(l);
            if(l.contains("OK"))
            {
                return;
            }
        }
        Thread.sleep(1500);
        ec.waitFor();
        init();
    }

    public String getNearest(String loc)throws Exception
    {
        System.out.println("Getting nearest");
        Socket socket=new Socket("52.172.129.174", 8080);
        PrintStream ps=new PrintStream(socket.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps.print(loc+"\n\r");
        ps.flush();
        String op=br.readLine();
        System.out.println(op+" received");
        socket.close();
        return op;
    }
    public void run(double d1, double d2, double d3, double d4)  throws Exception
    {  
        init();
        System.out.println("Fetching directions");
        String output  = getUrlContents("https://apis.mapmyindia.com/advancedmaps/v1/eugbfqyaze9tfzlnp4m7ld1y4qy2uvsl/route_adv/driving/"+d2+","+d1+";"+d4+","+d3+"?steps=true");  
        output+="\"name\":\"Destination\"";
        StringTokenizer st=new StringTokenizer(output,"\":");
        StringBuilder k=new StringBuilder("Your Location, ");
        StringBuilder arrow=new StringBuilder(", ");
        ArrayList<String> arr=new ArrayList<String>();
        while(st.hasMoreTokens())
        {
            String wd=st.nextToken();
            if(wd.equals("name"))
            {
                wd=st.nextToken();
                if(wd.equals(",")) continue;
                StringBuilder sb=new StringBuilder(wd);
                if(k.length()+sb.length()+2>150)
                {
                    arr.add(k.toString());
                    k=new StringBuilder("");
                }
                k.append(sb).append(arrow);
            }
        }
        arr.add(k.toString().substring(0,k.length()-2));
        System.out.println("Sending sms");
        for(int i=0;i<arr.size();i++)
        {
            System.out.println(arr.get(i)+"\n");
            Process ec=Runtime.getRuntime().exec("python smssender.py +918240530069 \""+arr.get(i)+"\"");
            Thread.sleep(7000);
            ec.waitFor();

            Thread.sleep(3000);
        }

        System.out.println("SMS Sent");
    }  

    private static String getUrlContents(String theUrl)  
    {  
        StringBuilder content = new StringBuilder();  
        // Use try and catch to avoid the exceptions  
        try  
        {  
            URL url = new URL(theUrl); // creating a url object  
            URLConnection urlConnection = url.openConnection(); // creating a urlconnection object  

            // wrapping the urlconnection in a bufferedreader  
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));  
            String line;  
            // reading from the urlconnection using the bufferedreader  
            while ((line = bufferedReader.readLine()) != null)  
            {  
                content.append(line + "\n");  
            }  
            bufferedReader.close();  
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
        return content.toString();  
    }  
}  