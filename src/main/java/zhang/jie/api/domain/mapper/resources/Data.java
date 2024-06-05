package zhang.jie.api.domain.mapper.resources;

import com.google.gson.Gson;

import java.util.List;

public class Data {

    private int id;
    private int current;
    private int hitCount;
    private int pages;
    private int searchCount;

    public Data(int id, int current, int hitCount, int pages, int searchCount, int size, int total) {
        this.id = id;
        this.current = current;
        this.hitCount = hitCount;
        this.pages = pages;
        this.searchCount = searchCount;
        this.size = size;
        this.total = total;
    }

    private int size;
    private int total;
    private List<Records> records;

    public List<Records> getRecords() {
        return records;
    }

    public void setRecords(List<Records> records) {
        this.records = records;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
