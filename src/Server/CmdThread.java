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
    private Server server;
    private boolean quit = false;
    
    /**
     * Constructs and instance.
     * @param s instance of the server
     */
    public CmdThread(Server s){
        this.setName("CMDThread");
        server = s;
    }
    
    /**
     * Reads characters from STDIN and sets quit/force quit flags accordingly.
     */
    @Override
    synchronized public void run() {
        InputStreamReader in = new InputStreamReader(System.in);
        int c;
        while(!quit){
            try{
            c = in.read();}
            catch (IOException e){ continue;}
            if(c == (int)'q'){
                quit = true;
                server.setQuit("q");
            }
            if(c == (int)'f'){
                quit = true;
                server.setQuit("f");
            }
        } 
    }
    
}
