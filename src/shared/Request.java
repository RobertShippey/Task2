/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;

/**
 * The class that the client will send to the server to request a command to be run.
 * @author Robert
 */
public class Request implements Serializable {

    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String LOG_OFF = "LOG_OFF";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String MAKE = "MAKE_RESERVATION";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String AMEND = "CHANGE_RESERVATON";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String DELETE = "DELETE_RESERVATION";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String REFRESH_OFFERS = "REFRESH_SPECIAL_OFFERS";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String FILMS = "GET_ALL_FILMS";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String MY_RESERVATIONS = "GET_ALL_MY_RESERVATIONS";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String FILM_DATES = "GET_FILM_DATES";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String FILM_DATE_TIMES = "GET_FILM_DATE_TIMES";
    /**
     * static field to be passed into the constructor of a new Request.
     */
    public static final String FILM_DATE_TIME_SEATS = "GET_FILM_DATE_TIMESEATS";
    
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String request;
    private String film;
    private String date;
    private String time;
    private int seats;
    private int newSeats;

    /**
     * getter for newSeats
     * @param s number of new seats
     */
    public void setNewSeats(int s){
        this.newSeats = s;
    }
    
    /**
     * setter for newSeats
     * @return number of new seats
     */
    public int getNewSeats(){
        return newSeats;
    }
    
    /**
     * setter for name
     * @param n the customer name
     */
    public void setName(String n){
        this.name = n;
    }
    
    /**
     * getter for name
     * @return the customer name
     */
    public String getName(){
        return name;
    }

    /**
     * getter for request. This should be one of the static Strings
     * @return the request
     */
    public String getRequest() {
        return this.request;
    }

    /**
     * setter for request. This should be one of the static Strings
     * @param r the request
     */
    public void setRequest(String r) {
        this.request = r;
    }

    /**
     * constructs a request. Should pass in a static string of this class to ensure compatibility.
     * @param r the request. Always use one of the static Strings
     */
    public Request(String r) {
        this.request = r;
    }

    /**
     * getter for film
     * @return the film name
     */
    public String getFilm() {
        return film;
    }

    /**
     * setter for film
     * @param film the film name
     */
    public void setFilm(String film) {
        this.film = film;
    }

    /**
     * getter for date
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * setter for date
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * getter for time
     * @return the film
     */
    public String getTime() {
        return time;
    }

    /**
     * setter for time
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * getter for seats
     * @return the number seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * setter for seats
     * @param seats the number of seats
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }
}
