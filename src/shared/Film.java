/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;

/**
 * The storage class for a Film
 * @author Robert
 */
public class Film implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String time;
    private int capacity;
    private int booked;
    private String date;
    
    /**
     * Constructs a new film
     * @param name the films name
     * @param time the films time
     * @param capacity  the films capacity
     */
    public Film(String name, String time, int capacity){
        this.name = name;
        this.time = time;
        this.capacity = capacity;
        this.booked = 0;
    }
    
    /**
     * Constructs a new film
     * @param name the films name
     * @param date the films date
     * @param time the films time
     * @param capacity the films capacity
     * @param booked the number of seats booked already
     */
    public Film(String name, String date, String time, int capacity, int booked){
        this.name = name;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
        this.booked = booked;
    }
    
    /**
     * Get this films name
     * @return the films name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Get this films date
     * @return the films date
     */
    public String getDate(){
        return this.date;
    }
    
    /**
     * Get this films capacity
     * @return the films capacity
     */
    public int getCapacity(){
        return this.capacity;
    }
    
    /**
     * Get the number of seats booked for this film
     * @return the number of seats booked
     */
    public int getBooked(){
        return this.booked;
    }
    
    /**
     * The amount of free space in this film
     * @return the number of free seats
     */
    public int space(){
        return this.capacity - this.booked;
    }
    
    /**
     * Book the number of seats passed in. This will return false if there is not enough space to carry out the request 
     * @param c the number of seats to book
     * @return true if booked, false if not
     */
    public boolean book(int c){
        if(this.space()-c < 0){
            return false;
        } else {
            this.booked += c;
            return true;
        }
    }
    
    /**
     * Frees up the number of seats passed in
     * @param c the number of seats
     */
    public void free(int c){
        this.booked -= c;
    }
    
    /**
     * Converts this Film instance to a string where each field is separated by a comma
     * @return this instance to a comma separated string
     */
    @Override
    public String toString(){
        return this.name + "," + this.date + "," 
                + this.time + "," + this.capacity + "," + this.booked;
    }

    /**
     * Gets this films time
     * @return the films time
     */
    public String getTime() {
       return this.time;
    }
    
}
