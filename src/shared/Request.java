/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

/**
 *
 * @author Robert
 */
public class Request {
    public static final String LOG_OFF = "LOGOFF";
    private String request;
    public String getRequest(){ return this.request; }
    public void setRequest(String r) { this.request = r;}
    
}
