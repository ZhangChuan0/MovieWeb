package zhang.jie.api.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.service.manager.ManagerService;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zhouhuanping$ on 2024/4/24$.
 */

@Controller
@RequestMapping("/manager/")
public class ManagerApi {
    @Autowired
    ManagerService managerService;


//    职员的查询和展示
    @PostMapping("/saveOrEdit")
    @ResponseBody
    public CoverListData saveOrEdit(@RequestBody Map<String,Object> param){
        CoverListData coverListData = new CoverListData();
        System.out.println(param);

        String name="";
        int i = 0;
        ArrayList<String> position = new ArrayList<>();

        if (param.get("name")!=null && !param.get("name").equals("") || param.get("position")!=null ){
        name = param.get("name").toString();
        position = (ArrayList<String>) param.get("position");

           coverListData.setRecords(  managerService.getByRough(name, position));
            coverListData.setData(managerService.getAllPosition());
            return coverListData;

        }

        coverListData.setRecords(managerService.getAll());
        coverListData.setData(managerService.getAllPosition());

        return coverListData;
    }


//    改变登录状态
    @PostMapping("/permission/changeLogin")
    @ResponseBody
    public CoverListData changeLogin(@RequestBody Map<String,String> param){
        CoverListData coverListData = new CoverListData();
        System.out.println(param);

        if (managerService.changeStaffLogin(param.get("act"),param.get("userId"))){
            return coverListData;
        }else {
            coverListData.setCode("1111111");
            return coverListData;
        }

    }



//    保存或更新职员信息

    @PostMapping("/saveOrUpdateStaff")
    @ResponseBody
    public CoverListData saveOrUpdateStaff(@RequestBody Map<String,Object> param){
        CoverListData coverListData = new CoverListData();
//        更新
        if(param.get("id")!=null && !param.get("id").equals("")){

            managerService.getStaffById(Integer.parseInt(param.get("id").toString()));

            managerService.updateStaff(param);
            coverListData.setData("更新成功");
            return coverListData;
        }else {

            //        保存
            boolean b = managerService.saveStaff(param);
            coverListData.setData("保存成功");
            return coverListData;
        }


    }


    @GetMapping("/getManagerById")
    @ResponseBody
    public CoverListData getManagerById(@RequestParam("managerId") String id){
        CoverListData coverListData = new CoverListData();
        System.out.println("前端的id"+id);

         coverListData.setData(managerService.getStaffById(Integer.parseInt(id)));
        return coverListData;
    }

//    删除员工


 @DeleteMapping("/deleteStaff")
    @ResponseBody
    public CoverListData deleteStaff(@RequestParam("id") String id){
     CoverListData coverListData = new CoverListData();
     if (managerService.deleteStaff(Integer.parseInt(id))){
            return coverListData;
        }
     coverListData.setCode("111111");

        return coverListData;
 }

}
