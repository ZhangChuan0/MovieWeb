package zhang.jie.api.domain;

import com.google.gson.Gson;

import java.util.List;

public class CoverClass {
    private String code;
    private List data;

    @Override
    public String toString() {
        
       return new Gson().toJson(this);
       
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
