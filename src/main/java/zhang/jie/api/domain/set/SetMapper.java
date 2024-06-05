package zhang.jie.api.domain.set;

import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.mapper.resources.Role;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/4/30$.
 */
@Mapper
public interface SetMapper {

//    职位表
    @Select("select * from position")
    List<Role> getPosition();

    // 场馆
    @Select("select * from room")
    List<Role> getRoom();

//    语言
    @Select("select * from language")
    List<Role> getLanguage();

//根据id更新职位表
    @Select("update position set name=#{name} where id=#{id}")
    void updatePosition(@Param("name") String name,@Param("id") int id);

    // 保存职位
    @Insert("insert into position (name) values (#{name})")
    void savePosition(@Param("name") String name);

    // 删除职位
    @Delete("delete from position where id=#{id}")
    void deletePosition(int id);
    @Select("select * from position where id=#{id}")
    List<Role> getPositionById(int id);



    // 更新场馆
    @Update("update room set name=#{name} where id=#{id}")
    void updateRoom(@Param("name") String name,@Param("id") int id);

    // 保存场馆
    @Insert("insert into room (name) values (#{name})")
    void saveRoom(@Param("name") String name);

    // 删除场馆
    @Delete("delete from room where id=#{id}")
    void deleteRoom(int id);
    @Select("select * from room where id=#{id}")
    List<Role> getRoomById(int id);




    // 更新语言
    @Update("update language set name=#{name} where id=#{id}")
    void updateLanguage(@Param("name") String name,@Param("id") int id);

    // 保存语言
    @Insert("insert into language (name) values (#{name})")
    void saveLanguage(@Param("name") String name);

    // 删除语言
    @Delete("delete from language where id=#{id}")
    void deleteLanguage(int id);
//查询语言
    @Select("select * from language where id=#{id}")
    List<Role> getLanguageById(int id);





}
