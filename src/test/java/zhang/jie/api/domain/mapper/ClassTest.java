package zhang.jie.api.domain.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.WebToken;
import zhang.jie.api.domain.equipment.CoverBook;
import zhang.jie.api.domain.mapper.courses.iml.CoursesMapper;
import zhang.jie.api.domain.mapper.resources.*;
import zhang.jie.api.domain.mapper.resources.imp.RecordsMapper;
import zhang.jie.api.domain.mapper.resources.imp.RoleMapper;
import zhang.jie.api.domain.mapper.role.CoverOfName;
import zhang.jie.api.domain.mapper.role.Name;
import zhang.jie.api.domain.mapper.role.iml.NameMapper;
import zhang.jie.api.domain.users.imp.User_userMapper;
import zhang.jie.api.service.courses.CoursesService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ClassTest {


    @Autowired
    BookMapper bookMapper;

    @Autowired
    CoverBook coverBook;

    @Test
    void Demo1(){
        System.out.println(coverBook.getAllBook());


    }

    @Autowired
    MenuMapper menuMapper;


    @Autowired
    RecordsMapper recordsMapper;





    @Test
    void demoTest() {
        Data data = new Data(1,12,1,10,0,10,20);
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
        List<Records> all = recordsMapper.getAll();
        data.setRecords(all);
        coverOtherClass.setData(data);
        System.out.println(coverOtherClass);
    }


    @Test
    void demoTest1() throws InterruptedException {

        Records records = new Records();
        records.setUrl("as");
        records.setDescription( ("description"));
        records.setName(  ("name"));
        System.out.println(recordsMapper.add(records));



    }


    @Autowired
    RoleMapper roleMapper;

    @Autowired
    WebTokenMapper webTokenMapper;
    @Test
    void demoTest2(){
        List list = new ArrayList();
        DataOfAny dataOfAny = new DataOfAny();
        DataOfAny dataOfAny1 = new DataOfAny();
        DataOfAny dataOfAny2 = new DataOfAny();

        List<Role> all = roleMapper.getAll();
        dataOfAny.setRecords(all);
        dataOfAny.setId(1);

        List<Records> all1 = recordsMapper.getAll();
        dataOfAny1.setRecords(all1);
        dataOfAny1.setId(2);

        List<WebToken> all2 = webTokenMapper.getAll();
        dataOfAny2.setRecords(all2);
        dataOfAny2.setId(3);

        list.add(dataOfAny);
        list.add(dataOfAny1);
        list.add(dataOfAny2);
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
        coverOtherClass.setListData(list);
        System.out.println(coverOtherClass);

    }

    @Autowired
    NameMapper nameMapper;

    @Test
    void testDemo3(){
        CoverOfName menu = new CoverOfName(1);
        CoverOfName time = new CoverOfName(1);
        CoverListData coverListData = new CoverListData();
        Name mItem = new Name(5);
        Name TItem = new Name(5);
        menu.setName("菜单管理");
        time.setName("时间管理");
        menu.setRecords(nameMapper.getMenuAll(mItem));
        time.setRecords(nameMapper.getTimeAll(TItem));
        coverListData.addOne(menu);
        coverListData.addOne(time);
        System.out.println(coverListData);




    }

    @Test
    void testDemo5(){
//        List objects = new ArrayList<>();
//
//        objects.add(41);
//        objects.add(42);
//        objects.add(43);
//        objects.add(44);

    }


    @Autowired
    User_userMapper userMapper;

    @Test
    void demoUer(){
        String phone = '%'+"212"+'%';

    }

    @Autowired
    CoursesService coursesService;

    @Autowired
    CoursesMapper coursesMapper;

    @Test
    void  demoCourse(){



    }

}
