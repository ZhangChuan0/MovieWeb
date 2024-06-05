package zhang.jie.api.domain.showdata;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/5/9$.
 */
public class ShowData {
    private String name;
    private List<Integer> value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getValue() {
        return value;
    }

    public void setValue(List<Integer> value) {
        this.value = value;
    }
    public void addValue(Integer value){
        this.value.add(value);

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
