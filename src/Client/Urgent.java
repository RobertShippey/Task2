/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *The threaded window that listens for urgent messages from the server and updates the text box accordingly.
 * @author Robert
 */
public class Urgent extends JFrame implements Runnable, ActionListener {
    private static final long serialVersionUID = 1L;
    private BufferedReader MsgServer;
    private boolean quit;
    private JTextArea msgs;

    /**
     * Constructs the window and sets up the server connections.
     */
    public Urgent(){
        try{
            Socket s = new Socket("localhost", 2001);
            s.setSoTimeout(5);
        MsgServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        
        quit = false;
        JLabel lbl1 = new JLabel("Urgent Messages:");
        msgs = new JTextArea();
        msgs.setEditable(false);
        msgs.setText("");
        msgs.setPreferredSize(new Dimension(200,200));
        JButton hideBtn = new JButton("Hide");
        Container panel = this.getContentPane();
        
        panel.add(lbl1,BorderLayout.PAGE_START);
        panel.add(msgs, BorderLayout.CENTER);
        panel.add(hideBtn, BorderLayout.PAGE_END);

        hideBtn.addActionListener(this);
        setTitle("Urgent Messages!");

        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(200, 250);
        this.pack();
    }
    
    /**
     * Listens for messages and updates the text box.
     */
    @Override
    public void run() {
        this.setVisible(true);
        while(!quit){
            String m = msgs.getText();
            String n = null;
            try {
            n = MsgServer.readLine();
            } catch (IOException e){
                continue;
            }
            if(n!=null){
            msgs.setText(n + "\n" + m);
            this.setVisible(true);
            }
        }
        this.dispose();
       //stuff
    }
    
    /**
     * Starts a new thread from this instance of runnable.
     */
    public void start(){
        Thread t = new Thread(this);
        t.start();
    }
    
    /**
     * Sets the quit flag to tell the thread to finish.
     */
    public void stop(){
        this.quit = true;
    }

    /**
     * Listens for the 'Hide' button to be presses and sets the window to invisible when pressed.
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        this.setVisible(false);
    }
}
