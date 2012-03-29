/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {

    JButton SUBMIT;
    JPanel panel;
    JLabel usernameLabel;
    JTextField usernameText;
            
           

    Client() {
        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameText = new JTextField(15);

        SUBMIT = new JButton("SUBMIT");

        panel = new JPanel(new GridLayout(3, 1));
        panel.add(usernameLabel);
        panel.add(usernameText);


        panel.add(SUBMIT);
        add(panel, BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN FORM");
    }

    public void actionPerformed(ActionEvent ae) {
        String value1 = usernameText.getText();
        
         ClientGUI page=new ClientGUI();
         page.setVisible(true);
  
         this.setVisible(false);



// code to be added for submitting string to server


    }
}

class LoginDemo {

    public static void main(String arg[]) {
        try {
            Client frame = new Client();
            frame.setSize(300, 100);
            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
