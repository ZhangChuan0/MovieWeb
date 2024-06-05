package zhang.jie.api.domain.users.imp;

import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.users.UserRole;
import zhang.jie.api.domain.users.User_use;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface User_userMapper {

    @Select("select * from user_use limit #{pageSize} offset #{num}") //pageSize 限制返回结果数量，num标注从什么位置开始返回
    ArrayList<User_use> getAllUserPages(@Param("pageSize") int pageSize, @Param("num") int num);

    @Select("select count(id) from user_use")
    int getAllUserCount();



    @Update("update user_use set status= 'ENABLE' where id=#{id}")
    int changeEnable(@Param("id") int id);

    @Update("update user_use set status= 'DISABLE' where id=#{id}")
    int changeDisable(@Param("id") int id);

//    电话号码 模糊查询
    @Select("SELECT * FROM user_use where phone LIKE #{search} limit #{pageSize} offset #{num}")
    ArrayList<User_use> searchByPhone(@Param("search") String  search,@Param("pageSize") int pageSize, @Param("num") int num);


//    电话某个电话号码的总页数
    @Select("select count(*) from user_use where phone like #{search}")
    int getPhonePage(@Param("search") String phone);

//    查询用户对应的role信息
    @Select("select * from user_role where id = #{id}")
    ArrayList<UserRole> getRoleById(@Param("id") int id);


//    更新角色分配信息
@Update("<script>"
        + "UPDATE user_role "
        + "SET hasPermission = CASE "
        + "<foreach item='index'  collection='indexList' separator=' '>"
        + "WHEN `index` = #{index} THEN 'True' "
        + "</foreach>"
        + "ELSE 'False' END "
        + "WHERE id = #{id}"
        + "</script>")
int updateUserRole(@Param("indexList") List<Integer> indexList, @Param("id") int id);


//新建保存

    @Insert("insert into user_use( name, password, phone, portrait, regIp, status, createTime) " +
            "VALUES(#{name},#{password},#{phone},#{portrait},#{regIp},#{status},#{createTime}) ")
    int addUser(User_use user);


//    更新根据id

    @Update("update user_use set name = #{name}, password = #{password}," +
            " phone = #{phone}, portrait = #{portrait}, " +
            "regIp = #{regIp}, status = #{status}, " +
            "createTime = #{createTime} where id = #{id}")
    int updateUser(User_use user);

//    得到某一个id对应的user
    @Select("select * FROM user_use where id = #{id}")
    User_use getOneUser(@Param("id") int id);


//    移除
    @Delete("delete from user_use where id = #{id}")
    int removeUser(@Param("id") int id);
}
