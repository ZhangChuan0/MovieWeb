package zhang.jie.api.domain.mapper.resources.imp;


import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.mapper.resources.Records;
import java.util.List;

@Mapper
public interface RecordsMapper {

    @Select("select * from recodes;")
    List<Records> getAll();

    @Select("select * from recodes where id = #{id}")
    List<Records> getById(int id);

    @Insert("insert into recodes(name, description, url) values(#{name}, #{description}, #{url})")
    int add(Records records);


    @Update("update recodes set name = #{name}, description = #{description}, url = #{url} where id = #{id}")
    int update(Records records);

    @Delete("delete from recodes where id = #{id}")
    int deleteRecordsById(int id);
}
