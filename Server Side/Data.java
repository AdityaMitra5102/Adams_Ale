import java.util.*;
class Data
{
    HttpReq hr=new HttpReq();
    ArrayList<Location> parseDb()throws Exception
    {
        ArrayList<Location> loc=new ArrayList<Location>();
        String dat=hr.sendGET();
        StringTokenizer st=new StringTokenizer(dat," \n\t\":,latong");
        int t=st.countTokens();
        System.out.println(t);
        for(int i=0;i<t;i+=2)
        {
            double a=Double.parseDouble(st.nextToken());
            double b=Double.parseDouble(st.nextToken());
            loc.add(new Location(a,b));
        }
        //System.out.println(loc);
        return loc;
    }
}