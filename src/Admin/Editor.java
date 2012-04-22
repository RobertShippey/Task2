/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

/**
 * Class to hold the entry point for the Administrator interface. 
 * @author Robert
 */
public class Editor {

    /**
     * Host name of the server.
     */
    public static String server;

    /**
     * Entry point of the Administrator interface. Checks that one argument has been passed in.
     * @param args the host of the server
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            server = args[0];
            Admin a = new Admin();
            a.start();
        } else {
            System.err.println("Error. Usage: java -cp Task2.jar Admin.Editor <host>");
            System.exit(0);
        }
    }
}
