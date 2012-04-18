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
    private static String Server;
    public static String getServer(){ return Server;}
    /**
     * Entry point into the client.
     * @param arg 
     */
    public static void main(String arg[]) {
        if(arg.length == 1){
            Client.Server = arg[0];
            Login frame = new Login();
        } else {
            System.err.println("Error. Argument should be IP address/host name of the server");
        }
            
    }
}
