/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author Robert
 */
class Admin extends JFrame implements Runnable{
    private static final long serialVersionUID = 1L;
    
    public Admin(){
        //set up the gui stuff here
    }

    @Override
    public void run() {
        boolean serverRunning = true;
        while(serverRunning){
            try{
            Socket s = new Socket(Editor.server, 2000);
            } catch (IOException ioe){
                serverRunning = false;
                continue;
            }
        }
    }
    
    public void start(){
        Thread t = new Thread(this);
        t.start();
    }
    
}
