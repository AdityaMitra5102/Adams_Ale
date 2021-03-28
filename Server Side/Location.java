class Location
{
    double lat;
    double lon;
    
    Location(double a,double b)
    {
        lat=a;
        lon=b;
    }
    
    public String toString()
    {
        return lat+","+lon;
    }
    public String toStringRev()
    {
        return lon+","+lat;
    }
}