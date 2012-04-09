/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;

/**
 * The storage class for a reservation
 * @author Robert
 */
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String _customerName;
    private Film _film;
    private int seats;
    
    /**
     * constructs a new instance of a booking, setting instance fields
     * @param name the customer's name
     * @param film a Film object for the reservation
     * @param no the number of seats
     */
    public Booking(String name, Film film, int no) {
        this._customerName = name;
        this._film = film;
        this.seats = no;
    }
        
    /**
     * Get the name of the customer from this instance
     * @return the customer's name
     */
    public String getName(){
        return _customerName;
    }
    
    /**
     * Returns the film this is a reservation of
     * @return a Film object
     */
    public Film getFilm(){
        return this._film;
    }
    
    /**
     * Get the number of seats reserved in this booking
     * @return the number of seats
     */
    public int getSeats(){
        return this.seats;
    }
}
