package zhang.jie.api.domain.mapper.resources.imp;


import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.mapper.resources.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select * from role")
    List<Role> getAll();

//    total roles
    @Select("select count(*) from role")
    int getTotalRoles();

    @Select("select * from recodes where id  =  #{id}")
    List<Role> getRoleById(int id);

    @Select("select * from role where name like #{name}") //模糊查询
    List<Role> getRoleByName(Role role);

//    返回分页数量
    @Select("select * from role limit #{pagesize} offset #{num}")
    List<Role> getRoleByPage(@Param("pagesize") int pagesize, @Param("num") int num);

//    保存role信息
    @Insert("insert into role(name,description) values(#{name},#{description})")
    int saveRole(Role role);

//    根据id更新role数据
    @Update("update role set name=#{name},description=#{description} where id=#{id}")
    int updateRole(Role role);

//    删除role 根据id信息
    @Delete("delete from role where id=#{id}")
    int deleteRole(int id);



}
