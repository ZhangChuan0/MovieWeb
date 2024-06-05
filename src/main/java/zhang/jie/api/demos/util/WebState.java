package zhang.jie.api.demos.util;

public class WebState {

    private String message;
    private boolean success;
    private int state;
    private String content;

    public WebState(String message, boolean success, int state, String content) {
        this.message = message;
        this.success = success;
        this.state = state;
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WebState() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {


       return  "{\"message\":\"" +
               message + "\", \"success\":" +
               success + ", \"sstate\":\"" +
               state + "\", \"content\": { \"access_token\":"+"\""+
                                content +"\"}"+
               "}";


    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;

    }
}
