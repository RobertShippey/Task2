/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Robert
 */
public class CmdThread extends Thread {
    private Server _s;
    private boolean q = false;
    private UrgentMsgThread _t;
    
    public CmdThread(Server s, UrgentMsgThread t){
        this.setName("CMDThread");
        _t = t;
        _s = s;
    }
    
    @Override
    synchronized public void run() {
        InputStreamReader in = new InputStreamReader(System.in);
        int c;
        while(!q){
            try{
            c = in.read();}
            catch (IOException e){ continue;}
            _t.send("Typed: " + (char)c);
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
