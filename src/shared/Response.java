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
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private String response;
    private Object[] object;
    private boolean success;
    private String reason;
    
    public void setResponse(String r){ this.response = r; }
    public void setSuccess(boolean s){ this.success = s; }
    public String getResponse() { return this.response;}
    
    public void setResponseObjects(Object[] o){ this.object = o; }
    public Object[] getResponseObjects() { return this.object;}
    public boolean getSuccess(){ return success; }
    
    public void setReason(String r) {this.reason = r;}
    public String getReason() {return reason; }
    
}
