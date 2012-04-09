/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 *Thread that checks for a quit or force quit command from the keyboard on STDIN.
 * @author Robert and Nathan
 */
public class CmdThread extends Thread {
    private Server _s;
    private boolean q = false;
    private UrgentMsgThread _t;
    
    /**
     * Constructs and instance.
     * @param s instance of the server
     * @param t instance of the Urgent Message Thread
     */
    public CmdThread(Server s, UrgentMsgThread t){
        this.setName("CMDThread");
        _t = t;
        _s = s;
    }
    
    /**
     * Reads characters from STDIN and sets quit/force quit flags accordingly.
     */
    @Override
    synchronized public void run() {
        InputStreamReader in = new InputStreamReader(System.in);
        int c;
        while(!q){
            try{
            c = in.read();}
            catch (IOException e){ continue;}
            if(c == (int)'q'){
                q = true;
                _s.setQuit("q");
            }
            if(c == (int)'f'){
                q = true;
                _s.setQuit("f");
            }
        } 
    }
    
}
