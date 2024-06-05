package zhang.jie.api.domain;

import com.google.gson.Gson;

public class Menu {
    private int id;
    private String name;
    private double size;

    public Menu() {
    }

    private String icon;

    @Override
    public String toString() {

        Gson gson = new Gson(); //转换成json
        return gson.toJson(this);
    }
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    private String order;

}
