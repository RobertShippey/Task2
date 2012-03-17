/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import shared.Booking;

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
    private List<Booking> reservations;
    
    public Session (Socket ip, Server s) throws IOException{
        server = s;
        _ip = ip;
        in = new ObjectInputStream(_ip.getInputStream());
        out = new ObjectOutputStream(ip.getOutputStream());
        
    }
    
    @Override
    synchronized public void run(){
        try {
            _name = (String)in.readObject();
        } catch (IOException ex) {
            //could not connect through socket anymore
        } catch (ClassNotFoundException ex) {
            //Didn't send a String of their username to start
        }
        
        while(!server.quitting()){
            try{
            Object something = in.readObject();
            //processing
            
            //if the client logs off
            //server.removeClient(this); //then return I guess...
            
            out.writeObject(something);
            } catch (IOException e) {
                //could not connect though socket anymore
            } catch (ClassNotFoundException cnf) {
                //client sent something weird
            }
        }
        
        
        
    }
    
    public boolean isConnected(){
        return _ip.isConnected();

    }
    
    public void forceQuit(){
        try{
        _ip.close();}
        catch (IOException e){ }
        _ip = null;
    }
    
    private void getCustomersReservations(){
        reservations = server.findBookings(_name);
    }
}
