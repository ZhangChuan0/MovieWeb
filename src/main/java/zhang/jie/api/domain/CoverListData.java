package zhang.jie.api.domain;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CoverListData {
    private String code = "000000";
    private List records ;

    public CoverListData() {
        this.records = new ArrayList<>();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List getRecords() {
        return records;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setRecords(List records) {
        this.records = records;
    }

    public void addAll(List records){

        this.records.addAll(records);
    }

    public void addOne(Object data){
        this.records.add(data);

    }
}
