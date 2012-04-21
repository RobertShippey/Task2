/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

/**
 *
 * @author Robert
 */
public class Editor {
    public static String server = "localhost";
    
    public static void main(String[] args){
        if(args.length == 1){
            server = args[0];
            Admin a = new Admin();
            a.setVisible(true);
            a.start();
        } else {
            System.err.println("Error. Usage: java -cp Task2.jar Admin.Editor <host>");
            System.exit(0);
        }
    }
    
}
