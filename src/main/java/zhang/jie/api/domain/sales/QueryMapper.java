package zhang.jie.api.domain.sales;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import zhang.jie.api.domain.mapper.courses.Course_info;

import java.util.ArrayList;

/**
 * Created by zhouhuanping$ on 2024/5/26$.
 */

@Mapper
public interface QueryMapper {

//    日期 转数字
    @Select("select * from courses_large where sales = #{sales}")
    ArrayList<Course_info> queryDate(int sales);

//    类型
@Select("select * from courses_large where previewFirstField = #{type} " +
        "or previewSecondField = #{type}")
    ArrayList<Course_info> queryType(String type);

//    语言
    @Select("select * from courses_large where teacherPosition = #{language}")
    ArrayList<Course_info> queryLanguage(String language);


}
