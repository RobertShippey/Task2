/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author Robert
 */
public class Login extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private JButton SUBMIT;
    private JPanel panel;
    private JLabel usernameLabel;
    private JTextField usernameText;
    private final Comms server;

    public Login()throws IOException {
        server = new Comms("localhost");
        
        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameText = new JTextField(15);
        usernameText.addKeyListener(this);

        SUBMIT = new JButton("SUBMIT");

        panel = new JPanel(new GridLayout(3, 1));
        panel.add(usernameLabel);
        panel.add(usernameText);
        panel.add(SUBMIT);

        add(panel, BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN FORM");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String name = usernameText.getText();
        server.logon(name);
        
        Menu page = new Menu(server);
        this.setVisible(false);
        page.setVisible(true);
        this.dispose();
        
        


    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if(ke.getKeyChar()=='\n'){
            actionPerformed(null);
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}
}
