package zhang.jie.api.domain.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhang.jie.api.domain.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {
    @Select("select * from menus")
    List<Menu> getAll();

//    查询用arraylist 增删用linkedlist


    @Insert("insert  into menus(name, size, `order`, icon) values(#{name},#{size},#{order},#{icon});")
    public int saveOrEdit(Menu menu);

    @Delete("DELETE from menus where id = #{id}")
    public int deleteById(int id);


//   根据Id查询数据库指定元素
    @Select("select * from menus where  id = #{id}")
    Menu getInfoById(int id);
}
