/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import shared.Booking;
import shared.Film;

/**
 *
 * @author Robert
 */
public class Server {

    //private LinkedList<Showing> _showings;
    private Data data;
    private LinkedList<Session> _clients;
    private boolean _quit;
    private boolean _forcequit;
    private LinkedList<String> users;

    public static void main(String[] args) {
        Server server = new Server();
        File films = new File("films.ser");
        File reservations = new File("reservations.txt");
        File users = new File("users.txt");
        server.readFile(films, reservations, users);

        CmdThread cmd = new CmdThread(server);
        cmd.start();

        UrgentMsgThread urgent = new UrgentMsgThread(server);
        urgent.start();

        ServerSocket s = null;
        try {
            s = new ServerSocket(2000);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("Running");
        while (!server.quitting()) {
            Socket cl = null;
            try {
                cl = s.accept();
                server.addClient(cl);
            } catch (IOException e) {
                continue;
            }


        }
        if (server.forceQuitting()) {
            server.closeAllClients();
        } else {
            server.waitOnClients();
        }

        server.writeFile(films, reservations, users);

        System.out.println("Quit");
        System.exit(0);
    }

    public Server() {
        _quit = false;
        _forcequit = false;
        data = null;
    }

    public void readFile(File f, File r, File u) {
        LinkedList<Booking> bll = new LinkedList<Booking>();
        LinkedList<Film> fll = new LinkedList<Film>();
        LinkedList<String> ull = new LinkedList<String>();
        try {

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
                for (int x = 0; x < film.length; x++) {
                    String[] items = film[x].split(",");
                    Film flm = new Film(items[0],items[1],Integer.parseInt(items[2]),Integer.parseInt(items[3]));
                    fll.add(flm);
                }
                ff.close();
            } else {
                f.createNewFile();
            }

            if (r.exists()) {
                FileInputStream fis = new FileInputStream(r);
                byte[] t = new byte[fis.available()];
                fis.read(t);
                String text = new String(t);
                String[] records = text.split("\n");
                for (int x = 0; x < records.length; x++) {
                    String[] row = records[x].split(",");
                    Booking b = new Booking(row[4], data.findFilm(row[0], row[1]), Integer.parseInt(row[3]));
                    bll.add(b);
                }
                fis.close();
            } else {
                r.createNewFile();
            }
        } catch (IOException ioe) {
        }
        data = new Data(fll, bll);
        users = ull;
    }

    public void writeFile(File f, File r, File u) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write("some string".getBytes());
        } catch (IOException ioe) {
        }

    }

    /***
     * Constructs Session and adds to linked list then starts Thread.
     * 
     * @param s Received from clients request to connect.
     */
    public void addClient(Socket s) throws IOException {
        Session c = new Session(s, this);
       if(!_clients.add(c)){
           throw new IOException("Could not add client to list.");
       }
        c.start();
    }

    public void removeClient(Session c) {
        c.forceQuit();
        boolean r = _clients.remove(c);

    }

    /***
     * 
     * @return 
     */
    public boolean quitting() {
        if (_quit) {
            return true;
        }
        return false;
    }

    /***
     * 
     * @param q 
     * @return 
     */
    public void setQuit(String q) {
        if (q.equals("q")) {
            _quit = true;
        }
        if (q.equals("f")) {
            _quit = true;
            _forcequit = true;
        }
    }

    public boolean forceQuitting() {
        return _forcequit;
    }

    public void waitOnClients() {
        boolean loop = true;
        while (loop) {
            Iterator<Session> it = _clients.iterator();
            loop = false;
            while (it.hasNext()) {
                if (it.next().isConnected()) {
                    loop = true;
                }
            }
        }
    }

    public void closeAllClients() {
        Iterator<Session> it = _clients.iterator();
        while (it.hasNext()) {
            it.next().forceQuit();
        }
    }
    
    public Data getData(){
        return this.data;
    }

    public boolean addUser(String user){
        if(users.contains(user)){
            return false;
        } else {
            users.add(user);
            return true;
        }
    
    }
}
