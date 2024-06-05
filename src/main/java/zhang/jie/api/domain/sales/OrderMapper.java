package zhang.jie.api.domain.sales;

import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.mapper.courses.Course_info;
import zhang.jie.api.domain.member.Member;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/5/4$.
 */
@Mapper
public interface OrderMapper {

    @Select("select * from `order`")
    List<Order> getAllOrder();

    @Select("select * from members where name = #{name} and password = #{password}")
    Member getUser(Member member);

    //将指定的余额减少
    @Update("UPDATE members SET balance = #{balance} WHERE name = #{name} and" +
            " password = #{password}")
    int updateBalance(Member member);


//    指定商品的库存减少

// tool
    @Update("UPDATE productofshopping SET stock = #{stock} WHERE id = #{id}")
    int updateStock(@Param("id") int id, @Param("stock") int stock);
//food
    @Update("UPDATE user_use SET password = #{password} WHERE id = #{id}")
    int updatePassword(@Param("id") int id, @Param("password") String password);
//    获取指定商品的库存
    @Select("select stock from productofshopping where id = #{id}")  //tool
    int getStock(int id);

    @Select("select password from user_use where id = #{id}")  //food
    String getPassword(int id);



//    生成订单 食物
@Insert("insert into `order`(user_id, payMethod, totalAmount, " +
        "remark, status, item_name, " +
        "item_total, item_type) VALUES " +
        "(#{userId},#{payMethod},#{totalAmount},#{remark},#{status},#{item_name},#{item_total},#{item_type})")
    int insertOrder(Order order);


//生成订单 电影
    @Insert("insert into `order`(user_id, payMethod, totalAmount, " +
            "remark, status, item_name, " +
            "item_total, item_type,price_type) VALUES " +
            "(#{userId},#{payMethod},#{totalAmount},#{remark}," +
            "#{status},#{item_name},#{item_total},#{item_type},#{price_type})")
    int insertOrderMovie(Order order);


//通过id查到电影名字
    @Select("select courseName from courses_large where id = #{id}")
    String getCourseName(int id);



    //通过id查到座位信息
    @Select("<script>" +
            "SELECT row_column " +
            "FROM seat " +
            "WHERE id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>")
    List<String> getSeat(@Param("ids") List<Integer> ids);


//    得到用户的id

    @Select("select id from members where name = #{name} and password = #{password}")
    int getId(Member member);


//    得到电影票房
    @Select("select courseName,activityStock from courses_large")
    List<Course_info> getAllName();

//    得到订单总额和名字
    @Select("select item_name,totalAmount from  `order`")
    List<Order> getOrder();

//更新库存
    @Update("update courses_large set activityStock = #{activityStock} where courseName = #{courseName}")
    int updateCourse_large(Course_info course_info);



//    退票
    @Update("update `order` set status = '已退款' where id = #{id}")
    int updateOrder(int id);



}
