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
    }
    
    public void addBooking(String name, int count){
        
        for(int x=0; x<count;x++){
            seats[booked++] = new Booking(name);
        }
    }
    
    public void removeBooking(String name){
        
    }
}
