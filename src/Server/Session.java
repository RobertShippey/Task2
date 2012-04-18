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
import shared.Film;
import shared.Request;
import shared.Response;

/**
 * The client's threaded session with the server
 * @author Robert
 */
public class Session extends Thread {

    private Server server;
    private Data data;
    private String name;
    private Socket IP;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LinkedList<Booking> reservations;
    private boolean quit;

    /**
     * Creates a session ready for the thread to be started.
     * @param ip a socket to the client
     * @param s the instance of the server
     * @throws IOException if any setup error occurs
     */
    public Session(Socket ip, Server s) throws IOException {
        server = s;
        data = server.getData();
        IP = ip;
        out = new ObjectOutputStream(IP.getOutputStream());
        in = new ObjectInputStream(IP.getInputStream());
        quit = false;
        reservations = new LinkedList<Booking>();
    }

    /**
     * Loop of interaction with the server.
     * Parse requests, process data, write back a response.
     */
    @Override
    synchronized public void run() {
        try {
            IP.setSoTimeout(Server.TIMEOUT_BLOCK);
            name = (String) in.readObject();
            this.setName(name + ":Thread");
            server.log.writeEvent("Connected: " + name, false);
            if (server.addUser(name)) {
                out.writeObject(null);
            } else {
                Booking[] b = data.getReservations(name);
                if (b != null) {
                    reservations.addAll(Arrays.asList(b));
                    out.writeObject(reservations.toArray());
                } else {
                    out.writeObject(null);
                }
            }
            //_ip.setSoTimeout(Server.TIMEOUT);
        } catch (IOException ex) {
            if(name == null) {name = "Unknown"; }
            server.log.writeError(name + " session: " + ex.getMessage(), true);
            return;
        } catch (ClassNotFoundException ex) {
            if(name == null) {name = "Unknown"; }
            server.log.writeError(name + " session: " + ex.getMessage(), true);
            return;
        }

        while (!quit) {
            try {
                Request req = (Request) in.readObject();

                String command = req.getRequest();
                server.log.writeEvent(name + " session: requested " + command, false);
                
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
                    data.cancelReservation(name, film, date, time, seats);
                    r.setSuccess(true);

                } else if (command.equals(Request.REFRESH_OFFERS)) {
                    if (data.offers == null) {
                        r.setSuccess(false);
                        r.setReason("Offers not found");
                    } else {
                        r.setResponse(data.offers);
                        r.setSuccess(true);
                    }
                } else if (command.equals(Request.MY_RESERVATIONS)) {
                    Booking[] b = data.getReservations(this.name);
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
                    
                    Object[] films = data.findFilms(film);
                    if(films!=null){
                    String[] dates = new String[films.length];
                    for(int x=0;x<dates.length;x++){
                        dates[x] = ((Film)films[x]).getDate();
                    }
                    r.setResponseObjects(dates);
                    r.setSuccess(true);
                    } else {
                        r.setSuccess(false);
                        r.setReason("No dates found for the specified film.");
                    } 
                } else if (command.equals(Request.FILM_DATE_TIMES)){
                    Object[] films = data.findFilms(film, date);
                    if(films!=null){
                    String[] times = new String[films.length];
                    for(int x=0;x<times.length;x++){
                        times[x] = ((Film)films[x]).getTime();
                    }
                    r.setResponseObjects(times);
                    r.setSuccess(true);
                    } else {
                        r.setSuccess(false);
                        r.setReason("No dates found for the specified film at the specified time.");
                    } 
                } else if (command.equals(Request.FILM_DATE_TIME_SEATS)) {
                    String[] seatsStrings;
                    if(data.findFilm(film, date, time).space() == 0){
                        seatsStrings = new String[data.findFilm(film, date, time).getCapacity()];
                        for (int x = 0; x < seatsStrings.length; x++) {
                            seatsStrings[x] = Integer.toString(x + 1);
                        }
                    } else {
                        seatsStrings = new String[data.findFilm(film, date, time).space()];
                        for (int x = 0; x < seatsStrings.length; x++) {
                            seatsStrings[x] = Integer.toString(x + 1);
                        }
                    }

                    r.setResponseObjects(seatsStrings);
                    r.setSuccess(true);
                }else if (command.equals(Request.LOG_OFF)) {
                    server.removeClient(this);
                    quit = true;
                    return;
                } else {
                    r.setSuccess(false);
                    r.setReason("Command not understood by the server");
                }

                out.writeObject(r);
            } catch (IOException e) {
               server.log.writeError(name + " session: " + e.getMessage(), true);
            } catch (ClassNotFoundException cnf) {
                server.log.writeError(name + " session: " + cnf.getMessage(), true);
            }
        }
    }

    /**
     * Is the session connected?
     * @return if the session is connected
     */
    public boolean isConnected() {
        return !quit;
    }

    /**
     * Close the socket.
     */
    public synchronized void forceQuit() {
        try {
            IP.close();
        } catch (IOException e) {
            server.log.writeError(name + " session: ", false);
        }
    }
}
