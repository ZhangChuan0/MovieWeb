package zhang.jie.api.domain.mapper.resources;

import com.google.gson.Gson;

import java.util.List;

public class CoverOtherClass {
    private String code;
    private List listData;
    private Object data;
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getListData() {
        return listData;
    }

    public void setListData(List listData) {

        this.listData = listData;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
