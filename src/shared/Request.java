/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;

/**
 *
 * @author Robert
 */
public class Request implements Serializable {
    public static final String LOG_OFF = "LOGOFF";
    private static final long serialVersionUID = 1L;
    private String request;
    public String getRequest(){ return this.request; }
    public void setRequest(String r) { this.request = r;}
    public Request(String r) {this.request = r;}
    
}
