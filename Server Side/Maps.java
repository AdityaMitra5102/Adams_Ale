import java.io.*;
import java.util.*;
import java.net.*;
class Maps
{
    double getDistance(Location src, Location dest)throws Exception
    {
        final String apikey="<REMOVED IN OPEN SOURCE>";
        String lnk="https://apis.mapmyindia.com/advancedmaps/v1/"+apikey+"/route_adv/walking/"+src.toStringRev()+";"+dest.toStringRev();
        URL url=new URL(lnk);
        URLConnection uc=url.openConnection();
        BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream()));
        String text="";
        String ln="";
        while((ln=br.readLine())!=null)
        {
            text+=ln;
        }
        //System.out.println(text);
        text=text.substring(0,text.indexOf("\"code\":\"Ok\""));
        text=text.substring(text.lastIndexOf("\"distance\""));
        text=text.substring(text.indexOf(":")+1,text.indexOf(","));
        System.out.println(text);
        return Double.parseDouble(text);
    }
    
    Location getNearest(Location orig, ArrayList<Location> dest)throws Exception
    {
        if(dest.size()==0)
        {
            return null;
        }
        double dist=getDistance(orig,dest.get(0));
        Location fn=dest.get(0);
        for(int i=1;i<dest.size();i++)
        {
            double x=getDistance(orig,dest.get(i));
            if(x<dist)
            {
                dist=x;
                fn=dest.get(i);
            }
        }
        return fn;
    }
}