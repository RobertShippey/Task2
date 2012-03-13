/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import shared.Showing;

/**
 *
 * @author Robert
 */
public class Server {
    private List<Showing> _showings;
    private List<Session> _clients;
    private boolean _quit;
    private boolean _forcequit;
    
    
    public static void main (String[] args){
        Server server = new Server();
        File data = new File("data.txt");
        server.readFile(data);
        
        CmdThread cmd = new CmdThread(server);
        cmd.start();
        
        UrgentMsgThread urgent = new UrgentMsgThread(server);
        urgent.start();
        
        ServerSocket s = null;
        try{
        s = new ServerSocket(2000);}
        catch(IOException e){
            System.err.println(e.getMessage());
            System.exit(0);
        }
        
        System.out.println("Running");
        while(!server.quitting()){
            Socket cl = null;
            try{
            cl = s.accept();}
            catch (IOException e){ continue; }
            server.addClient(cl);
            
        }
        System.out.println("Quit");
        System.exit(0);
    }
    
    public Server(){
        _quit = false;
        _forcequit = false;
    }
    
    public void readFile(File f){
        //loop
        //get data for booking
        //construct booking
        //_reservations.add();
    }
    public void writeFile(File f){
        //loop over _reservations
        //write data to f
    }
    
    /***
     * Constructs Session and adds to linked list then starts Thread.
     * 
     * @param s Received from clients request to connect.
     */
    public void addClient(Socket s){
        Session c = new Session(s);
        _clients.add(c);
        c.start();
    }
    /***
     * 
     * @return 
     */
    private boolean quitting() {
        if(_quit){ return true;}
       return false;
    }
    /***
     * 
     * @param q 
     * @return 
     */
    public void setQuit(String q)
    {
        if(q.equals("q")){
            _quit = true;
        }
        if(q.equals("f")){
            _quit = true;
            _forcequit = true; 
        }
    }
}
