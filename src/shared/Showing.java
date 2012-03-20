/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Robert
 */
public class Showing implements Serializable {
    private static final long serialVersionUID = 1L;
    private Booking[] seats;
    private int capacity;
    private int booked;
    private Date date;
    private String filmName;
    
    public Showing(int capacity, Date date, String name){
        this.capacity = capacity;
        this.date = date;
        this.filmName = name;
        this.seats = new Booking[capacity];
        for(int x=0;x<seats.length;x++){
            seats[x] = new Booking (name);
        }
    }
    
    public void addBooking(String name, int count){
        
        for(int x=0; x<count && booked<=capacity;x++){
            seats[booked++].setName(name);
        }
    }
    
    public void removeBooking(String name){
        for(int x=0;x<seats.length;x++){
            if(name.equalsIgnoreCase(seats[x].getName())){
                seats[x] = null;
                booked--;
            }
        }
        
    }
    
    public LinkedList<Booking> getBookings(String name){
        LinkedList<Booking> b = new LinkedList<Booking>();
        for(int x=0;x<seats.length;x++){
            if(name.equals(seats[x].getName())){
                b.add(seats[x]);
            }
            
        }
        if(b.isEmpty()){
            return null;
        }
        
        return b;
    }
}
