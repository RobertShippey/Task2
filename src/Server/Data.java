/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import shared.Booking;
import shared.Film;

/**
 *The class to hold all server data and deal with requests of the data.
 * @author Robert and Nathan
 */
public class Data {

    private Film[] films;
    private List<Booking> reservations;
    private final UrgentMsgThread urgent;
    private String offers;

    /**
     * Constructs the instance
     * @param u instance of the Urgent Message Thread
     */
    public Data(UrgentMsgThread u){
    this.urgent = u;
    }
    
    /**
     * Makes the list synchronized and keeps it in the instance.
     * @param ll a LinkedList of bookings from the data file
     */
    public void addReservationsLL(LinkedList<Booking> ll){
        this.reservations = Collections.synchronizedList(ll);
    }
    
    /**
     * Stores the array in the instance.
     * @param films array of Films from the data file
     */
    public void addFilmsArray(Film[] films){
        this.films = films;
    }

    /**
     * find all reservations that were made by customerName
     * @param customerName the customer's name
     * @return an array of Bookings that belong to the customer
     */
    public Booking[] getReservations(String customerName) {
        synchronized (reservations) {
            LinkedList<Booking> r = new LinkedList<Booking>();
            Iterator<Booking> it = reservations.iterator();
            while (it.hasNext()) {
                Booking res = it.next();
                if (res.getName().equals(customerName)) {
                    r.add(res);
                }
            }
            if (r.size() > 0) {
                
                Booking[] a = new Booking[r.size()];
                Object[] objArray = r.toArray();
                int i = 0;
                for (Object o : objArray) {
                    a[i++] = (Booking) o;
                }

                return a;
            }
            return null;

        }
    }

    /**
     * Searches for a film in the array, returns null if not found
     * @param name the Films name
     * @param date the date of the Film "dd/mm/yyyy"
     * @param time the time of the Film "hh:mm"
     * @return Film instance
     */
    public synchronized Film findFilm(String name, String date, String time) {
            for(int x=0;x<films.length;x++){
                if (films[x].getName().equals(name) && films[x].getDate().equals(date) 
                        && films[x].getTime().equals(time)) {
                    return films[x];
                }
            }
        return null;
    }

    /**
     * makes a reservation and stores it in the linked list
     * @param customer the customer's name
     * @param film the film name
     * @param date the film date
     * @param time the time of the film
     * @param no the number of seats 
     * @return true if made, false if couldn't make reservation
     */
    public boolean makeReservation(String customer, String film, 
            String date, String time, int no) {
        Film f = findFilm(film, date, time);
        if (f == null) {
            return false;
        }
        synchronized (reservations) {
            if (f.space() >= no) {
                f.book(no);
                Booking b = new Booking(customer, f, no);
                reservations.add(b);
                
                if (f.space() == 0) {
                    urgent.send(film + " is now fully booked at " 
                            + date + " " + time);
                }
                
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the customer's reservation and removes it from the linked list, also frees space from the film
     * @param customer the customer's name
     * @param film the film name
     * @param date the film date
     * @param time the film time
     * @param no the number of seats
     */
    public void cancelReservation(String customer, String film, 
            String date, String time, int no) {
        Film f = findFilm(film, date, time);
        Booking[] b = getReservations(customer);
        synchronized (reservations) {
            for (int x = 0; x < b.length; x++) {
                if ((b[x].getFilm() == f) && (b[x].getSeats() == no)) {
                    reservations.remove(b[x]);
                }

            }
        }
        int s = f.space();
        f.free(no);
        if (s == 0) {
            urgent.send(film + " now has " + f.space() 
                    + " seats available at " + date + " " + time);
        }

    }

    /**
     * Removes old booking and creates a new booking
     * @param customer the customer name
     * @param film the film name
     * @param date the film date
     * @param time the film time
     * @param oldSeats the number of seats the old reservation had
     * @param newSeats the number of seats for the new reservation to have
     * @return true if able to make new booking and false if could not.
     */
    public boolean changeReservation(String customer, String film, 
            String date, String time, int oldSeats, int newSeats) {
        this.cancelReservation(customer, film, date, time, oldSeats);
        return this.makeReservation(customer, film, date, time, newSeats);
    }

    /**
     * Checks if a film is fully booked.
     * @param film the film name
     * @param date the film date
     * @param time the film time
     * @return true if film is fully booked, false if not
     */
    public boolean isFull(String film, String date, String time) {
        Film f = findFilm(film, date, time);
        if (f.space() == 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Gets the amount of free space that the film has.
     * @param film the film name
     * @param date the film date
     * @param time the film time
     * @return the number of seats available for booking
     */
    public int getSpace(String film, String date, String time) {
        Film f = findFilm(film, date, time);
        return f.space();
    }

    /**
     * Gets a new iterator of the reservations LinkedList
     * @return a new iterator
     */
    public Iterator<Booking> getBookingIt() {
        synchronized (reservations) {
            return reservations.iterator();
        }
    }
    
    /**
     * Gets all of the films to string and creates a CSV format string ready to be written to file
     * @return the films to a csv string
     */
    public synchronized String allFilmsToString() {
        String r = "";
        if (films != null) {
            for (int x = 0; x < films.length; x++) {
                r += films[x].toString() + Server.endLine;
            }
            return r;
        } else {
            return null;
        }

    }
    
    /**
     * Gets a String array of the names of all the films
     * @return Film names
     */
    public String[] getFilmNames(){
        String[] r = new String[films.length];
        for(int x=0; x<r.length;x++){
            r[x] = films[x].getName();
        }
        return r;
    }

    /**
     * Gets an array of films whos name match the parameter
     * @param film the film name
     * @return an Object array of Films
     */
    public Object[] findFilms(String film) {
        LinkedList<Film> r = new LinkedList<Film>();
        for(int x=0;x<films.length;x++){
            if(films[x].getName().equals(film)){
                r.add(films[x]);
            }
        }
        if(r.isEmpty()){
            return null;
        }
        return r.toArray();
    }
    
    /**
     * Gets an array of films whos name and date match the parameters
     * @param film the films name
     * @param date the films time
     * @return an Object array of Films
     */
    public Object[] findFilms(String film, String date){
         LinkedList<Film> r = new LinkedList<Film>();
        for(int x=0;x<films.length;x++){
            if(films[x].getName().equals(film) && films[x].getDate().equals(date)){
                r.add(films[x]);
            }
        }
        if(r.isEmpty()){
            return null;
        }
        return r.toArray();
        
    }
    
    /**
     * Sets the offers for use by the clients
     * @param off the offer string
     */
    public void setOffers(String off){
        this.offers = off;
    }
    
    /**
     * Get the offers from the data structure
     * @return the offers string
     */
    public String getOffers(){
        return this.offers;
    }
}
