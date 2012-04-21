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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * A Login GUI that connects to the server.
 * @author Robert and Nathan
 */
public class Login extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private JButton submit;
    private JPanel panel;
    private JLabel usernameLabel;
    private JTextField usernameText;
    private Comms server;

    /**
     * Creates elements and adds them to this instance of Login, sets up ActionListeners to this instance.
     */
    public Login() {

        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameText = new JTextField(15);
        usernameText.addKeyListener(this);

        submit = new JButton("SUBMIT");

        panel = new JPanel(new GridLayout(3, 1));
        panel.add(usernameLabel);
        panel.add(usernameText);
        panel.add(submit);

        add(panel, BorderLayout.CENTER);
        submit.addActionListener(this);
        setTitle("LOGIN FORM");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 100);
        setVisible(true);
    }

    /**
     * Makes a new connection to the server (exits if it can't connect) and logs the name with the server's session.
     * Constructs a new Menu page and shows it, hides and disposes of this page.
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            server = new Comms();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        String name = usernameText.getText();
        server.logon(name);

        Menu page = new Menu(server);
        this.setVisible(false);
        page.setVisible(true);
        this.dispose();

    }

    /**
     * Listens for an enter key press, runs actionPerformed when it's found.
     * @param ke 
     */
    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyChar() == '\n') {
            actionPerformed(null);
        }
    }

    /**
     * Not implemented.
     * @param ke 
     */
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * Not implemented.
     * @param ke 
     */
    @Override
    public void keyPressed(KeyEvent ke) {
    }
}
