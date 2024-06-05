package zhang.jie.api.domain.shopping;

import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.mapper.courses.Course_info;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/4/29$.
 */

@Mapper
public interface ProductMapper {

    @Select("select * from productofshopping")
    List<Product> getAll();


//    模糊查询名字

    @Select("select * from productofshopping where name like #{name}")
    List<Product> getByName(String name);


//    得到所有电影名
    @Select("select courseName from courses_large")
    List<Course_info> getAllCourseName();

//根据id查到商品信息
    @Select("select * from productofshopping where id=#{id}")
    Product getById(int id);

    //    保存商品
    @Insert("insert into productofshopping( name, price, iamge_url, stock, saveTime, description, status) values(" +
            "#{name},#{price},#{iamge_url},#{stock},#{saveTime},#{description},#{status})")
    int saveProduct(Product product);


//    更新
    @Update("update productofshopping set name=#{name},price=#{price}," +
            "iamge_url=#{iamge_url},stock=#{stock},saveTime=#{saveTime}" +
            ",description=#{description},status=#{status} where id=#{id} ")
    int updateProduct(Product product);

//    删除
    @Delete("delete from productofshopping where id=#{id}")
    int deleteProduct(int id);
}
