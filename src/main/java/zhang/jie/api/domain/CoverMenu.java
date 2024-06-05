package zhang.jie.api.domain;

import com.google.gson.Gson;
import java.util.List;

public class CoverMenu {
    private String code;
    private List<Menu> data;

    public CoverMenu() {
    }

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

    public List<Menu> getData() {
        return data;
    }

    public void setData(List<Menu> data) {
        this.data = data;
    }

}