import java.util.*;
class AdamsAleSMSClient
{
    public static void main(String args[])
    {
        try
        {
            String phn=args[0];
            String loc=args[1];
            String response=loc;
            response=response.substring(response.indexOf("getLoc"),response.indexOf("EOF")-1);
            response=response.substring(response.indexOf("?")+1);
            double latSrc=Double.parseDouble(response.substring(response.indexOf("=")+1, response.indexOf("&")));
            double lonSrc=Double.parseDouble(response.substring(response.lastIndexOf("=")+1));
            UrlConnectionReader ob=new UrlConnectionReader();
            String dest=ob.getNearest(loc.trim());
            StringTokenizer st=new StringTokenizer(dest," ,");
            double latDest=Double.parseDouble(st.nextToken());
            double lonDest=Double.parseDouble(st.nextToken());
            ob.run(latSrc, lonSrc, latDest, lonDest);
        }
        catch(Exception excep)
        {}
    }
}