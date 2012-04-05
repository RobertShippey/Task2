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
public class Request implements Serializable {

    public static final String LOG_OFF = "LOGOFF";
    public static final String MAKE = "MAKERESERVATION";
    public static final String AMEND = "CHANGERESERVATON";
    public static final String DELETE = "DELETERESERVATION";
    public static final String REFRESH_OFFERS = "REFRESHSPECIALOFFERS";
    public static final String FILMS = "GETALLFILMS";
    public static final String MY_RESERVATIONS = "GETALLMYRESERVATIONS";
    public static final String FILM_DATES = "GETFILMDATES";
    public static final String FILM_DATE_TIMES = "GETFILMDATETIMES";
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String request;
    private String film;
    private String date;
    private String time;
    private int seats;
    private int newSeats;

    
    public void setNewSeats(int s){
        this.newSeats = s;
    }
    
    public int getNewSeats(){
        return newSeats;
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    public String getName(){
        return name;
    }

    public String getRequest() {
        return this.request;
    }

    public void setRequest(String r) {
        this.request = r;
    }

    public Request(String r) {
        this.request = r;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
