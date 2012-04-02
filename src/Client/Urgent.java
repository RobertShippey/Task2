/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 *
 * @author Robert
 */
public class Urgent extends JFrame implements Runnable, ActionListener {
    private static final long serialVersionUID = 1L;
    private BufferedReader MsgServer;
    private boolean quit;
    private JTextArea msgs;

    public Urgent(){
        try{
        MsgServer = new BufferedReader(new InputStreamReader(new Socket("localhost", 2001).getInputStream()));
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        
        quit = false;
        JLabel lbl1 = new JLabel("Urgent Messages:");
        msgs = new JTextArea();
        msgs.setEditable(false);
        msgs.setText("");
        JButton hideBtn = new JButton("Hide");
        JPanel panel = new JPanel(new GridLayout(3, 1));
       
        panel.add(lbl1);
        panel.add(msgs);
        panel.add(hideBtn);

        add(panel, BorderLayout.CENTER);
        hideBtn.addActionListener(this);
        setTitle("Urgent Messages!");

        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 100);
        this.setVisible(true);
    }
    
    @Override
    public void run() {
        while(!quit){
            String m = msgs.getText();
            String n = "";
            try {
            n = MsgServer.readLine();
            } catch (IOException e){
                continue;
            }
            msgs.setText(n + "\n" + m);
        }
       //stuff
    }
    
    public void start(){
        Thread t = new Thread(this);
        t.start();
    }
    
    public void stop(){
        this.quit = true;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.setVisible(false);
    }
}
