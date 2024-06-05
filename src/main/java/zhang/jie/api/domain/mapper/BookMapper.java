package zhang.jie.api.domain.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhang.jie.api.domain.mapper.book.Book;
import zhang.jie.api.domain.mapper.book.Product;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("SELECT * FROM book;")
    List<Book> getAll();

    @Select("select * from product;")
    List<Product> getProductAll();
}
