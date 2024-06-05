package zhang.jie.api.front;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.mapper.resources.CoverOtherClass;
import zhang.jie.api.domain.shopping.Product;
import zhang.jie.api.service.users.UserService;

import java.util.Map;

@Controller
@RequestMapping("/boss/")
public class UserApi {


    @Autowired
    UserService userService;

    //    获取用户列表信息
    @PostMapping("/user/getUserPages")
    @ResponseBody
    public CoverOtherClass getUserPages(@RequestBody Map<String, String> param) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
        int pageSize = 10, currentPage = 1;



//分页
        if (param.get("pageSize") != null && !param.get("pageSize").isEmpty()) {
            pageSize = Integer.parseInt(param.get("pageSize"));
        }
        if (param.get("currentPage") != null && !param.get("currentPage").isEmpty()) {
            currentPage = Integer.parseInt(param.get("currentPage"));

        }

//    判断电话
        if (param.get("phone") != null && !param.get("phone").isEmpty()) {
            String num = param.get("phone");
            coverOtherClass.setData(userService.getUserByPhone(num, pageSize, currentPage));
            return coverOtherClass;
        }

        coverOtherClass.setData(userService.getUserPage(pageSize, currentPage));
        return coverOtherClass;
    }

    @GetMapping("/user/enableUser")
    @ResponseBody
    public boolean enableUser(@RequestParam("userId") String userId) {
        int id = Integer.parseInt(userId);

        return 1 == userService.enableUser(id);

    }


    @PostMapping("/user/forbidUser")
    @ResponseBody
    public boolean forbidUser(@RequestBody Map<String, Integer> param) {

        return 1 == userService.disableUser(param.get("userId"));

    }


    //    得到指定id对应的角色信息
    @GetMapping("/role/getRoleWithUserPermission")
    @ResponseBody
    public CoverOtherClass getRoleWithUserPermission(@RequestParam("userId") String userId) {

        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
        int id = Integer.parseInt(userId);
        coverOtherClass.setData(userService.getRoleByUserId(id));
        return coverOtherClass;
    }

    //    给指定用户分配角色
    @PostMapping("/user/allocateUserRoles")
    @ResponseBody
    public boolean allocateUserRoles(@RequestBody Map<String, Object> param) {
        try {
            System.out.println("后端收集到的：" + param);
            return userService.assignRole(param.toString());
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }


    }


    //    保存或修改
    @PostMapping("/user/saveOrEditUser")
    @ResponseBody
    public CoverOtherClass saveOrEditUser(@RequestBody Map<String, String> param) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");

        System.out.println("转化的结果" + userService.setInfoInit(param));


//        更新
        if (param.get("id") != null && !param.get("id").isEmpty()) {

            userService.upDateUser(param);
            return coverOtherClass;
        }
        //新建
        userService.saveUser(param);
        return coverOtherClass;
    }

    //得到id对应的user
    @GetMapping("/user/getUserById")
    @ResponseBody
    public CoverOtherClass getUserById(@RequestParam("userId") String userId) {

        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
        coverOtherClass.setData(userService.getOneUserInfo(Integer.parseInt(userId)));
        return coverOtherClass;
    }


    @DeleteMapping("/user/removeUser")
    @ResponseBody
    public CoverOtherClass removeUser(@RequestParam("userId") String userId) {
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        if (userService.removeUser(Integer.parseInt(userId))) {
            coverOtherClass.setCode("000000");
            return coverOtherClass;
        }
        coverOtherClass.setCode("111111");
        return coverOtherClass;

    }

//    卖品===============================================================


    @PostMapping("/shopping/getAllData")
    @ResponseBody
    public CoverListData getAllData(@RequestBody Product product) {
        CoverListData coverListData = new CoverListData();

        //    名字模糊查询卖品
        if (product.getName() != null && !product.getName().isEmpty()) {

            coverListData.setData(userService.getShoppingByName(product));
            return coverListData;
        }
        coverListData.setData(userService.getAllShopping());
        return coverListData;
    }

    //    得到电影名称
    @GetMapping("/getMovieName")
    @ResponseBody
    public CoverListData getMovieName() {
        CoverListData coverListData = new CoverListData();
        coverListData.setRecords(userService.getAllMovieName());

        return coverListData;
    }

    //保存 Or 更新
    @PostMapping("/shopping/saveOrUpdate")
    @ResponseBody
    public CoverListData saveOrUpdate(@RequestBody Product product) {
        CoverListData coverListData = new CoverListData();

//    更新
        if (product.getId() != 0) {
            if (userService.update(product)) {
                return coverListData;
            }

        }
//    保存
        if (userService.save(product)) {

            return coverListData;
        }
        coverListData.setCode("111111");
        return coverListData;
    }


    //根据单id查到product信息
    @GetMapping("/shopping/getProductById")
    @ResponseBody
    public CoverListData getProductById(@RequestParam("id") int id) {
        CoverListData coverListData = new CoverListData();
        coverListData.setData(userService.getOneShopping(id));
        return coverListData;
    }

//    删除
    @DeleteMapping("/shopping/deleteProduct")
    @ResponseBody
    public CoverListData deleteProduct(@RequestParam("id") int id) {
        CoverListData coverListData = new CoverListData();

        if (!userService.remove(id)){
            coverListData.setCode("111111");
            return coverListData;
        }
        return coverListData;
    }
}
