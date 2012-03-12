/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
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
        
        System.out.println("Running");
        while(!server.quitting()){
            
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
    
    public void addClient(Socket s){
        Session c = new Session(s);
        _clients.add(c);
        c.start();
    }

    private boolean quitting() {
        if(_quit){ return true;}
       return false;
    }
    
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
