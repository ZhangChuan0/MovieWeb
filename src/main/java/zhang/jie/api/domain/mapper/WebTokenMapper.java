package zhang.jie.api.domain.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhang.jie.api.domain.WebToken;

import java.util.List;

@Mapper
public interface WebTokenMapper {

    @Select("select * from webtoken")
    public List<WebToken> getAll();

    @Select("select count(id) from webtoken")
     int getCount();

    @Select("select * from webtoken where id = #{id}")
    public WebToken columnsCount(int id);

    @Select("select * from webtoken where id = #{id}")
     List<WebToken> getWebTokenById(int id);

}
