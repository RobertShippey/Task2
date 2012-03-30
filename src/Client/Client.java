/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import javax.swing.JOptionPane;

public class Client {

    public static void main(String arg[]) {
        try {
            Login frame = new Login();
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
    }
}
