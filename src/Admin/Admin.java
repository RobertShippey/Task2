/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Server.Server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import shared.Request;

/**
 * The interface class.
 * @author Robert
 */
public class Admin extends Thread {

    private File filmFile;
    private LinkedList<String> films;
    private Socket s;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private BufferedReader cmd;

    /**
     * Sets up the file and command line BufferedReader
     */
    public Admin() {
        filmFile = new File("data" + File.separator + "films.txt");
        cmd = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Waits for the server to shut down, then presents the user with the menu and accepts input to carry out each task.
     */
    @Override
    public void run() {
        System.out.println("Waiting for the server to shutdown...");
        waitForServer();
        System.out.println("Server is shut down. Safe to edit text files.");
        readData();

        while (true) {

            System.out.println("");
            System.out.println("0) Add a film");
            System.out.println("1) Remove a film");
            System.out.println("2) View all films");
            System.out.println("Type quit to exit");

            String command = "quit";
            try {
                command = cmd.readLine();
            } catch (IOException ioe) {
            }


            if (command.equals("quit")) {
                break;
            }

            int option = -1;
            try {
                option = Integer.parseInt(command);
            } catch (Exception e) {
                System.err.println("Command not recognised. Try again.");
                continue;
            }
            
            int i;
            
            switch (option) {
                case 0:
                    //<editor-fold desc="Add a film">
                    String film = null;
                    try {
                        System.out.println("Enter the film name:");
                        String filmName = cmd.readLine();
                        System.out.println("Enter the film date: (DD-MM-YYYY");
                        String filmDate = cmd.readLine();
                        System.out.println("Enter the film time: (HH:MM)");
                        String filmTime = cmd.readLine();
                        System.out.println("Enter the films capacity:");
                        String filmCapacity = cmd.readLine();

                        film = filmName + "," + filmDate + "," + filmTime + "," + filmCapacity + "," + "0";
                    } catch (Exception e) {
                        System.err.println("Something very bad happened. Your data wasn't saved.");
                        System.err.println("Please try again.");
                        continue;
                    }
                    if (film != null) {
                        films.add(film);
                    }
                    System.out.println("Item sucessfully added!");

                    break;
                    //</editor-fold>
                case 1:
                    //<editor-fold desc="Remove a film">
                    System.out.println("Please select a film to delete");
                    Iterator<String> it = films.iterator();
                    i = 0;
                    while (it.hasNext()) {
                        System.out.println((i++) + ") " + it.next());
                    }
                    try {
                        int item = Integer.parseInt(cmd.readLine());
                        films.remove(item);
                    } catch (Exception e) {
                        System.err.println("Something very bad happened. Your data wasn't saved.");
                        System.err.println("Please try again.");
                    }
                    System.out.println("Item successfully removed!");

                    break;
                    //</editor-fold>
                case 2:
                    System.out.println("All films: ");
                    Iterator<String> itt = films.iterator();
                    i = 0;
                    while (itt.hasNext()) {
                        System.out.println((i++) + ") " + itt.next());
                    }
                    break;
                default:
                    System.err.println("Command not recognised. Try again.");
                    continue;
            }
        }
        writeData();
    }

    /**
     * Hooks up a socket to the server, if it fails then the server is down and it will break, otherwise it will carry on looping.
     */
    private void waitForServer() {
        while (true) {
            try {
                s = new Socket(Editor.server, 2000);
                oos = new ObjectOutputStream(s.getOutputStream());
                ois = new ObjectInputStream(s.getInputStream());

                oos.writeObject("ADMINISTRATOR");
                ois.readObject();

                oos.writeObject(new Request(Request.LOG_OFF));

                s.close();
            } catch (Exception e) {
                break;
            }
        }
    }

    
    /**
     * Reads the data from the file into the linked list
     */
    private void readData() {
        films = new LinkedList<String>();
        try {
            if (filmFile.exists()) {
                FileInputStream ff = new FileInputStream(filmFile);
                byte[] fileBytes = new byte[ff.available()];
                ff.read(fileBytes);
                String filmsString = new String(fileBytes);
                String[] film = filmsString.split(Server.endLine);
                if (!film[0].equals("")) {
                    films.addAll(Arrays.asList(film));
                } else {
                    films = null;
                }
            }
        } catch (IOException ioe) {
            films = null;
            System.err.println("Could not read data from data/films.txt");
        }
    }

    /**
     * Iterates over the linked list and writes the data out to the file.
     */
    private void writeData() {
        try {
            filmFile.getParentFile().mkdirs();
            filmFile.createNewFile();
        } catch (Exception e) {
        }
        try {
            FileOutputStream pw = new FileOutputStream(filmFile);
            Iterator<String> it = films.iterator();
            while (it.hasNext()) {
                pw.write((it.next() + "\r\n").getBytes());
            }
            pw.flush();
        } catch (Exception e) {
            System.err.println("Could not save data.");
        }
    }
}
