/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import shared.Booking;
import shared.Request;
import shared.Response;

/**
 *
 * @author Robert
 */
class Session extends Thread {

    private Server server;
    private Data data;
    private String _name;
    private Socket _ip;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LinkedList<Booking> reservations;

    public Session(Socket ip, Server s) throws IOException {
        server = s;
        data = server.getData();
        _ip = ip;
        out = new ObjectOutputStream(_ip.getOutputStream());
        in = new ObjectInputStream(_ip.getInputStream());
        
    }

    @Override
    synchronized public void run() {
        try {
            _ip.setSoTimeout(Server.TIMEOUT_BLOCK);
            _name = (String) in.readObject();
            System.out.println(_name);
            if (server.addUser(_name)) {
                out.writeObject(null);
            } else {
                Booking[] b = data.getReservations(_name);
                if (b != null) {
                    reservations.addAll(Arrays.asList(b));
                    out.writeObject(reservations.toArray());
                } else {
                    out.writeObject(null);
                }
            }
            _ip.setSoTimeout(Server.TIMEOUT);
        } catch (IOException ex) {
            //could not connect through socket anymore
        } catch (ClassNotFoundException ex) {
            //Didn't send a String of their username to start
        }

        while (!server.quitting()) {
            try {
                Request req = (Request)in.readObject();

                String command = req.getRequest();
                System.out.println(command);
                Response r = new Response();
                
                //processing
                
                if(command.equals(Request.LOG_OFF)){
                    server.removeClient(this);
                    System.out.println("Removed");
                    return;
                }
                
                out.writeObject(r);
            } catch (IOException e) {
                //could not connect though socket anymore
            } catch (ClassNotFoundException cnf) {
                //client sent something weird
            }
        }



    }

    public boolean isConnected() {
        return _ip.isConnected();

    }

    public void forceQuit() {
        try {
            _ip.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
