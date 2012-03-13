/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Robert
 */
class Session extends Thread{
    private Server server;
    private String _name;
    private Socket _ip;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public Session (Socket ip, Server s) throws IOException{
        server = s;
        _ip = ip;
        in = new ObjectInputStream(_ip.getInputStream());
        out = new ObjectOutputStream(ip.getOutputStream());
        
    }
    
    @Override
    synchronized public void run(){
        
        
        server.removeClient(this);
    }
}
