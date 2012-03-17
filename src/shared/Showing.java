/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.util.Date;

/**
 *
 * @author Robert
 */
public class Showing {
    private Booking[] seats;
    private int capacity;
    private int booked;
    private Date date;
    private String name;
    
    public Showing(int capacity, Date date, String name){
        this.capacity = capacity;
        this.date = date;
        this.name = name;
        this.seats = new Booking[capacity];
        for(int x=0;x<seats.length;x++){
            seats[x] = new Booking (name);
        }
    }
    
    public void addBooking(String name, int count){
        
        for(int x=0; x<count;x++){
            seats[booked++].setName(name);
        }
    }
    
    public void removeBooking(String name){
        
    }
    
    public Booking[] getBookings(String name){
        Booking[] b = new Booking[10];
        //find all bookings with this name and add them to the array;
        
        return b;
    }
}
