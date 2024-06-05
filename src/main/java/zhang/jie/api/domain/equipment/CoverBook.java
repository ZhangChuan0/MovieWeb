package zhang.jie.api.domain.equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zhang.jie.api.domain.mapper.BookMapper;
import zhang.jie.api.domain.mapper.book.Book;
import zhang.jie.api.domain.mapper.book.Product;

import java.util.List;

@Component
public class CoverBook {

    @Autowired
    BookMapper bookMapper;

    public List<Book> getAllBook() {

        List<Book> all = bookMapper.getAll();
        List<Product> productAll = bookMapper.getProductAll();
        System.out.println(all);


        for (Book book: all){
            for (Product product: productAll){
                if (book.getBook_id() == product.getBook_id()){
                    book.setProduct(product);
                }
            }
        }

        all.removeIf(book -> book.getProduct() == null);

        return all;
    }
}
