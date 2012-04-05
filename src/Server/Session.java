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
    private boolean quit;

    public Session(Socket ip, Server s) throws IOException {
        server = s;
        data = server.getData();
        _ip = ip;
        out = new ObjectOutputStream(_ip.getOutputStream());
        in = new ObjectInputStream(_ip.getInputStream());
        quit = false;
        reservations = new LinkedList<Booking>();
    }

    @Override
    synchronized public void run() {
        try {
            _ip.setSoTimeout(Server.TIMEOUT_BLOCK);
            _name = (String) in.readObject();
            this.setName(_name + ":Thread");
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

        while (!quit) {
            try {
                Request req = (Request) in.readObject();

                String command = req.getRequest();
                System.out.println(command);
                
                String name = req.getName();
                String film = req.getFilm();
                String date = req.getDate();
                String time = req.getTime();
                int seats = req.getSeats();
                int newSeats = req.getNewSeats();
                
                Response r = new Response();

                if (command.equals(Request.MAKE)) {
                    if (data.makeReservation(name, film, date, time, seats)) {
                        r.setSuccess(true);
                    } else {
                        r.setSuccess(false);
                        r.setReason("Capacity of the film is full. Try a different showing.");
                    }
                } else if (command.equals(Request.AMEND)) {
                    if (data.changeReservation(name, film, date, time, seats, newSeats)) {
                        r.setSuccess(true);
                    } else {
                        r.setSuccess(false);
                        r.setReason("Capacity of the film is full. Try a different showing.");
                    }
                } else if (command.equals(Request.DELETE)) {
                    data.cancelReservation(time, film, date, time, seats);
                    r.setSuccess(true);

                } else if (command.equals(Request.REFRESH_OFFERS)) {
                    if (data.offers == null) {
                        r.setSuccess(false);
                        r.setReason("Offers not found");
                    } else {
                        r.setReason(data.offers);
                        r.setSuccess(true);
                    }
                } else if (command.equals(Request.MY_RESERVATIONS)) {
                    Booking[] b = data.getReservations(_name);
                    if (b != null) {
                        r.setSuccess(true);
                        r.setResponseObjects(b);
                        reservations.removeAll(reservations);
                        reservations.addAll(Arrays.asList(b));
                    } else {
                        r.setSuccess(false);
                        r.setResponseObjects(null);
                        r.setReason("No reservations found");
                    }
                } else if(command.equals(Request.FILMS)){
                    r.setResponseObjects(data.getFilmNames());
                    r.setSuccess(true);
                    
                } else if (command.equals(Request.FILM_DATES)){
                    
                } else if (command.equals(Request.FILM_DATE_TIMES)){
                    
                }
                else if (command.equals(Request.LOG_OFF)) {
                    server.removeClient(this);
                    System.out.println("Removed");
                    quit = true;
                    return;
                } else {
                    r.setSuccess(false);
                    r.setReason("Command not understood by the server");
                }


                //processing
                
                
                
                out.writeObject(r);
            } catch (IOException e) {
                //could not connect though socket anymore
            } catch (ClassNotFoundException cnf) {
                //client sent something weird
            }
        }



    }

    public boolean isConnected() {
        return !quit;

    }

    public synchronized void forceQuit() {
        try {
            _ip.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
