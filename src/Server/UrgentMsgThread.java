/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Robert
 */
class UrgentMsgThread extends Thread {

    private Server server;
    private ServerSocket s;
    private List<Socket> clients;

    public UrgentMsgThread(Server server) {
        this.server = server;
        try {
            s = new ServerSocket(2001);
            s.setSoTimeout(Server.TIMEOUT);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        clients = Collections.synchronizedList(new LinkedList<Socket>());
    }

    @Override
    synchronized public void run() {
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
            clients.add(cl);
        }

    }

    public void send(String msg) {
        synchronized (clients) {
            UMSender m = new UMSender((Socket[]) clients.toArray(), msg);
            m.start();
        }
    }

    class UMSender extends Thread {

        private Socket[] clients;
        private String message;

        public UMSender(Socket[] c, String m) {
            clients = c;
            message = m;
        }

        @Override
        public void run() {
            for (int x = 0; x < clients.length; x++) {
                try {
                    clients[x].getOutputStream().write(message.getBytes());
                } catch (IOException e) {
                    continue;
                }
            }

        }
    }
}