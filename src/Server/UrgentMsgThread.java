/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The thread that deals with urgent messages.
 * @author Robert
 */
public class UrgentMsgThread extends Thread {

    private Server server;
    private ServerSocket s;
    private List<Socket> clients;

    /**
     * constructs a new urgent message thread ready to be started
     * @param server the server's instance
     */
    public UrgentMsgThread(Server server) {
        this.setName("URGNT Thread");
        this.server = server;
        try {
            s = new ServerSocket(2001);
            s.setSoTimeout(Server.TIMEOUT);
        } catch (IOException e) {
            server.log.writeError("Urgent Messages: " + e.getMessage(), true);
        }
        clients = Collections.synchronizedList(new LinkedList<Socket>());
    }

    /**
     * Gets new connections
     */
    @Override
    public void run() {
        if (s == null) {
            return;
        }
        while (!server.quitting()) {
            Socket cl = null;
            try {
                cl = s.accept();
            } catch (IOException e) {
                continue;
            }
            synchronized(clients){
            clients.add(cl);}
        }
    }

    /**
     * Send an urgent message to all clients.
     * @param msg message to send to all clients
     */
    public synchronized void send(String msg) {
            Object[] socs = null;
            try{
            socs = clients.toArray();
            } catch (ClassCastException cce){
                server.log.writeError("Urgent Messages: " + cce.getMessage(), true);
            }
            UMSender m = new UMSender(socs, msg);
            m.start();
        
    }

    /**
     * Urgent message sender thread
     */
    class UMSender extends Thread {

        private Object[] clients;
        private String message;

        public UMSender(Object[] c, String m) {
            this.setName("UM Sending: " + m);
            clients = c;
            message = m;
        }

        @Override
        public void run() {
            if(clients == null){ return; }
            for (int x = 0; x < clients.length; x++) {
                Socket s = (Socket) clients[x];
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                    bw.write(message);
                    bw.newLine();
                    bw.flush();
                } catch (IOException e) {
                    continue;
                }
            }

        }
    }
}