package zhang.jie.api.service.courses;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.mapper.courses.CourseTheme;
import zhang.jie.api.domain.mapper.courses.Course_info;
import zhang.jie.api.domain.mapper.courses.Courses;
import zhang.jie.api.domain.mapper.courses.iml.CoursesMapper;
import zhang.jie.api.domain.mapper.resources.CoverOtherClass;
import zhang.jie.api.domain.mapper.resources.DataOfAny;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CoursesService {
    @Autowired
    CoursesMapper coursesMapper;

    private String imageServerUrl;

    //    分页查询 全部课程列表
    public DataOfAny getAllCourses(int pageSize, int num) {

        DataOfAny data = new DataOfAny(1, pageSize, num, coursesMapper.getAllCourseCount(), 0);
        data.setRecords(coursesMapper.getAllCourse(pageSize, pageSize * (num - 1)));
        return data;

    }

    public boolean changeStatus(String status, int id) {

        try {

            return coursesMapper.changeStatus(status, id) > 0;

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }


//    根据名字模糊查询

    public DataOfAny getCoursesByName(String name, int pageSize, int num) {
        name = '%' + name + '%';

        DataOfAny data = new DataOfAny(1, pageSize, num, coursesMapper.getCourseByNameCount(name), 0);
        data.setRecords(coursesMapper.getCourseByName(name, pageSize, pageSize * (num - 1)));
        return data;
    }

//    查询状态

    public DataOfAny getCoursesByStatus(String status, int pageSize, int num) {

        DataOfAny data = new DataOfAny(1, pageSize, num, coursesMapper.getCourseByStatusCount(status), 0);
        data.setRecords(coursesMapper.getCourseByStatus(status, pageSize, pageSize * (num - 1)));
        return data;
    }

//    查询得到course的name通过Id
    public DataOfAny getCourseNameBydId(int courseId){
        DataOfAny data = new DataOfAny();
        data.setRecords(coursesMapper.getCourseName(courseId));
        return data;
    }



//    保存前端的图片

    public boolean saveImg(MultipartFile file) {
        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件扩展名
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 设置保存路径
            String uploadDir = new File(System.getProperty("user.dir"), "src/Pic/").getAbsolutePath();

            // 创建保存路径(如果不存在)
            File saveDir = new File(uploadDir);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            // 构建保存文件路径
            String savePath;
            File saveFile;
            int fileCount = 1;
            do {
                String fileCountSuffix = fileCount > 1 ? "-" + fileCount : "";
                savePath = uploadDir + File.separator + originalFilename.replace(fileExtension, fileCountSuffix + fileExtension);
                saveFile = new File(savePath);
                fileCount++;
            } while (saveFile.exists() && !saveFile.isDirectory());

            // 保存文件
            file.transferTo(saveFile);

//            服务器该图片的服务器地址
              imageServerUrl = "http://127.0.0.1:8080/images/" + saveFile.getName();

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }

    }

//    得到图像地址
    public String getImgUrl(){
        return this.imageServerUrl;
    }


//    前端的查询large-form
    public CoverOtherClass getCoursesById(int courseID){

        CoverOtherClass coverOtherClass = new CoverOtherClass();

        Map<String, Object> course = new HashMap<>();

        coverOtherClass.setCode("000000");
        String name = "teacherDTO";
        String activityCourseDTO = "activityCourseDTO";

        Course_info allInfo = coursesMapper.getAllInfo(courseID);

        course.put(name, coursesMapper.getBasicInfo(courseID));
        course.put(activityCourseDTO,coursesMapper.getActivityInfo(courseID));


        allInfo.setMap(course);

        coverOtherClass.setData(allInfo);

        return coverOtherClass;
    }


//    保存前端的值
    public boolean saveCourse(String courseName ,String courseListImg,
    Double price,
    Integer status ,String sortNum ){

        Random random = new Random();

        // 生成一个 0 到 999999（包括）之间的随机数
        int randomNumber = random.nextInt(10000000);
        int id = Integer.parseInt(String.format("%07d", randomNumber));

        Courses courses = new Courses();
        Course_info courseInfo = new Course_info();
        courses.setId(id);
        courses.setName(courseName);
        courses.setPrice(price);
        courses.setStatus(String.valueOf(status));

        courseInfo.setCourseImgUrl(courseListImg);
        courseInfo.setId(id);
        courseInfo.setStatus(status);
        courseInfo.setSortNum(sortNum);
        courseInfo.setCourseName(courseName);
        courseInfo.setPrice(price);

        return coursesMapper.saveCourse(courses) == coursesMapper.saveCourse_large(courseInfo);





    }




//    根据指定id找到选中的课表信息


    public CoverListData getCourseByID(int courseID){
        CoverListData coverListData = new CoverListData();
        List<CourseTheme> courseTheme = coursesMapper.getImportInfo("course_theme", courseID);
//去重
        List<CourseTheme> courseThemes = courseTheme.stream().distinct().collect(Collectors.toList());

        List<CourseTheme> theme = coursesMapper.getImportInfo("theme", courseID);

        Iterator<CourseTheme> iterator = courseThemes.iterator();


        while (iterator.hasNext()){
            CourseTheme next = iterator.next();
            Iterator<CourseTheme> iterator1 = theme.iterator();
            while (iterator1.hasNext()){
                CourseTheme next1 = iterator1.next();
                if (next.getCode().equals(next1.getCode())){
                    next.setRecords(next1);
                }
            }
        }

        coverListData.addAll(courseThemes);
        return coverListData;

    }

//    保存
    public boolean saveCourseTheme(int id ,String sectionName,String description){

    return  1==coursesMapper.saveCourseTheme(id,sectionName,description);

    }

//    更新theme数据
    public boolean updateCourseTheme( String sectionName,String code){
        int index = Integer.parseInt(code);

        return 1 == coursesMapper.updateTheme(sectionName,index);
    }


    public String saveVideo(MultipartFile file){

        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件扩展名
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 设置保存路径
            String uploadDir = new File(System.getProperty("user.dir"), "src/Pic/video").getAbsolutePath();

            // 创建保存路径(如果不存在)
            File saveDir = new File(uploadDir);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            // 构建保存文件路径
            String savePath;
            File saveFile;
            int fileCount = 1;
            do {
                String fileCountSuffix = fileCount > 1 ? "-" + fileCount : "";
                savePath = uploadDir + File.separator + originalFilename.replace(fileExtension, fileCountSuffix + fileExtension);
                saveFile = new File(savePath);
                fileCount++;
            } while (saveFile.exists() && !saveFile.isDirectory());

            // 保存文件
            file.transferTo(saveFile);

//            服务器该图片的服务器地址
            return  "http://127.0.0.1:8080/images/video/" + saveFile.getName();

        } catch (IOException e) {

            e.printStackTrace();
            return "";
        }


    }


//    处理前端的一坨信息
    public Course_info operation(Map<String,Object> param){
        Course_info courseInfo = new Course_info();

        for (Map.Entry<String, Object> entry : param.entrySet()){
            if (entry.getValue()!=null && !entry.getValue().equals("")){
                if (entry.getKey().equals("price")){
                    courseInfo.setPrice(Double.valueOf(entry.getValue().toString()));
                }
                if (entry.getKey().equals("discounts")){
                    courseInfo.setDiscounts(Double.valueOf(entry.getValue().toString()));
                }
                if (entry.getKey().equals("courseName")) {
                    courseInfo.setCourseName((String) entry.getValue());
                } else if (entry.getKey().equals("brief")) {
                    courseInfo.setBrief((String) entry.getValue());
                } else if (entry.getKey().equals("previewFirstField")) {
                    courseInfo.setPreviewFirstField((String) entry.getValue());
                } else if (entry.getKey().equals("previewSecondField")) {
                    courseInfo.setPreviewSecondField((String) entry.getValue());
                } else if (entry.getKey().equals("sortNum")) {
                    courseInfo.setSortNum((String) entry.getValue());
                } else if (entry.getKey().equals("courseListImg")) {
                    courseInfo.setCourseListImg((String) entry.getValue());
                } else if (entry.getKey().equals("courseImgUrl")) {
                    courseInfo.setCourseImgUrl((String) entry.getValue());
                } else if (entry.getKey().equals("discounts")) {
                    courseInfo.setDiscounts(entry.getValue());
                } else if (entry.getKey().equals("price")) {
                    courseInfo.setPrice(Double.valueOf(entry.getValue().toString()));
                } else if (entry.getKey().equals("sales")) {
                    courseInfo.setSales((Integer) entry.getValue());
                } else if (entry.getKey().equals("discountTag")) {
                    courseInfo.setDiscountTag((String) entry.getValue());
                } else if (entry.getKey().equals("activityCourse")) {
                    courseInfo.setActivityCourse(""+entry.getValue()+"");
                } else if (entry.getKey().equals("status")) {
                    courseInfo.setStatus((Integer) entry.getValue());
                } else if (entry.getKey().equals("courseDescriptionMarkDown")) {
                    courseInfo.setCourseDescriptionMarkDown((String) entry.getValue());
                } else if (entry.getKey().equals("activityBeginTime")) {
                    courseInfo.setActivityBeginTime((String) entry.getValue());
                } else if (entry.getKey().equals("activityEndTime")) {
                    courseInfo.setActivityEndTime((String) entry.getValue());
                } else if (entry.getKey().equals("activityAmount")) {
                    courseInfo.setActivityAmount(Double.valueOf(entry.getValue().toString()));
                } else if (entry.getKey().equals("activityStock")) {
                    courseInfo.setActivityStock((Integer) entry.getValue());
                } else if (entry.getKey().equals("id")){
                    courseInfo.setId(Integer.parseInt((String) entry.getValue()));
                } else if (entry.getKey().equals("teacherName")){
                    courseInfo.setTeacherName((String) entry.getValue());
                }else if (entry.getKey().equals("teacherPosition")){
                    courseInfo.setTeacherPosition(entry.getValue().toString());
                }else if (entry.getKey().equals("teacherDescription")){
                    courseInfo.setTeacherDescription((String)entry.getValue());
                }
            }


        }

        return courseInfo;

    }

//    保存course_large表
    public boolean saveCourseLarge(Map<String,Object> param){

        return 1==coursesMapper.addCourse_large(operation(param));
    }

//    更新巨大的coure表
    public boolean updateCourse_large(Map<String,Object> param){

        return 1 == coursesMapper.updateCourse_large(operation(param));


    }

//    删除一条
    public boolean deleteCourseLarge(int id){


        return 1==coursesMapper.deleteCourse_large(id);
    }

//    查找一条根据id
    public Course_info findCourseLarge(int id){

        return coursesMapper.getCourse_largeById(id);
    }


}
