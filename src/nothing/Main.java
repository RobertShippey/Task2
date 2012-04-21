/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nothing;

/**
 *
 * @author Robert
 */
public class Main {
    public static void main(String[] args){
        System.err.println("Error!!");
        System.err.println("To run the server use:");
        System.err.println("java -cp Task2.jar Server.Server\n");
        System.err.println("To run the client use:");
        System.err.println("java -cp Task2.jar Client.Client <host of the server>\n");
        System.err.println("To run the admin application use:");
        System.err.println("java -cp Task2.jar Admin.Editor <host of the server>");
        System.exit(0);
    }
    
}
