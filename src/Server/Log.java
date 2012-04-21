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
 *A log file writer.
 * @author Robert
 */
public class Log {

    private Calendar now = null;
    private File logfile = null;
    private FileWriter log = null;

    /**
     * Constructs a Log instance, sets up the file named by the date or server.log if appending
     * @param append boolean if true, then data will be written to the end of the file rather than the beginning
     */
    public Log(boolean append) {
        try {
            if (append) {
                logfile = new File("logs" + File.separator + "server.log");
            } else {
                logfile = new File("logs" + File.separator + getTime() + ".log");
            }
            if (!logfile.getParentFile().exists()) {
                logfile.getParentFile().mkdirs();
            }
            log = new FileWriter(logfile, append);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Constructs a Log instance, sets up the file named by the date.
     */
    public Log() {
        try {
            logfile = new File("logs" + File.separator + getTime() + ".log");
            if (!logfile.getParentFile().exists()) {
                logfile.getParentFile().mkdirs();
            }
            log = new FileWriter(logfile);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Gets the date and time formatted as "yyyy-MM-dd HH:mm:ss" to be used within the instance.
     * @return the formatted date/time
     */
    private String getTime() {
        now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String time = sdf.format(now.getTime());
        return time;
    }

    /**
     * Writes a message to the file (and optionally to stdout) in the format "<i>TIME</i>:: <i>MESSAGE</i>"
     * @param msg the message to be written
     * @param stdout use true if the Message should be mirrored to stdout, false for file only.
     * @return true if file was successfully written to, false otherwise
     */
    public boolean writeMessage(String msg, boolean stdout) {
        try {
            String message = getTime() + ":: " + msg + Server.endLine;
            if (stdout) {
                System.out.print(message);
            }
            log.write(message);
            log.flush();
            return true;
        } catch (IOException e) {
            System.err.println(getTime() + ":: Could not write message: " + msg);
            return false;
        }
    }

    /**
     * Writes an error to the file (and optionally to stderr) in the format "<i>TIME</i>:: ERROR: <i>MESSAGE</i>"
     * @param err the error message
     * @param stdout use true if the Message should be mirrored to stderr, false for file only.
     * @return true if file was successfully written to, false otherwise
     */
    public boolean writeError(String err, boolean stdout) {
        System.err.println(getTime() + ":: ERROR:" + err);
        return writeMessage("ERROR: " + err, false);
    }

    /**
     * Writes an event to the file (and optionally to stdout) in the format "<i>TIME</i>:: EVENT: <i>MESSAGE</i>"
     * @param e the event message
     * @param stdout use true if the Message should be mirrored to stdout, false for file only.
     * @return true if file was successfully written to, false otherwise
     */
    public boolean writeEvent(String e, boolean stdout) {
        return writeMessage("EVENT: " + e, stdout);
    }
}
