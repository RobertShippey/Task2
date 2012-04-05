/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import shared.Booking;
import shared.Request;
import shared.Response;

/**
 *
 * @author Robert
 */
class Comms{
    
    private static final String LOG_OFF = "LOGOFF";
    
    private Socket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String user;
    private Booking[] reservations;

    public Comms(String host) throws IOException {
        server = new Socket(host, 2000);
        in = new ObjectInputStream(server.getInputStream());
        out = new ObjectOutputStream(server.getOutputStream());
    }
    
    public void logon(String name){
        user = name;
        try{
        out.writeObject(user);
        Object[] r = (Object[])in.readObject();
        if(r!=null){
            reservations = (Booking[])r;
        }
        } catch (ClassNotFoundException cnf) {
            
        } catch (IOException ioe) {
            
        }
    }
    
    public void logoff(){
        try{
        out.writeObject(new Request(LOG_OFF));
        in.close();
        out.close();
        server.close();
        } catch (IOException e){ }
    }
    
    public int getReservationsLength(){
        return reservations.length;
    }
    
    public Booking getReservation(final int i){
        if(i >= reservations.length || i<0){
            throw new ArrayIndexOutOfBoundsException("Out of bounds. Use getReservationLength.");
        } else {
            return reservations[i];
        }
    }
    
    public Response sendRequest(Request r){
      try{
          r.setName(user);
        out.writeObject(r);
        return (Response) in.readObject();
      } catch (Exception e){
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
     return null;
     
    }
    
}
