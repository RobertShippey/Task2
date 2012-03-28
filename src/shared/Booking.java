/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;

/**
 *
 * @author Robert
 */
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String _customerName;
    private Film _film;
    private int seats;
    
    public Booking(String name, Film film, int no) {
        this._customerName = name;
        this._film = film;
        this.seats = no;
    }
        
    public String getName(){
        return _customerName;
    }
    
    public Film getFilm(){
        return this._film;
    }
    
    public int getSeats(){
        return this.seats;
    }
}
