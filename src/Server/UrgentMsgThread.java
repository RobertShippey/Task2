/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.List;

/**
 *
 * @author Robert
 */
class UrgentMsgThread extends Thread {
    private Server _s;
    private List<String> _msgs;

    public UrgentMsgThread(Server server) {
        _s = server;
    }
    
    @Override
    synchronized public void run(){
        
    }
    
    public void send(String msg){
        _msgs.add(msg);
    }
    
    
}
