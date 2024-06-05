package zhang.jie.api.domain.mapper.book;

import com.google.gson.Gson;

public class Product {
    private int book_id;
    private String name;

    private int people;

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getBook_id() {
        return book_id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
