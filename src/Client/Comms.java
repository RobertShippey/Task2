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
 * The clients communication with the 
 * @author Robert
 */
public class Comms{
    
    private Socket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String user;
    private Booking[] reservations;

    /**
     * Create a new session with the server
     * @param host ip address of the server
     * @throws IOException can't connect to the server
     */
    public Comms() throws IOException {
        server = new Socket(Client.getServer(), 2000);
        server.setSoTimeout(0);
        in = new ObjectInputStream(server.getInputStream());
        out = new ObjectOutputStream(server.getOutputStream());
    }

    /**
     * Log on to the server
     * @param name the users name
     */
    public void logon(String name) {
        user = name;
        try {
            out.writeObject(user);
            Object[] r = (Object[]) in.readObject();
            if (r != null) {
                Booking[] a = new Booking[r.length];
                int i = 0;
                for (Object o : r) {
                    a[i++] = (Booking) o;
                }

                reservations = a;
            }
        } catch (ClassNotFoundException cnf) {
        } catch (IOException ioe) {
        }
    }
    
    /**
     * Log off, disconnect the session with the server.
     */
    public void logoff() {
        try {
            out.writeObject(new Request(Request.LOG_OFF));
            server.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Get how long the reservations array is
     * @return reservations.length
     */
    public int getReservationsLength(){
        if(reservations==null){
            return 0;
        }
        return reservations.length;
    }
    
    /**
     * Get a specific reservation
     * @param i the index of the reservation
     * @return the reservation indexed by i
     * @throws ArrayIndexOutOfBoundsException 
     */
    public Booking getReservation(final int i)throws ArrayIndexOutOfBoundsException{
        if(i >= reservations.length || i<0){
            throw new ArrayIndexOutOfBoundsException("Out of bounds. Use getReservationLength.");
        } else {
            return reservations[i];
        }
    }
    
    /**
     * Get local or remote reservations that are owned by this user and format them in a comma seperated format.
     * @param refresh use true to get new data from the server, false to use local cache
     * @return all reservations as Strings.
     */
    public String[] getAllReservationsAsStrings(boolean refresh) {
        if (refresh) {
            Request r = new Request(Request.MY_RESERVATIONS);
            Response response = sendRequest(r);
            Object[] objs = response.getResponseObjects();
            if(objs==null){
                String[] n = {""};
                return n;
            }
            Booking[] bookings = new Booking[objs.length];
            for (int x = 0; x < objs.length; x++) {
                bookings[x] = (Booking) objs[x];
            }
            reservations = bookings;
        }
        return getAllReservationsAsStrings();
    }
    
    /**
     * Get local reservations formatted as comma separated Strings
     * @return all reservations as Strings
     */
    public String[] getAllReservationsAsStrings(){
        if(reservations==null){
            String[] r = {""};
           return r;
        }
        String[] r = new String [reservations.length];
        for (int x =0; x<r.length;x++){
            r[x] = reservations[x].getFilm().getName() + ", " + reservations[x].getFilm().getDate() + ", " + reservations[x].getFilm().getTime() + ", " + reservations[x].getSeats();
        }
        return r;
    }
    
    /**
     * Sends a Request object to the server. Ensure that it was constructed with a static string from Request.
     * This will return null if any exception is catched.
     * @param r the request
     * @return the Response object created by the server or null.
     */
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
    
    /**
     * Get all film names from the server
     * @return Film names as Strings
     */
    public String[] getFilmNames(){
        Request r = new Request(Request.FILMS);
        Response response = sendRequest(r);
        Object[] filmObjs =  removeDuplicates(response.getResponseObjects());
        String[] films = new String[filmObjs.length];
        for(int x =0; x<filmObjs.length;x++){
            films[x] = (String) filmObjs[x];
        }
        return films;
    }
    
    /**
     * Get, from the server, the dates of the films with the name passed in
     * @param film the film name
     * @return Strings of dates
     */
    String[] getFilmDates(String film) {
        Request req = new Request(Request.FILM_DATES);
        req.setFilm(film);
        
        Response response = sendRequest(req);
        Object[] obj = removeDuplicates(response.getResponseObjects());
        String[] r = new String[obj.length];
        for(int x=0;x<obj.length;x++){
            r[x] = (String) obj[x];
        }
        return r;
    }

    /**
     * Get, from the server, the times of the specified film on the specified date.
     * @param film the film name
     * @param date the film date
     * @return String of times
     */
    String[] getFilmDateTimes(String film, String date) {
        Request req = new Request (Request.FILM_DATE_TIMES);
        req.setFilm(film);
        req.setDate(date);
        
        Response response = sendRequest(req);
        Object[] obj = removeDuplicates(response.getResponseObjects());
        if(obj!=null){
        String[] r = new String[obj.length];
        for(int x=0;x<obj.length;x++){
            r[x] = (String) obj[x];
        }
        return r;
        } else {
            String[] n = {""};
            return n;
        }
        
    }

    /**
     * Gets, from the server, an array of seats. If the film is fully booked it will return 0 to capacity.
     * If the film is not fully booked it will return 0 to space.
     * @param film the film name
     * @param date the film date
     * @param time the film time
     * @return the seats as Strings
     */
    String[] getFilmDateTimeSeats(String film, String date, String time) {
        Request req = new Request(Request.FILM_DATE_TIME_SEATS);
        req.setFilm(film);
        req.setDate(date);
        req.setTime(time);
        
        Response response = sendRequest(req);
        Object[] obj = removeDuplicates(response.getResponseObjects());
        if(obj==null){
            String[] n = {""};
            return n;
        }
        String[] r = new String[obj.length];
        for(int x=0;x<obj.length;x++){
            r[x] = (String) obj[x];
        }
        return r;
    }
    
    /**
     * Removes duplicated (based on the string value of the objects) of any Object array.
     * Used for removing duplications in the dropdown boxes.
     * @param objs any object array
     * @return a new Object array with no duplications
     */
    private Object[] removeDuplicates(final Object[] objs){
        if(objs == null || objs.length==0) { return null; }
        int count = 1;
        boolean[] unique = new boolean[objs.length];
        unique[0] = true;
        for(int x=1;x<objs.length;x++){
            unique[x] = true;
            for(int y=0;y<x;y++){
                if((objs[x].toString().equals(objs[y].toString()))){
                    unique[x] = false;
                    break;
                }
            }
            if(unique[x]) { count++; }
        }
        
        Object[] r = new Object[count];
        int i=0;
        for(int x=0;x<objs.length;x++){
            if(unique[x]){
                r[i++] = objs[x];
            }
        }
        return r;
    }
    
}
