/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

import java.io.Serializable;

/**
 * The Response class that the server will always send to the client.
 * @author Robert
 */
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;
    private String response;
    private Object[] object;
    private boolean success;
    private String reason;

    /**
     * construct a new response. Sets an error message in this.reason (as a fall back) which should be reset as a success or error message.
     */
    public Response() {
        this.reason = "Something bad happened";
    }

    /**
     * setter for response
     * @param r the response
     */
    public void setResponse(String r) {
        this.response = r;
    }

    /**
     * Setter for success.
     * @param s Set to true if response has no errors, true if an error occured.
     */
    public void setSuccess(boolean s) {
        this.success = s;
    }

    /**
     * getter for response
     * @return the response
     */
    public String getResponse() {
        return this.response;
    }

    /**
     * Setter for an object array. This will be the payload for any response.
     * @param o Any array that can be cast as Object
     */
    public void setResponseObjects(Object[] o) {
        this.object = o;
    }

    /**
     * Getter for the object array. This is the payload from the server.
     * This should be parsed out as what ever objects are expected.
     * Could be of any length and any class type.
     * @return the object array from the server
     */
    public Object[] getResponseObjects() {
        return this.object;
    }

    /**
     * Getter for success. This will be true if the server encountered no errors in running the request.
     * If this is false, check this.reason for an error message.
     * @return true if no errors, false otherwise.
     */
    public boolean getSuccess() {
        return success;
    }

    /**
     * This should be called passing in any non-null String before sending back to the server.
     * If there is no error message, pass a success message
     * @param r a reason why the request was not successful
     */
    public void setReason(String r) {
        this.reason = r;
    }

    /**
     * Get the reason why a request did not succeed. If this.success is true then this should contain a success message.
     * @return the reason why the request did not succeed
     */
    public String getReason() {
        return reason;
    }
}
