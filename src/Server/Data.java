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

    private List<Film> films;
    private List<Booking> reservations;

    public Data(LinkedList<Film> films, LinkedList<Booking> reservations) {
        this.films = Collections.synchronizedList(films);
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
            return (Booking[]) r.toArray();
        }
    }

    public Film findFilm(String name, String time) {
        synchronized (films) {
            Iterator<Film> fit = films.iterator();
            while (fit.hasNext()) {
                if (fit.next().getName().equals(name) && fit.next().getDate().equals(time)) {
                    return fit.next();
                }
            }
        }
        return null;
    }

    public boolean makeReservation(String customer, String film, String time, int no) {
        Film f = findFilm(film, time);
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

    public void cancelReservation(String customer, String film, String time, int no) {
        Film f = findFilm(film, time);
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

    public boolean changeReservation(String customer, String film, String time, int oldSeats, int newSeats) {
        this.cancelReservation(customer, film, time, oldSeats);
        return this.makeReservation(customer, film, time, newSeats);
    }

    public boolean isFull(String film, String time) {
        Film f = findFilm(film, time);
        if (f.space() == 0) {
            return true;
        } else {
            return false;
        }

    }

    public int getSpace(String film, String time) {
        Film f = findFilm(film, time);
        return f.space();
    }

    public Iterator<Film> getFilmItt() {
        synchronized (films) {
            return films.iterator();
        }
    }

    public Iterator<Booking> getBookingItt() {
        synchronized (reservations) {
            return reservations.iterator();
        }
    }
}
