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
    
    public void logoff(){
        try{
        out.writeObject(new Request(LOG_OFF));
        in.close();
        out.close();
        server.close();
        } catch (IOException e){ }
    }
    
    public int getReservationsLength(){
        if(reservations==null){
            return 1;
        }
        return reservations.length;
    }
    
    public Booking getReservation(final int i){
        if(i >= reservations.length || i<0){
            throw new ArrayIndexOutOfBoundsException("Out of bounds. Use getReservationLength.");
        } else {
            return reservations[i];
        }
    }
    
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
    
    public String[] getFilmNames(){
        Request r = new Request(Request.FILMS);
        Response response = sendRequest(r);
        Object[] filmObjs =  response.getResponseObjects();
        String[] films = new String[filmObjs.length];
        for(int x =0; x<filmObjs.length;x++){
            films[x] = (String) filmObjs[x];
        }
        return films;
    }

    String[] getFilmDates(String film) {
        Request req = new Request(Request.FILM_DATES);
        req.setFilm(film);
        
        Response response = sendRequest(req);
        Object[] obj = response.getResponseObjects();
        String[] r = new String[obj.length];
        for(int x=0;x<obj.length;x++){
            r[x] = (String) obj[x];
        }
        return r;
    }

    String[] getFilmDateTimes(String film, String date) {
        Request req = new Request (Request.FILM_DATE_TIMES);
        req.setFilm(film);
        req.setDate(date);
        
        Response response = sendRequest(req);
        Object[] obj = response.getResponseObjects();
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

    String[] getFilmDateTimeSeats(String film, String date, String time) {
        Request req = new Request(Request.FILM_DATE_TIME_SEATS);
        req.setFilm(film);
        req.setDate(date);
        req.setTime(time);
        
        Response response = sendRequest(req);
        Object[] obj = response.getResponseObjects();
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
    
}
