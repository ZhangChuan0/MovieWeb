package zhang.jie.api.front;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.CoverMenu;
import zhang.jie.api.domain.Menu;
import zhang.jie.api.domain.WebToken;
import zhang.jie.api.domain.equipment.CoverBook;
import zhang.jie.api.domain.mapper.MenuMapper;
import zhang.jie.api.domain.mapper.UserMapper;
import zhang.jie.api.domain.mapper.WebTokenMapper;
import zhang.jie.api.domain.mapper.book.Book;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller()
@RequestMapping("/data/")
public class DateApi {

    @Autowired
    private UserMapper userMapper;

//    get 请求 也可以发送json格式文件
    @GetMapping("/{age}")
    @ResponseBody
    public String getById(@PathVariable(value = "age",required = false) int age){
        try
        {
            return userMapper.findById(age).toString();
        }catch (Exception e){

            System.err.println(e+"未知错误");
        }
            return "{\"code\":\"error\""+"}";

    }


    @Autowired
    private WebTokenMapper webTokenMapper;

    @PostMapping("/web")
    @ResponseBody
    public List<WebToken> getAll(@RequestBody Map<String,Integer> parm){
           List<WebToken> all = webTokenMapper.getAll();
            Iterator<WebToken> iterator = all.iterator(); //迭代器 不破坏结构情况下改变数据
        int count = webTokenMapper.getCount();


        while (iterator.hasNext()){
                WebToken webToken = iterator.next();
                if (parm.get("state") == webToken.getState()){
                    iterator.remove();
                    count--;
                }
            }
        if (count == webTokenMapper.getCount()){
            return null;
        }

        return all;
    }


//    post
    @PostMapping("/byId")
    @ResponseBody
    public WebToken colo(@RequestBody Map<String,Integer> parm){

        return webTokenMapper.columnsCount(parm.get("id"));

    }




//    book和product表一起使用
    @Autowired
    CoverBook coverBook;

    @RequestMapping("/book")
    @ResponseBody
   public List<Book> getAllBook(){
        return coverBook.getAllBook();
    }

    @Autowired
    MenuMapper menuMapper;


    @RequestMapping("/boss/menu/getALL")
    @ResponseBody
    public CoverMenu getAllMenus(){

        List<Menu> all = menuMapper.getAll();
/*
        采用手工模拟的方式
        String code = "{\"code\":\"8080\"}";
        JsonObject gsonObject = JsonParser.parseString(code).getAsJsonObject();
        Map<String, Object> map = new Gson().fromJson(gsonObject, new TypeToken<Map<String, Object>>(){}.getType());
        all.add(map);

        */

        CoverMenu coverMenu = new CoverMenu();
        coverMenu.setCode("000000");
        coverMenu.setData(all);

        return coverMenu;
    }


//    前端的增加菜单元素

    @PostMapping("/boss/menu/saveOrUpdate")
    @ResponseBody
    public boolean saveOrEdit(@RequestBody Map<String,String> parm){

//        解决重复提交问题--在这儿统一
        Menu menu = new Menu();
        if (parm.get("name").isEmpty()){
            return false;
        }

        menu.setName(parm.get("name"));
        menu.setSize(Double.parseDouble(parm.get("size")));
        menu.setOrder(parm.get("order"));
        menu.setIcon(parm.get("icon"));
        int code = menuMapper.saveOrEdit(menu);
        return code == 1;

    }

//删除请求
    @DeleteMapping("/boss/menu/{id}")
    @ResponseBody
    public boolean deleteByid(@PathVariable Integer id){

    int code = menuMapper.deleteById(id);

        return code == 1;
    }

    @GetMapping("/boss/menu/getEditMenuInfo")
    @ResponseBody
    public Menu getEditInfoById(@RequestParam int id){

        return menuMapper.getInfoById(id);

    }
}
