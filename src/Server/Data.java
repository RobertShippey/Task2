/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import shared.Booking;
import shared.Film;

/**
 *
 * @author Robert
 */
public class Data {

    private Film[] films;
    private List<Booking> reservations;

    public Data(Film[] films, LinkedList<Booking> reservations) {
        this.films = films;
        this.reservations = Collections.synchronizedList(reservations);
    }

    public Booking[] getReservations(String customerName) {
        synchronized (reservations) {
            LinkedList<Booking> r = new LinkedList<Booking>();
            Iterator<Booking> it = reservations.iterator();
            while (it.hasNext()) {
                if (it.next().getName().equals(customerName)) {
                    r.add(it.next());
                }
            }
            if (r.size() > 0) {
                return (Booking[]) r.toArray();
            }
            return null;

        }
    }

    public synchronized Film findFilm(String name, String date, String time) {
            for(int x=0;x<films.length;x++){
                if (films[x].getName().equals(name) && films[x].getDate().equals(date) && films[x].getTime().equals(time)) {
                    return films[x];
                }
            }
        return null;
    }

    public boolean makeReservation(String customer, String film, String date, String time, int no) {
        Film f = findFilm(film, date, time);
        if (f == null) {
            return false;
        }
        synchronized (reservations) {
            if (f.space() >= no) {
                f.book(no);
                Booking b = new Booking(customer, f, no);
                reservations.add(b);
                return true;
            }
        }
        return false;
    }

    public void cancelReservation(String customer, String film, String date, String time, int no) {
        Film f = findFilm(film, date, time);
        Booking[] b = getReservations(customer);
        synchronized (reservations) {
            for (int x = 0; x < b.length; x++) {
                if (b[x].getFilm() == f) {
                    reservations.remove(b[x]);
                }

            }
        }
        f.free(no);

    }

    public boolean changeReservation(String customer, String film, String date, String time, int oldSeats, int newSeats) {
        this.cancelReservation(customer, film, date, time, oldSeats);
        return this.makeReservation(customer, film, date, time, newSeats);
    }

    public boolean isFull(String film, String date, String time) {
        Film f = findFilm(film, date, time);
        if (f.space() == 0) {
            return true;
        } else {
            return false;
        }

    }

    public int getSpace(String film, String date, String time) {
        Film f = findFilm(film, date, time);
        return f.space();
    }

    public Iterator<Booking> getBookingItt() {
        synchronized (reservations) {
            return reservations.iterator();
        }
    }
    
    public synchronized String allFilmsToString() {
        String r = new String("");
        if (films != null) {
            for (int x = 0; x < films.length; x++) {
                r += films[x].toString() + "\n";
            }
            return r;
        } else {
            return null;
        }

    }
}
