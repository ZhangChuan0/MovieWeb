package zhang.jie.api.domain.mapper;


import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.User;

import java.util.List;

@Mapper
public interface UserMapper {

//    查询全部
    @Select("select * from user") //注解开发 全部
    public List<User> getAll();

// 查询内容数量
    @Select("select count(id) from user;")
    public int getCount();


//    根据指定内容查询
    @Select("select * from user where id = #{id}")
    public User findById(Integer id);

//增加元素
    @Insert("insert into user (code,age) values (#{code},#{age});")
    public int insertUser(User user);

//更新
    @Update("update user set code = #{code} where age = #{age}")
    public int updateUser(User user);

//删除
    @Delete("delete from user where id > #{num}")
    public int deleteUser(int num);

}
