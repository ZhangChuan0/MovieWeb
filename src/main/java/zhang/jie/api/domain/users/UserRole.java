package zhang.jie.api.domain.users;

import com.google.gson.Gson;

public class UserRole {
    private int id;
    private String name;
    private int index;
    private String hasPermission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(String hasPermission) {
        this.hasPermission = hasPermission;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
