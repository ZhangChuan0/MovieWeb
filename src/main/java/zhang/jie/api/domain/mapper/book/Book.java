package zhang.jie.api.domain.mapper.book;

import com.google.gson.Gson;

public class Book {

    private String name;

    private int book_id;

    private Product product;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
