package zhang.jie.api.service.users;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhang.jie.api.domain.mapper.courses.Course_info;
import zhang.jie.api.domain.mapper.resources.DataOfAny;
import zhang.jie.api.domain.shopping.Product;
import zhang.jie.api.domain.shopping.ProductMapper;
import zhang.jie.api.domain.users.User_use;
import zhang.jie.api.domain.users.imp.User_userMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {


    @Autowired
    User_userMapper userMapper;

    //获取用户列表  分页的情况下完成
    public DataOfAny getUserPage(int pageSize,int num){

        DataOfAny data = new DataOfAny(1,pageSize,num,userMapper.getAllUserCount(),0);
        data.setRecords(userMapper.getAllUserPages(pageSize, pageSize*(num-1)));

        return data;
    }



//    电话号码查询
    public DataOfAny getUserByPhone(String num,int pageSize,int number){
        String phone = '%'+num+'%';
        DataOfAny data = new DataOfAny(1,pageSize,number,userMapper.getPhonePage(phone),0);
        data.setRecords(userMapper.searchByPhone(phone,pageSize,pageSize*(number-1)));
        return  data;
    }


//    启用
    public int enableUser(int userId){
        return userMapper.changeEnable(userId);
    }
// 禁用
    public int disableUser(int userId){
        return userMapper.changeDisable(userId);
    }


//    Id查用户对应的role信息
    public DataOfAny getRoleByUserId(int userId){
        DataOfAny dataOfAny = new DataOfAny();
        dataOfAny.setRecords(userMapper.getRoleById(userId));
        return dataOfAny;
    }


//    给指定用户分配role信息
    public boolean assignRole(String param){
        Gson gson = new Gson();

        // 解析 JSON 字符串
        JsonObject jsonObject = gson.fromJson(param, JsonObject.class);
        JsonArray roleList = jsonObject.getAsJsonArray("roleIdList");
        int  userId  = jsonObject.get("userId").getAsInt();

        List list = new ArrayList<>();

        for (JsonElement element : roleList) {
            if (element.isJsonNull()) {
                // 处理 JsonNull 对象的情况
                continue;
            } else {
                int index = element.getAsInt();
                list.add(index);
            }

        }



        if (!list.isEmpty()){
            System.out.println("sql执行结果"+userMapper.updateUserRole(list, userId));
            return true;
        }


        return false;
    }





//    保存或

    public boolean saveUser(Map<String ,String> param){


        return 1 == userMapper.addUser(setInfoInit(param));

    }

//    更新
public boolean upDateUser(Map<String ,String> param){


    return 1 == userMapper.updateUser(setInfoInit(param));

}


    public User_use getOneUserInfo(int id){

        return userMapper.getOneUser(id);
    }


//    处理一坨数据
    public User_use setInfoInit(Map<String ,String> param) {
        User_use userUse = new User_use();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (entry.getValue() != null & !Objects.equals(entry.getValue(), "")) {
                if (entry.getKey().equals("id")) {
                    userUse.setId(Integer.parseInt(entry.getValue()));
                }
                if (entry.getKey().equals("phone")) {
                    userUse.setPhone((String) entry.getValue());
                }
                if (entry.getKey().equals("password")) {
                    userUse.setPassword((String) entry.getValue());
                }
                if (entry.getKey().equals("name")) {
                    userUse.setName((String) entry.getValue());
                }
                if (entry.getKey().equals("portrait")) {
                    userUse.setPortrait((String) entry.getValue());
                }
                if (entry.getKey().equals("regIp")) {
                    userUse.setRegIp((String) entry.getValue());
                }
                if (entry.getKey().equals("status")) {
                    userUse.setStatus((String) entry.getValue());
                }
                if (entry.getKey().equals("createTime")) {
                    userUse.setCreateTime((String) entry.getValue());
                }
                if (entry.getKey().equals("updateTime")){
                    userUse.setUpdateTime((String) entry.getValue());
                }


            }
        }
        return userUse;
    }

//移除某个用户
    public boolean removeUser(int id){
        try
        {
            return 1==userMapper.removeUser(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }


    }


//    周边有关
    @Autowired
    ProductMapper productMapper;
//    全部周边
    public DataOfAny getAllShopping(){
        DataOfAny dataOfAny = new DataOfAny();

        List<Product> all = productMapper.getAll();
        dataOfAny.setRecords(all);
        return dataOfAny;
    }


//    名字模糊查询卖品
    public DataOfAny getShoppingByName(Product product){
        DataOfAny dataOfAny = new DataOfAny();

            List<Product> byName = productMapper.getByName("%"+product.getName()+"%");
            dataOfAny.setRecords(byName);
        return dataOfAny;
    }

//    得到全部电影信息
    public List<Course_info> getAllMovieName(){
        return productMapper.getAllCourseName();
    }


//  保存
    public boolean save(Product product){

        return 1 == productMapper.saveProduct(product);
    }
//更新
public boolean update(Product product){

    return 1 == productMapper.updateProduct(product);
}

//根据id查到信息
    public Product getOneShopping(int id ){

        return productMapper.getById(id);
    }

//    删除
    public boolean remove(int id) {
    return 1== productMapper.deleteProduct(id);
    }
}
