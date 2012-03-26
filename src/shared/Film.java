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
public class Film {
    private String name;
    private Date time;
    private int capacity;
    private int booked;
    
    public Film(String name, Date time, int capacity){
        this.name = name;
        this.time = time;
        this.capacity = capacity;
        this.booked = 0;
    }
    
    public Film(String name, Date time, int capacity, int booked){
        this.name = name;
        this.time = time;
        this.capacity = capacity;
        this.booked = booked;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Date getDate(){
        return this.time;
    }
    
    public int space(){
        return this.capacity - this.booked;
    }
    
    public boolean book(int c){
        if(this.booked-c < 0){
            return false;
        } else {
            this.booked -= c;
            return true;
        }
    }
    
    public void free(int c){
        this.booked += c;
    }
    
}
