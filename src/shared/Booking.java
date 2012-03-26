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
    private int number;
    
    public Booking(){ }
    public Booking(String name, Film film, int no) {
        this._customerName = name;
        this._film = film;
        this.number = no;
    }
    
    public void setName(String name){
        this._customerName = name;
    }
    
    public String getName(){
        return _customerName;
    }
    
    public Film getFilm(){
        return this._film;
    }
    
}
