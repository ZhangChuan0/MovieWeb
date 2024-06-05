package zhang.jie.api.domain;
import com.google.gson.Gson;

public class WebToken {
    private int state;
    private String content;

    private String success;
    private int id;

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {

        Gson gson = new Gson();

        return gson.toJson(this);
    }
}

