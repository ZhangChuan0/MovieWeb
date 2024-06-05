package zhang.jie.api.domain.member;

import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.sales.Order;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/5/1$.
 */

@Mapper
public interface MemberMapper {

    @Select("select * from members")
    List<Member> getAll();

//    姓名和phone联合查询

    @Select("select * from members where name = #{name} and phone = #{phone}")
    List<Member> getMemberByNameAndPhone(Member member);


//    插入
    @Insert("insert into members(name,phone,password,gender) " +
            "values(#{name},#{phone},#{password},#{gender})")
    int insertMember(Member member);

//    更新
    @Insert("update members set name = #{name},phone = #{phone},password = #{password}," +
            "gender = #{gender} where id = #{id}")
    int updateMember(Member member);


//    充值渠道
    @Update("UPDATE members \n" +
            "SET balance = CAST(balance AS SIGNED) + #{amount}\n" +
            "WHERE id = #{memberId};")
    int recharge(@Param("memberId") int memberId, @Param("amount") int amount);

//    查询余额

    @Select("select balance from members where id = #{id}")
    Member getBalance(Member member);

//    更改密码   重置密码到默认值

    @Update("update members set password = #{password} where name = #{name} and phone=#{phone}")
    int updatePassword(Member member);


//    查询订单
    //1.指定用户的记录总数量
  @Select("select count(*) from `order` where user_id = #{user_id}")
    int getOrderCount(@Param("user_id") int user_id);

//  分页
  @Select("select * from `order` where user_id = #{user_id} limit #{pageSize},7")
    List<Order> getOrderByPage(@Param("user_id") int user_id, @Param("pageSize") int pageSize);


//  模糊查询 item

    @Select("select * from `order` where user_id = #{user_id} and `order`.item_name like #{item} limit #{pageSize},7")
    List<Order> getOrderByItem(@Param("user_id") int user_id, @Param("item") String item, @Param("pageSize") int pageSize);

//    模糊查询的总数
    @Select("select count(*) from `order` where user_id = #{user_id} and `order`.item_name like #{item}")
    int getOrderCountByItem(@Param("user_id") int user_id, @Param("item") String item);

}

