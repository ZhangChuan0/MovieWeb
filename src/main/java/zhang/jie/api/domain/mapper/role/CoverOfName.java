package zhang.jie.api.domain.mapper.role;

import com.google.gson.Gson;

import java.util.List;

public class CoverOfName {

    private int id;

    public int getId() {
        return id;
    }

    public CoverOfName(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<Name> records;
    private String name;
    private boolean selected = true;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<Name> getRecords() {
        return records;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void setRecords(List<Name> records) {
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
