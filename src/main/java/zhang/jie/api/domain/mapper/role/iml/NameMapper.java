package zhang.jie.api.domain.mapper.role.iml;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zhang.jie.api.domain.mapper.role.Name;

import java.util.List;

@Mapper
public interface NameMapper {


    //菜单的项
    @Select("select * from testofmenu where id = #{id}")
    List<Name> getMenuAll(Name name);



//    时间的项
    @Select("select * from testoftime where id = #{id}")
    List<Name> getTimeAll(Name name);

//    更新每一项

/*    @Select("update testoftime set selected = 'true' where `index` = #{index}")
    void updateTimeAll(@Param("index") int index);


    @Select("update testofmenu set selected = 'true' where `index` = #{index}")
    void updateMenuAll(@Param("index") int index);*/


    @Update("<script>" +
            "UPDATE testoftime SET selected = CASE " +
            "<foreach item='index' collection='indexList' separator=' '>" +
            "WHEN `index` = #{index} THEN 'true' " +
            "</foreach>" +
            "ELSE 'false' END " +
            "WHERE id = #{id}" +
            "</script>")
    void updateTimeAll1(@Param("indexList") List<Integer> indexList, @Param("id") int id);

    @Update("<script>" +
            "UPDATE testofmenu SET selected = CASE " +
            "<foreach item='index' collection='indexList' separator=' '>" +
            "WHEN `index` = #{index} THEN 'true' " +
            "</foreach>" +
            "ELSE 'false' END " +
            "WHERE id = #{id}" +
            "</script>")
    void updateMenuAll1(@Param("indexList") List<Integer> indexList, @Param("id") int id);



}
