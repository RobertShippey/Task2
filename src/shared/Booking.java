/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

/**
 *
 * @author Robert
 */
public class Booking {
    private String _customerName;
    private String _film;
    
    public Booking(){ }
    public Booking(String film) {_film = film;}
    
    public void setName(String name){
        _customerName = name;
    }
    
    public String getName(){
        return _customerName;
    }
    
}
