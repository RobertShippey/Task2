/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            log = new FileWriter(logfile);
        } catch (IOException ex) {
            return;
        }
    }

    private String getTime() {
        now = Calendar.getInstance();
        String time = now.get(Calendar.DAY_OF_MONTH) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.YEAR) + "-" + now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
        return time;
    }

    public boolean writeMessage(String msg, boolean stdout) {
        try {
            String message = getTime() + ": " + msg + "\n";
            if(stdout){ System.out.print(message); }
            log.write(message);
            log.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeError(String err, boolean stdout) {
        return writeMessage("ERROR: " + err, stdout);
    }

    public boolean writeEvent(String e, boolean stdout) {
        return writeMessage("EVENT: " + e, stdout);
    }
}
