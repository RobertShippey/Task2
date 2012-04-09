/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import shared.Booking;
import shared.Film;

/**
 *The server's entry point and core server functionality
 * @author Robert
 */
public class Server {
    
    /**
     * A non-blocking timeout constant for use in the server
     */
    public static final int TIMEOUT = 10;
    /**
     * A blocking timeout for the server.
     */
    public static final int TIMEOUT_BLOCK = 0;
    //private LinkedList<Showing> _showings;
    private Data data;
    private List<Session> _clients;
    private boolean _quit;
    private boolean _forcequit;
    private List<String> users;
    private String[] offers;

    /**
     * Entry point for the server
     * @param args 
     */
    public static void main(String[] args) {
        Server server = new Server();
        File films = new File("data/films.txt");
        File reservations = new File("data/reservations.txt");
        File users = new File("data/users.txt");
        File offers = new File("data/special_offers.txt");
        server.readFile(films, reservations, users, offers);
       
        ServerSocket s = null;
        try {
            s = new ServerSocket(2000);
            s.setSoTimeout(Server.TIMEOUT);
        } catch (IOException e) {
            server.log.writeError(e.getMessage(), true);
            System.exit(0);
        }

        server.log.writeEvent("Running", true);
        while (!server.quitting()) {
            Socket cl = null;
            try {
                cl = s.accept();
            } catch (IOException e) {
                continue;
            }
            try{
             server.addClient(cl);
            } catch (IOException e) {
                continue;
            }

        }
        server.urgent.send("The server is shutting down!");
        if (server.forceQuitting()) {
            server.closeAllClients();
        } else {
            server.waitOnClients();
        }

        server.writeFile(films, reservations, users);

        server.log.writeEvent("Shutdown", true);
    }
    /**
     * The instance of the urgent message thread. Should only be used for .send(message)
     */
    public final UrgentMsgThread urgent;
    private final CmdThread cmd;
    /**
     * The instance of the log file writer. Should only be used to write messages/errors/events
     */
    public final Log log;

    /**
     * Constructs a new server instance
     */
    public Server() {
        log = new Log();
        log.writeEvent("Started", false);
        _clients = Collections.synchronizedList(new LinkedList<Session>());
        users = Collections.synchronizedList(new LinkedList<String>());
        _quit = false;
        _forcequit = false;
        
        urgent = new UrgentMsgThread(this);
        urgent.start();
        
        cmd = new CmdThread(this, urgent);
        cmd.start();
        
        data = new Data(urgent);
    }

    /**
     * Reads the parameter files into the internal data structures
     * @param f Films file
     * @param r Reservations file
     * @param u Users file
     * @param s Special Offers file
     */
    public void readFile(File f, File r, File u, File s) {
        LinkedList<Booking> bll = new LinkedList<Booking>();
        LinkedList<String> ull = new LinkedList<String>();
        Film[] fl = null;
        String ofrs = null;
        try {
            if(!u.getParentFile().exists()){
                u.getParentFile().mkdir();
            }
            
            if (u.exists()) {
                FileInputStream uf = new FileInputStream(u);
                byte[] us = new byte[uf.available()];
                uf.read(us);
                ull.addAll(Arrays.asList(new String(us).split("\n")));
                uf.close();
            } else {
                u.createNewFile();
            }

            if (f.exists()) {
                FileInputStream ff = new FileInputStream(f);
                byte[] fileBytes = new byte[ff.available()];
                ff.read(fileBytes);
                String films = new String(fileBytes);
                String[] film = films.split("\n");
                fl = new Film[film.length];
                if (!film[0].equals("")) {
                    for (int x = 0; x < film.length; x++) {
                        String[] items = film[x].split(",");
                        fl[x] = new Film(items[0], items[1], items[2], Integer.parseInt(items[3]), Integer.parseInt(items[4]));
                    }
                } else {
                    fl = null;
                }
                ff.close();
            } else {
                f.createNewFile();
            }
            
            data.addFilmsArray(fl);

            if (r.exists()) {
                FileInputStream fis = new FileInputStream(r);
                byte[] t = new byte[fis.available()];
                fis.read(t);
                String text = new String(t);
                String[] records = text.split("\n");
                if(!records[0].equals("")){
                for (int x = 0; x < records.length; x++) {
                    String[] row = records[x].split(",");
                    Booking b = new Booking(row[5], data.findFilm(row[0], row[1], row[2]), Integer.parseInt(row[4]));
                    bll.add(b);
                }}
                fis.close();
            } else {
                r.createNewFile();
            }
            
            data.addReservationsLL(bll);
            
            if(s.exists()){
                FileInputStream of = new FileInputStream(s);
                byte[] o = new byte[of.available()];
                of.read(o);
                ofrs = new String(o);
            }
        } catch (IOException ioe) {
            log.writeError(ioe.getMessage(), true);
        }
        users = ull;
        data.offers = ofrs;
    }

    /**
     * Writes the internal data out the the parameter files in (rough) CSV format.
     * @param ff films file
     * @param rf reservations file
     * @param uf users file
     */
    public void writeFile(File ff, File rf, File uf) {
        try {
            FileOutputStream fos = new FileOutputStream(ff);
            String fs = data.allFilmsToString();
            if(fs!=null){
                byte[] fb = fs.getBytes();
                fos.write(fb);
            }
            fos.close();
            
            fos = new FileOutputStream(rf);
            Iterator<Booking> bit = data.getBookingIt();
            while(bit.hasNext()){
                Booking b = bit.next();
                String res = b.getFilm().getName() + "," + b.getFilm().getDate() + "," + b.getFilm().getTime() + "," + b.getFilm().getBooked() + "," + b.getSeats() + "," + b.getName() + "\n";
                fos.write(res.getBytes());
            }
            fos.close();
            
            fos = new FileOutputStream(uf);
            synchronized (users) {
                Iterator<String> uit = users.iterator();
                while (uit.hasNext()) {
                    String u = uit.next();
                    String name = u + "\n";
                    fos.write(name.getBytes());
                }
            }
            fos.close();
            
        } catch (IOException ioe) {
            log.writeError(ioe.getMessage(), true);
        }

    }

    /**
     * Constructs a new Session, adds it to the linked list and starts the session
     * @param s A Socket to the client
     * @throws IOException Couldn't add the client to the linked list
     */
    public void addClient(Socket s) throws IOException {
        synchronized (_clients) {
            Session c = new Session(s, this);
            if (!_clients.add(c)) {
                throw new IOException("Could not add client to list.");
            }
            c.start();
        }
    }

    /**
     * Force quits the session
     * @param c the Session to remove
     */
    public synchronized void removeClient(Session c) {
        c.forceQuit();
    }

    /**
     * Is the server shutting down?
     * @return true if it is, false if it's not
     */
    public synchronized boolean  quitting() {
        return _quit;
    }

    /**
     * Set the server to quit (pass "q" in) or force quit (pass "f" in)
     * @param q String indicating quit level
     */
    public synchronized void setQuit(String q) {
        if (q.equals("q")) {
            _quit = true;
        }
        if (q.equals("f")) {
            _quit = true;
            _forcequit = true;
        }
    }

    /**
     * Is the server force quitting?
     * @return true if the server is force quitting, false if not
     */
    public boolean forceQuitting() {
        return _forcequit;
    }

    /**
     * Blocks the server from shutting down until all clients are disconnected.
     */
    public void waitOnClients() {
        log.writeMessage("Waiting for clients to disconnect", false);
        Object[] clients = null;
        synchronized (_clients) {
            clients = _clients.toArray();
            if (clients == null || clients.length == 0) {
                return;
            }
        }
        boolean loop = true;
        while (loop) {
            loop = false;
            for (int x = 0; x < clients.length; x++) {
                Session c = (Session) clients[x];
                if (c.isConnected()) {
                    loop = true;
                }
            }
        }
    }

    /**
     * Closes the connection to all the clients, used for force quitting the server.
     */
    public void closeAllClients() {
        log.writeMessage("Disconnecting all clients", false);
        synchronized (_clients) {
            Iterator<Session> it = _clients.iterator();
            while (it.hasNext()) {
                it.next().forceQuit();
            }
        }
    }
    
    /**
     * Gets the Data instance
     * @return the instance of the Data class
     */
    public Data getData(){
        return this.data;
    }

    /**
     * Adds users to the list if they're new
     * @param user the user's name
     * @return true if user is new, false if already registered
     */
    public boolean addUser(String user) {
        synchronized (users) {
            if (users.contains(user)) {
                return false;
            } else {
                users.add(user);
                return true;
            }
        }
    }
    
    /**
     * Gets the Strings detailing the offers
     * @return each offer as a String in the array
     */
    public String[] getOffers(){
        return this.offers;
    }
}
