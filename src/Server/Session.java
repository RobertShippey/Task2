/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.net.Socket;

/**
 *
 * @author Robert
 */
class Session extends Thread{
    private String _name;
    private Socket _ip;
    
    public Session (Socket ip){
        _ip = ip;
        
    }
    
    @Override
    public void run(){
        
    }
}
