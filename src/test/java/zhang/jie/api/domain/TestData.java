package zhang.jie.api.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.jie.api.demos.vue.loggin.Manager;
import zhang.jie.api.demos.vue.mapper.ManagerMapper;
import zhang.jie.api.domain.mapper.MenuMapper;
import zhang.jie.api.domain.mapper.ResourceMapper;
import zhang.jie.api.domain.mapper.UserMapper;
import zhang.jie.api.domain.mapper.WebTokenMapper;
import zhang.jie.api.domain.mapper.courses.CourseTheme;
import zhang.jie.api.domain.mapper.courses.Course_info;
import zhang.jie.api.domain.mapper.courses.iml.CoursesMapper;
import zhang.jie.api.domain.mapper.resources.CoverOtherClass;
import zhang.jie.api.domain.sales.OrderMapper;
import zhang.jie.api.domain.shopping.ProductMapper;
import zhang.jie.api.domain.users.imp.SeatMapper;
import zhang.jie.api.front.DateApi;
import zhang.jie.api.front.ResourceApi;
import zhang.jie.api.service.courses.CoursesService;
import zhang.jie.api.service.manager.ManagerService;
import zhang.jie.api.service.sale.SaleService;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class TestData {


    @Autowired
    private UserMapper userMapper;

    @Test
    public void demo() {


//        System.out.println(userMapper.getAll());  全部查询
//        System.out.println(userMapper.findById(1)); 单个查询

//        System.out.println(userMapper.insertUser(new User("ok",12))); 指定插入

//        System.out.println(userMapper.updateUser(new User("爱我中华",111)));

//        System.out.println(userMapper.deleteUser(100));

        int num = userMapper.getCount();

        if (num - 10 != 0) {
            userMapper.deleteUser(10);
        }
    }


    @Autowired
    private WebTokenMapper webTokenMapper;

    @Autowired
    DateApi dateApi;

    @Test
    public void demo2(){
        Map<String ,String> map  = new HashMap<>();
        map.put("success","Married");

        List<WebToken> all01 = webTokenMapper.getAll();
        List<WebToken> all02 = new ArrayList<>();

        for(WebToken we:all01){
            if (map.get("success").equals(we.getSuccess())){
                all02.add(we);
            }
        }


        Iterator<WebToken> iterator = all01.iterator();

        while (iterator.hasNext()){
            WebToken webToken  = iterator.next();
            if (map.get("success").equals(webToken.getSuccess())){
                iterator.remove();
            }
        }

        System.out.println(all01);

    }


    @Autowired
    MenuMapper menuMapper;

    @Test
    public void testMenu(){

        System.out.println(menuMapper.getInfoById(-1) == null);

    }


    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    ResourceApi resourceApi;

    @Test
    public void testCoverClass(){
        Map<String , String> map = new HashMap<>();
        map.put("name","jack");
        map.put("order","1");


        resourceMapper.saveOrEdit("jack","3");
//        resourceApi.saveOrEdit(map);


    }


    @Autowired
    CoursesMapper coursesMapper;
    @Test
    public void Demo5() {
        // 使用Gson将Map对象转换为JSON字符串

        CoverOtherClass coverOtherClass = new CoverOtherClass();

        Map<String, Object> course = new HashMap<>();

        coverOtherClass.setCode("000000");
        String name = "teacherDTO";
        String activityCourseDTO = "activityCourseDTO";

        Course_info allInfo = coursesMapper.getAllInfo(1);

        course.put(name, coursesMapper.getBasicInfo(2));
        course.put(activityCourseDTO,coursesMapper.getActivityInfo(3));


        allInfo.setMap(course);
        coverOtherClass.setData(allInfo);

    }

//    拿到图片地址
        @Test
        public void listFiles() {
            String directoryPath ="src/Pic";
            List<String> fileNames = new ArrayList<>();
            File directory = new File(directoryPath);

            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            fileNames.add("http://127.0.0.1:8080/images/"+file.getName());
                        }
                    }
                }
            }
            System.out.println(fileNames);
        }


        @Autowired
    CoursesService coursesService;

@Test
    void Demo2(){

    Random random = new Random();

    // 生成一个 0 到 999999（包括）之间的随机数
    int randomNumber = random.nextInt(10000000);

    // 如果 randomNumber 小于 100000，补齐到六位数
    String sixDigitsNumber = String.format("%07d", randomNumber);


}


@Test
    void demo6(){

    List<CourseTheme> courseTheme = coursesMapper.getImportInfo("course_theme", 13781);

    System.out.println(courseTheme);
    List<CourseTheme> courseThemes = courseTheme.stream().distinct().collect(Collectors.toList());

    List<CourseTheme> theme = coursesMapper.getImportInfo("theme", 13781);

    Iterator<CourseTheme> iterator = courseThemes.iterator(); //father


    System.out.println(courseThemes.size()+" "+theme.size());


    while (iterator.hasNext()){
        CourseTheme next = iterator.next();
        Iterator<CourseTheme> iterator1 = theme.iterator(); //son
        while (iterator1.hasNext()){
            CourseTheme next1 = iterator1.next();
            if (next.getCode().equals(next1.getCode())){
                next.setRecords(next1);
            }
        }
    }

    System.out.println(courseThemes);




//    System.out.println(courseThemes);

}

@Autowired
    ManagerMapper mapper;
@Autowired
    ManagerService managerService;
@Test
    void Demo7(){
//    System.out.println(mapper.getAllPosition());
    Manager manager = new Manager();
    manager.setName("1");
    manager.setGender("nan");
    manager.setNote("asd");
    manager.setPassword("123");
    manager.setData_of_birth("12312");
    manager.setPhone("12312");
    manager.setPortrait("12312");
    manager.setSalary_info(123);
    manager.setPosition("asd");



    System.out.println(mapper.saveManager(manager));


}

@Autowired
    ProductMapper productMapper;


        @Test
    void demo8(){
            System.out.println(productMapper.getAllCourseName());

        }


        @Autowired
    SeatMapper seatMapper;


        @Autowired
    OrderMapper orderMapper;

        @Test
    void demo9(){
            ArrayList<Integer> arrayList = new ArrayList<>();

            arrayList.add(397);

            arrayList.add(144);

            List<String> seatList = orderMapper.getSeat(arrayList);
            List<String> formattedSeatList = new ArrayList<>();

            for (String seat : seatList) {
                String formattedSeat = seat.replace("-", "排") + "座";
                formattedSeatList.add(formattedSeat);
            }


            System.out.println(formattedSeatList);

        }

        @Autowired
        SaleService saleService;

    @Test
    void demo13(){

        saleService.getMovieList();

    }

}