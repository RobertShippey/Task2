/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

/**
 *
 * @author Robert
 */
public class Response {
    private String response;
    private Object object;
    
    public void setResponse(String r){ this.response = r; }
    public String getResponse() { return this.response;}
    
    public void setResponseObject(Object o){ this.object = o; }
    public Object getResponseObject() { return this.object;}
    
}
