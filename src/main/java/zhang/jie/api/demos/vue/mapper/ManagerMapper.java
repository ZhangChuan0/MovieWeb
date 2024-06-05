package zhang.jie.api.demos.vue.mapper;

import org.apache.ibatis.annotations.*;
import zhang.jie.api.demos.vue.loggin.Manager;
import zhang.jie.api.domain.users.UserRole;

import java.util.ArrayList;

/**
 * Created by zhouhuanping$ on 2024/4/24$.
 */

@Mapper
public interface ManagerMapper {


//    得到全部的职员信息
    @Select("select * from manager")
    ArrayList<Manager> getAllStaff();

    @Select("select * from `position`")
    ArrayList<UserRole> getAllPosition();

//   更新权限

    @Update("update manager set role = #{role} where id = #{id}")
    int updateRole(@Param("role") String role, @Param("id") int id);

//    模糊查询

    @Select("<script>"
            + "SELECT * FROM manager WHERE 1=1"
            + "<if test='name != null and name != &quot;&quot;'>"
            + " AND name LIKE CONCAT('%', #{name}, '%')"
            + "</if>"
            + "<if test='position != null and position.size() > 0'>"
            + " AND position IN "
            + "<foreach collection='position' item='positionItem' open='(' separator=',' close=')'>"
            + "#{positionItem}"
            + "</foreach>"
            + "</if>"
            + "</script>")
    ArrayList<Manager> getByBlur(@Param("name") String name,@Param("position") ArrayList<String> position);


//    保存manager
    @Insert("insert into manager(name, phone, password, portrait, gender, data_of_birth, position, salary_info, note) " +
            "values(#{name}, #{phone}, #{password}, #{portrait}, #{gender}, #{data_of_birth}, #{position}, #{salary_info}, #{note})")
    int saveManager(Manager manager);



//    更新manager
@Update("update manager set " +
        "name = #{name}, " +
        "phone = #{phone}, " +
        "password = #{password}, " +
        "portrait = #{portrait}, " +
        "gender = #{gender}, " +
        "data_of_birth = #{data_of_birth}, " +
        "position = #{position}, " +
        "salary_info = #{salary_info}, " +
        "note = #{note} " +
        "where id = #{id}")
int updateManagerById(Manager manager);

//编辑根据ID找员工
    @Select("select * from manager where id = #{id};")
    Manager getManagerById(@Param("id") int id);

//    移除员工
    @Delete("delete from manager where id = #{id}")
    int deleteManagerById(@Param("id") int id);


//    登录信息
    @Select("select * from manager where phone = #{phone} and password = #{password} and role = #{role}")
    Manager login(Manager manager);



}
