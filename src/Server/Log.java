/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Robert
 */
public class Log {

    private Calendar now = null;
    private File logfile = null;
    private FileWriter log = null;

    public Log() {
        try {
            logfile = new File("logs/" + getTime() + ".log");
            if(!logfile.getParentFile().exists()) { logfile.getParentFile().mkdir(); }
            log = new FileWriter(logfile);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return;
        }
    }

    private String getTime() {
        now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(now.getTime());
        return time;
    }

    public boolean writeMessage(String msg, boolean stdout) {
        try {
            String message = getTime() + ":: " + msg + "\n";
            if(stdout){ System.out.print(message); }
            log.write(message);
            log.flush();
            return true;
        } catch (IOException e) {
            System.err.print(getTime() + ": Could not write message: " + msg);
            return false;
        }
    }

    public boolean writeError(String err, boolean stdout) {
        System.err.println(getTime() + ":: " + err);
        return writeMessage("ERROR: " + err, false);
    }

    public boolean writeEvent(String e, boolean stdout) {
        return writeMessage("EVENT: " + e, stdout);
    }
}
