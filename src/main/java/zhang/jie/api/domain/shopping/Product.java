package zhang.jie.api.domain.shopping;

import com.google.gson.Gson;

/**
 * Created by zhouhuanping$ on 2024/4/29$.
 *
 */
public class Product {
    private int id;
    private String name;
    private String price;
    private String iamge_url;
    private int stock;
    private int saveTime;
    private String description ;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIamge_url() {
        return iamge_url;
    }

    public void setIamge_url(String iamge_url) {
        this.iamge_url = iamge_url;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(int saveTime) {
        this.saveTime = saveTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
