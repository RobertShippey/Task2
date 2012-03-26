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
import java.util.Iterator;
import java.util.LinkedList;

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

    public static void main(String[] args) {
        Server server = new Server();
        File films = new File("films.txt");
        File reservations = new File("reservations.txt");
        server.readFile(films, reservations);

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

        server.writeFile(films, reservations);

        System.out.println("Quit");
        System.exit(0);
    }

    public Server() {
        _quit = false;
        _forcequit = false;
        data = null;
    }

    public void readFile(File f, File r) {
       /* if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                byte[] t = new byte[fis.available()];
                fis.read(t);
                String text = new String(t);
                String[] records = text.split("\n");
                for (int x = 0; x < records.length; x++) {
                    String[] row = records[x].split(",");
                    Booking b = new Booking(row[0]);
                    //stuff
                    b.setName(row[4]);
                   // _showings.find(row[0]).add(b);
                }

            } catch (IOException ioe) {
            }
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(" ".getBytes());
                fos.flush();
                fos.close();
            } catch (IOException ioe) {
            }

        } */
    }

    public void writeFile(File f, File r) {
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
        _clients.add(c);
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

}
