package zhang.jie.api.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.mapper.resources.Role;
import zhang.jie.api.domain.set.SetMapper;
import zhang.jie.api.service.set.SetService;

/**
 * Created by zhouhuanping$ on 2024/4/30$.
 */

@Controller
@RequestMapping("/setting/")
public class Setting {

    @Autowired
    SetService setService;

    @GetMapping("/getLanguage")
    @ResponseBody
    public CoverListData getLanguage(){
        CoverListData coverListData = new CoverListData();

        coverListData.setRecords(setService.getLanguageList());

        return coverListData;

    }

//厅号
    @GetMapping("/getRoom")
    @ResponseBody
    public CoverListData getRoom(){
        CoverListData coverListData = new CoverListData();

        coverListData.setRecords(setService.getRoomList());

        return coverListData;

    }

//岗位
@GetMapping("/getPosition")
@ResponseBody
public CoverListData getPosition(){
    CoverListData coverListData = new CoverListData();

    coverListData.setRecords(setService.getPositionList());

    return coverListData;

}

    @Autowired
    SetMapper setMapper;
    //====================保存
@PostMapping("/saveSetting")
@ResponseBody
    public CoverListData saveSetting(@RequestBody Role role){
//        更新{"id":0,"name":"张三","description":"position"}
    CoverListData coverListData = new CoverListData();
    System.out.println("更新"+role);
    String name = role.getName();
    String description = role.getDescription();
//    更新
    if (role.getId()!=0){
        int id = role.getId();
        if (description.equals("position")){
         setMapper.updatePosition(name,id);
        }else if (description.equals("room")){
            setMapper.updateRoom(name,id);
        }else if (description.equals("language")){
            setMapper.updateLanguage(name,id);
        }

    }else {
        if (description.equals("position")){
            setMapper.savePosition(name);
        }else if (description.equals("room")){
            setMapper.saveRoom(name);
        }else if (description.equals("language")){
            setMapper.saveLanguage(name);
        }
    }

    return coverListData;
}

    //查找单个
    @PostMapping("/queryById")
    @ResponseBody
    public CoverListData queryById(@RequestBody Role role){
//        查找单个{"id":44771,"name":"","description":"position"}
        CoverListData coverListData = new CoverListData();

        String description = role.getDescription();
        int id = role.getId();

        if (description.equals("position")){
            coverListData.setData(setMapper.getPositionById(id));
        }else if (description.equals("room")){
            coverListData.setData(setMapper.getRoomById(id));
        }else if (description.equals("language")){
            coverListData.setData(setMapper.getLanguageById(id));
        }

        return coverListData;
    }

//删除
@PostMapping("/deleteById")
@ResponseBody
public CoverListData deleteById(@RequestBody Role role){
//        删除{"id":44771,"description":"position"}
    CoverListData coverListData = new CoverListData();

    String description = role.getDescription();
    int id = role.getId();

    if (description.equals("position")){
       setMapper.deletePosition(id);
    }else if (description.equals("room")){
        setMapper.deleteRoom(id);
    }else if (description.equals("language")){
        setMapper.deleteLanguage(id);
    }

    System.out.println("删除"+role);
    return coverListData;
}



}
