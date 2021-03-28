import java.io.*;

import java.net.*;

public class HttpReq 
{
	final String URLwithKey="<REMOVED IN OPENSOURCE>";
    void sendPOST(Location loc)throws Exception
    {
        String USER_AGENT = "Mozilla/5.0";
        String POST_URL = "https://"+URLwithKey+".json";
        String POST_PARAMS = "{\"lat\":\""+loc.lat+"\",\"long\":\""+loc.lon+"\"}";
        URL obj = new URL(POST_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
        httpURLConnection.setDoOutput(true);
        OutputStream os = httpURLConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK)
        { 
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in .readLine()) != null) 
            {
                response.append(inputLine);
            }
            in .close();
            System.out.println(response.toString());
        } 
        else
        {
            System.out.println("POST request not worked");
        }
    }
    
    String sendGET() throws Exception
    {
        URL url=new URL("https://"+URLwithKey+".json?print=pretty");
        URLConnection uc=url.openConnection();
        BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String text="";
        String ln="";
        while((ln=br.readLine())!=null)
        {
            if(ln.contains("lat"))
                text+="\n"+ln;
            else if(ln.contains("long"))
                text+="\t"+ln;
        }
        System.out.println(text);
        return text;
    }
}