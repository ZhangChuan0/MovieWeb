package zhang.jie.api.domain.mapper.courses.iml;


import org.apache.ibatis.annotations.*;
import zhang.jie.api.domain.mapper.courses.CourseTheme;
import zhang.jie.api.domain.mapper.courses.Course_info;
import zhang.jie.api.domain.mapper.courses.Courses;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CoursesMapper {


//    分页查所有
    @Select("select * from courses_large limit #{pageSize} offset #{num}")   //pageSize 限制返回结果数量，num标注从什么位置开始返回
    ArrayList<Course_info> getAllCourse(@Param("pageSize") int pageSize, @Param("num") int num);

//    查总数
    @Select("select count(*) from courses_large")
    int getAllCourseCount();

//    改变status
    @Update("update courses_large set discountTag = #{status} where id = #{id}")
    int changeStatus(@Param("status") String status,@Param("id") int id);



//    模糊查询姓名
    @Select("select * from courses_large where courses_large.courseName like #{name} limit #{pageSize} offset #{num}")
    ArrayList<Course_info> getCourseByName(@Param("name") String name,@Param("pageSize") int pageSize, @Param("num") int num);

//    查询模糊姓名的数量总数
    @Select("select count(*) from courses_large where courses_large.courseName like #{name}")
    int getCourseByNameCount(@Param("name") String name);


//    查询状态
    @Select("select * from courses_large where discountTag = #{status} limit #{pageSize} offset #{num}")
    ArrayList<Course_info> getCourseByStatus(@Param("status") String status,@Param("pageSize") int pageSize, @Param("num") int num);
    //    查询指定的status的总数
    @Select("select count(*) from courses_large where discountTag = #{status}")
    int getCourseByStatusCount(@Param("status") String status);
//----------------以上ok

//    组装

//    1.基本信息
    @Select("select teacherName,teacherDescription,teacherPosition from courses_large where id = #{courseId}")
    Course_info getBasicInfo(@Param("courseId") int courseId);


//    3.秒杀活动
    @Select("select activityBeginTime,activityEndTime,activityAmount,activityStock from courses_large where  id = #{courseId};")
    Course_info getActivityInfo(@Param("courseId") int courseId);

    @Select("select * from courses_large where  id = #{courseId}")
    Course_info getAllInfo(@Param("courseId") int courseId);


//    course表的保存

    @Insert("insert into courses(id, `index`, name, status, price, sortnum) VALUES (#{course.id}, #{course.index}, #{course.name}, #{course.status}, #{course.price}, #{course.sortnum})")
    int saveCourse(@Param("course") Courses course);

//    更新子表
    @Insert("insert into courses_large(id,courseName,courseListImg,price,status,sortNum) values" +
            " (#{course_large.id},#{course_large.courseName},#{course_large.courseListImg}," +
            "#{course_large.price},#{course_large.status},#{course_large.sortNum})")
    int saveCourse_large(@Param("course_large") Course_info course_large);


//查询得到name通过Id的形式
    @Select("select * from courses where id = #{id}")
    List<Courses> getCourseName(@Param("id") int id);



//theme数形结构通过index
@Select("SELECT  ${name}.code, ${name}.index,${name}.name \n" +
        "FROM course_theme\n" +
        "         left JOIN theme ON course_theme.code = theme.code\n" +
        "        left join vue.courses c on c.id = course_theme.id\n" +
        "where c.id=#{id}")
    List<CourseTheme> getImportInfo(@Param("name") String name,@Param("id") int id);


//保存
    @Insert("insert into course_theme (id,  name, code)\n" +
            "values (#{id}, #{name}, #{code})")
    int saveCourseTheme(@Param("id") int id,@Param("name") String name,@Param("code") String code);



//    更新theme表的数据

    @Update("update theme set name = #{name} where `index` = #{code} ;")
    int updateTheme(@Param("name") String name,@Param("code") int code);


//    将所有表的信息都转移到course_large上

    @Insert("INSERT INTO courses_large (courseName, brief, teacherName, teacherPosition, " +
            "teacherDescription, previewFirstField, previewSecondField, sortNum, " +
            "courseListImg, courseImgUrl, discounts, price, sales, discountTag, " +
            "activityCourse, activityBeginTime, activityEndTime, activityAmount, " +
            "activityStock, status, courseDescriptionMarkDown) " +
            "VALUES (#{courseName}, #{brief}, #{teacherName}, #{teacherPosition}, " +
            "#{teacherDescription}, #{previewFirstField}, #{previewSecondField}, #{sortNum}, " +
            "#{courseListImg}, #{courseImgUrl}, #{discounts}, #{price}, #{sales}, #{discountTag}, " +
            "#{activityCourse}, #{activityBeginTime}, #{activityEndTime}, #{activityAmount}, " +
            "#{activityStock}, #{status}, #{courseDescriptionMarkDown})")
    int addCourse_large(Course_info courseInfo);


//    根据指定的id删除一条
    @Delete("delete from courses_large where id = #{id}")
    int deleteCourse_large(@Param("id") int id);

//    根据id查找一条
    @Select("select * from courses_large where id = #{id}")
    Course_info getCourse_largeById(@Param("id") int id);


//    更新

    @Update("update courses_large set courseName = #{courseName}, brief = #{brief}, " +
            "teacherName = #{teacherName}, teacherPosition = #{teacherPosition}, " +
            "teacherDescription = #{teacherDescription}, previewFirstField = #{previewFirstField}," +
            " previewSecondField = #{previewSecondField}, sortNum = #{sortNum}, courseListImg = #{courseListImg}, " +
            "courseImgUrl = #{courseImgUrl}, discounts = #{discounts}, price = #{price}, sales = #{sales   }," +
            " discountTag = #{discountTag}, activityCourse = #{activityCourse}, activityBeginTime = #{activityBeginTime}, " +
            "activityEndTime = #{activityEndTime}, activityAmount = #{activityAmount}, activityStock = #{activityStock}, " +
            "status = #{status}, courseDescriptionMarkDown = #{courseDescriptionMarkDown} where id = #{id}")
    int updateCourse_large(Course_info courseInfo);

}
