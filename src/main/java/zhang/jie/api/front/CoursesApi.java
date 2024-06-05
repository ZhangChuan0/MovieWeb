package zhang.jie.api.front;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.mapper.resources.CoverOtherClass;
import zhang.jie.api.domain.users.images.Image;
import zhang.jie.api.service.courses.CoursesService;

import java.util.Map;

@RequestMapping("/boss/")
@Controller
public class CoursesApi {

        @Autowired
        CoursesService coursesService;


        @PostMapping("/courses/getQueryCourses")
        @ResponseBody
        public CoverOtherClass getQueryCourses(@RequestBody Map<String,String> param){
            CoverOtherClass coverOtherClass = new CoverOtherClass();
        System.out.println(param.toString());
        coverOtherClass.setCode("000000");
        int pageSize = 10,currentPage = 1;
        String name ="",status = "";
//        分页

        //分页
        if (param.get("pageSize") != null && !param.get("pageSize").isEmpty()){
            pageSize = Integer.parseInt(param.get("pageSize"));
        }
        if (param.get("currentPage") != null && !param.get("currentPage").isEmpty()){
            currentPage = Integer.parseInt(param.get("currentPage"));
        }

        if (param.get("name") != null && !param.get("name").isEmpty()){
            name = param.get("name");
            coverOtherClass.setData(coursesService.getCoursesByName(name,pageSize,currentPage));
            return coverOtherClass;
        }
        if (param.get("status") != null && !param.get("status").isEmpty()){
            status = param.get("status");
            coverOtherClass.setData(coursesService.getCoursesByStatus(status,pageSize,currentPage));
            return coverOtherClass;
        }

        coverOtherClass.setData(coursesService.getAllCourses(pageSize,currentPage));
        return coverOtherClass;
    }


    @GetMapping("/courses/changeState")
    @ResponseBody
    public boolean changeState(@RequestParam("courseId") int courseId,@RequestParam("status") String status) {
        return coursesService.changeStatus(status,courseId);
    }


    @PostMapping("/course/upload")
    @ResponseBody
    public Image uploadCourse(@RequestParam("file") MultipartFile file) {
        Image image = new Image();
        try {

                 image.setStatus("000000");

            if (coursesService.saveImg(file))  {
                image.setName(coursesService.getImgUrl());
                return image;
            }


        }catch (Exception e)
        {
            System.err.println(e);

            image.setStatus("111111");
        }

        return image;
    }



//    保存巨大表单

    @PostMapping("/courses/saveUpdateCourse")
    @ResponseBody
    public CoverOtherClass saveUpdateCourse(@RequestBody Map<String,Object> param) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
/*
        {
            String courseName = "", courseListImg = "";
            Double price = 1.0;
            Integer status = 0, sortNum = 0;
            int i = 0;

            CoverOtherClass coverOtherClass = new CoverOtherClass();

            if (param.get("courseName") != null && !param.get("courseName").toString().isEmpty()) {
                courseName = param.get("courseName").toString();
                i++;
            }

            if (param.get("price") != null && !param.get("price").toString().isEmpty()) {
                price = Double.parseDouble(param.get("price").toString());
                i++;
            }
            if (param.get("status") != null && !param.get("status").toString().isEmpty()) {
                status = Integer.parseInt(param.get("status").toString());
                i++;
            }
            if (param.get("sortNum") != null && !param.get("sortNum").toString().isEmpty()) {
                sortNum = Integer.parseInt(param.get("sortNum").toString());
                i++;
            }
            if (param.get("courseListImg") != null && !param.get("courseListImg").toString().isEmpty()) {
                courseListImg = param.get("courseListImg").toString();
                i++;

            }
            if (i == 5) {
                Course_info courseInfo = new Course_info();
                courseInfo.setSortNum(sortNum);
                courseInfo.setCourseName(courseName);
                courseInfo.setStatus(status);
                courseInfo.setPrice(price);
                courseInfo.setCourseImgUrl(courseListImg);
                coursesService.saveCourse(courseName, courseListImg, price, status, sortNum);
            }
            System.out.println(i + "参数");


            coverOtherClass.setCode("000000");
            return coverOtherClass;
        }
    }*/

//        更新走条路径
        if (param.get("id")!=null){
            if ( coursesService.updateCourse_large(param)){
                return coverOtherClass;
            }
            coverOtherClass.setCode("111111");
            return coverOtherClass;

        }

        if (coursesService.saveCourseLarge(param)){
            return coverOtherClass;
        }


    coverOtherClass.setCode("111111");
    return coverOtherClass;
    }

    // 查找

    @GetMapping("courses/getCourse_largeById")
    @ResponseBody
    public CoverOtherClass getCourse_largeById(@RequestParam("courseId") int courseId) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();

        System.out.println("id:=="+courseId);
        coverOtherClass.setData(coursesService.findCourseLarge(courseId));
        coverOtherClass.setCode("000000");
        return coverOtherClass;

    }
// 删除
    @DeleteMapping("courses/deleteById")
    @ResponseBody
    public CoverOtherClass deleteById(@RequestParam("courseId") int courseId) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
        if (coursesService.deleteCourseLarge(courseId)){
            return coverOtherClass;
        }
    coverOtherClass.setCode("111111");
        System.out.println("id:=="+courseId);
        return coverOtherClass;

    }




    @GetMapping("/courses/getCourseById")
    @ResponseBody
    public CoverOtherClass getCourseById(@RequestParam("courseId") int courseId) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();

        try {
            System.out.println("id:=="+courseId);
            return coursesService.getCoursesById(courseId);
        }catch (Exception e){
            System.out.println("报错"+e);
            coverOtherClass.setCode("111111");
            return coverOtherClass;
        }

    }

//根据指定的id查课程章节
    @GetMapping("/courses/selection/getSectionAndLesson")
    @ResponseBody
    public CoverListData getSectionAndLesson(@RequestParam("courseId") int courseId) {


        return coursesService.getCourseByID(courseId);
    }


//   通过Id查询到course表的单条记录

    @GetMapping("/courses/getCourseNameById")
    @ResponseBody
    public CoverOtherClass getCourseNameById(@RequestParam("courseId") int courseId) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
        coverOtherClass.setData(coursesService.getCourseNameBydId(courseId));
        return coverOtherClass;

    }


    //根据id获取章节信息

    @GetMapping("/courses/selection/getSectionById")
    @ResponseBody
    public CoverListData getSectionById(@RequestParam("code") String code,@RequestParam("name") String name) {
        CoverListData coverListData = new CoverListData();

//        更新某项数据
        System.out.println("section code: "+code+","+name);

        if (coursesService.updateCourseTheme(name,code)){

            return coverListData;
        }
        coverListData.setCode("111111");
        return  coverListData;

    }

//    新增courseTheme

    @PostMapping("/courses/saveCourseTheme")
    @ResponseBody
    public CoverListData saveCourseTheme(@RequestBody Map<String,Object> param) {
        CoverListData coverListData = new CoverListData();
        System.out.println("前端需要保存的数据"+param);
    int id = 0, orderNum = 0,i=0;
    String sectionName ="",description="",code="";
    //        五个数据全拿下

    if (param.get("id") != null && !param.get("id").toString().isEmpty()){
        id =    Integer.parseInt(param.get("id").toString());
        i++;
    }
    if (param.get("sectionName") != null && !param.get("sectionName").toString().isEmpty()){
        sectionName = param.get("sectionName").toString();
        i++;
    }
    if (param.get("orderNum") != null && !param.get("orderNum").toString().isEmpty()){
        orderNum = Integer.parseInt(param.get("orderNum").toString());
        i++;
    }
    if (param.get("description") != null && !param.get("description").toString().isEmpty()){
        description = param.get("description").toString();
        i++;
    }


    if ( i == 4){
        boolean ok = coursesService.saveCourseTheme(id,sectionName,description);
        if (!ok){
            coverListData.setCode("111111");
            return coverListData;
        }

    }

        return coverListData;

    }




//    保存前端上传的视频
    @PostMapping("/video/upload")
    @ResponseBody
    public String saveVideInService(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return   coursesService.saveVideo(file);
    }
}
