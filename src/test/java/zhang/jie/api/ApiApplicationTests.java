package zhang.jie.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zhang.jie.api.demos.util.UserInfo;
import zhang.jie.api.domain.WebToken;
import zhang.jie.api.front.PostValue;
import zhang.jie.api.demos.web.PathVariableController;
import zhang.jie.api.demos.web.Util;

import java.util.ArrayList;

@SpringBootTest
class ApiApplicationTests {

    @Autowired
    PathVariableController pathVariableController;

    @Test
    void contextLoads() {
        Util util0 = new Util("1","jack","java study open at now","www.bing.com");
        Util util1 = new Util("2","lisa","C++ study open at now","www.bing.com");
        Util util2 = new Util("3","David","python study open at now","www.bing.com");
        Util util3 = new Util("4","frank","C# study open at now","www.bing.com");

        ArrayList<Util> utils = new ArrayList<>();

        utils.add(util0);
        utils.add(util1);
        utils.add(util2);
        utils.add(util3);

        for (Util a:utils) {
           if (a.getId().equals("1")){

//               System.out.println(a);
           }
        }

//        pathVariableController.getLogin("1");

    }

    @Autowired
    PostValue postValue;

    @Test
    void testVue(){

        UserInfo userInfo = new UserInfo();
        userInfo.setName("jack");
        userInfo.setPortrait("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        System.out.println(userInfo);

    }

    @Test
    public void demoe1(){

        WebToken webToken = new WebToken();
        webToken.setId(1);
        webToken.setContent("nihao");
        webToken.setSuccess("true");
        webToken.setState(1);
        System.out.println(webToken.toString());

    }


}
