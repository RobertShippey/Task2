/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import shared.Booking;
import shared.Film;

/**
 *
 * @author Robert
 */
public class Data {
    private LinkedList<Film> films;
    private LinkedList<Booking> reservations;
    
    public Data(LinkedList<Film> films, LinkedList<Booking> reservations){
        this.films = films;
        this.reservations = reservations;
    }
    
    public Booking[] getReservations(String customerName){
        LinkedList<Booking> r = new LinkedList<Booking>();
        Iterator<Booking> it = reservations.iterator();
        while(it.hasNext()){
            if(it.next().getName().equals(customerName)){
                r.add(it.next());
            }
        }
        return (Booking[])r.toArray();
        
    }
    
    public Film findFilm(String name, Date time){
        Iterator<Film> fit = films.iterator();
        while (fit.hasNext()) {
            if (fit.next().getName().equals(name) && fit.next().getDate().equals(time)) {
                return fit.next();
            }
        }
        return null;
    }
    
    public boolean makeReservation(String customer, String film, Date time, int no) {
        Film f = findFilm(film, time);
        if (f == null) {
            return false;
        }

        if (f.space() >= no) {
            f.book(no);
            Booking b = new Booking(customer, f, no);
            reservations.add(b);
            return true;
        }
        return false;
    }
    
    public void cancelReservation(String customer, String film, Date time, int no){
        Film f = findFilm(film, time);
        Booking[] b = getReservations(customer);
        for(int x=0; x<b.length;x++){
            if(b[x].getFilm() == f){
                reservations.remove(b[x]);
            }
            
        }
        f.free(no);
        
    }
    
    public boolean isFull(String film, Date time){
        Film f = findFilm(film,time);
        if(f.space()==0){
            return true;
        } else { return false;}
        
    }
    
    public int getSpace(String film, Date time){
        Film f = findFilm(film, time);
        return f.space();
    }
    
}
