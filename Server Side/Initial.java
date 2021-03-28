class Initial
{
    public static void main(String args[])
    {
        Server ob=new Server();
        while(true)
        {
            try
            {
                System.out.println("WAITING");
                ob.network();
            }
            catch(Exception excep)
            {
                excep.printStackTrace();
            }
        }
    }
}