package zhang.jie.api.domain.mapper.resources;

import com.google.gson.Gson;

import java.util.List;

public class DataOfAny {

    private int id;
    private int current;
    private int hitCount;
    private int pages;
    private int searchCount;
    private List records;
    public DataOfAny() {
    }

    public DataOfAny(int id, int current, int hitCount, int pages, int searchCount) {
        this.id = id;
        this.current = current;
        this.hitCount = hitCount;
        this.pages = pages;
        this.searchCount = searchCount;
    }



    @Override
    public String toString() {
        return  new Gson().toJson(this);
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

    public List<Object> getRecords() {
        return records;
    }

    public void setRecords(List records) {
        this.records = records;
    }
}
