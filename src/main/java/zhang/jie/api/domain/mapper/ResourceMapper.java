package zhang.jie.api.domain.mapper;


import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.mapper.resource.Resource;

import java.util.List;

@Mapper
public interface ResourceMapper {

    @Select("select * from resource")
    List<Resource> getAll();

    @Select("select * from resource where id = #{id}")
    List<Resource> getById(int id);


    @Insert("INSERT INTO resource (name, `order`) VALUES (#{name}, #{order})")
    void saveOrEdit(@Param("name") String name, @Param("order") String order);

    @Delete("delete from resource where id = #{id}")
    int deleteById(int id);

    @Update("UPDATE resource SET `order` = #{order} ,`name` = #{name} WHERE `id` = #{id};")
    int updateById(@Param("name") String name, @Param("order") String order,@Param("id") int id);



}