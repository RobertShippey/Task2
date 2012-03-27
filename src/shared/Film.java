/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Robert
 */
public class Film implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String time;
    private int capacity;
    private int booked;
    
    public Film(String name, String time, int capacity){
        this.name = name;
        this.time = time;
        this.capacity = capacity;
        this.booked = 0;
    }
    
    public Film(String name, String time, int capacity, int booked){
        this.name = name;
        this.time = time;
        this.capacity = capacity;
        this.booked = booked;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getDate(){
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
