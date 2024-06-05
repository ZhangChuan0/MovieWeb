package zhang.jie.api.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.jie.api.demos.vue.loggin.Manager;
import zhang.jie.api.demos.vue.mapper.ManagerMapper;
import zhang.jie.api.domain.member.Member;
import zhang.jie.api.domain.member.MemberMapper;
import zhang.jie.api.domain.set.SetMapper;
import zhang.jie.api.domain.showdata.ShowDataMapper;
import zhang.jie.api.domain.users.User_use;
import zhang.jie.api.domain.users.imp.User_userMapper;
import zhang.jie.api.service.manager.ManagerService;
import zhang.jie.api.service.member.MemberService;

/**
 * Created by zhouhuanping$ on 2024/4/25$.
 */
@SpringBootTest
public class bigTest {

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
        manager.setData_of_birth("1999-01-01");
        manager.setPhone("12312");
        manager.setPortrait("12312");
        manager.setSalary_info(123);
        manager.setPosition("asd");



        System.out.println(mapper.saveManager(manager));


    }

    @Test
    void demo8(){
        Manager manager = new Manager();
        manager.setPassword("123456");
        manager.setPhone("18783174270");
        manager.setRole("admin");

        if (null == mapper.login(manager)){
            System.out.println("查无此人");
        }else {

            System.out.println(mapper.login(manager));
        }



    }

    @Autowired
    User_userMapper userMapper;

    @Test
    void Demo9(){
        User_use userUse = new User_use();
        userUse.setRegIp("12312");
        userUse.setPortrait("12312");
        userUse.setStatus("da");
        userUse.setCreateTime("2024/04/02");
        userUse.setPassword("123456");
        userUse.setPhone("18783174270");
        userUse.setName("12312");

        System.out.println(userMapper.addUser(userUse));


    }


    @Autowired
    SetMapper setMapper;

    @Test
    void demo10(){
        System.out.println(setMapper.getPosition());

        System.out.println(setMapper.getRoom());

        System.out.println(setMapper.getLanguage());

    }


    @Autowired
    MemberMapper memberMapper;


    @Autowired
    MemberService memberService;
    @Test
    void demo11(){
        Member member = new Member();
        member.setId(2679);



        System.out.println("前"+memberService.getMemberBalance(member));
// 充值100
        member.setBalance("100");
        memberService.recharge(member);
        System.out.println("后"+memberService.getMemberBalance(member));
    }


    @Autowired
    ShowDataMapper showDataMapper;
    @Test
    void demo12(){

        System.out.println(showDataMapper.getCourseInfo());




    }



}
