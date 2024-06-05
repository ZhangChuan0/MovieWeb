package zhang.jie.api.domain.showdata;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhang.jie.api.domain.mapper.courses.Course_info;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/5/9$.
 */

@Mapper
public interface ShowDataMapper {

    @Select("select courseName,discounts,activityStock,price from  courses_large")
    List<Course_info> getCourseInfo();
}
