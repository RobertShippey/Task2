/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 * Class to hold the main function.
 * @author Robert and Nathan
 */
public class Client {
    private static String Server = "localhost";
    /**
     * Get the IP address or hostname of the server, as passed it via args.
     * @return a String representation of the server's location
     */
    public static String getServer(){ return Server;}
    /**
     * Entry point into the client.
     * Pass in the IP address or host name of the server as the first and only arg
     * @param arg location of the server
     */
    public static void main(String arg[]) {
       // if(arg.length == 1){
          //  Client.Server = arg[0];
            Login frame = new Login();
       // } else {
        //    System.err.println("Error. Argument should be IP address/host name of the server");
      //  }
            
    }
}
