package zhang.jie.api.front;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.CoverClass;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.mapper.ResourceMapper;
import zhang.jie.api.domain.mapper.resources.*;
import zhang.jie.api.domain.mapper.resources.imp.RecordsMapper;
import zhang.jie.api.domain.mapper.resources.imp.RoleMapper;
import zhang.jie.api.service.role.RoleService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/boss/")
@Controller
public class ResourceApi {

    @Autowired
    ResourceMapper resourceMapper;

    @RequestMapping("/resource/category/getAll")
    @ResponseBody
    public CoverClass getAll(){

        CoverClass coverClass = new CoverClass();
        coverClass.setCode("000000");
        coverClass.setData(resourceMapper.getAll());

        return coverClass;
    }

    @PostMapping("resource/category/saveOrEdit")
    @ResponseBody
    public boolean saveOrEdit(@RequestBody Map<String ,String> parm) {
        String name = parm.get("name");
        String order = parm.get("order");

        if (parm.get("id") == null){
            if (name.isEmpty() || order.isEmpty()){
                return false;
            }else {
                resourceMapper.saveOrEdit(name,order);
            }
        }else {
            int id = Integer.parseInt(parm.get("id"));
                resourceMapper.updateById(name,order,id);
        }

        return true;
    }

//删除接口
    @DeleteMapping("/resource/category/{id}")
    @ResponseBody
    public boolean deleteById(@PathVariable int id){
        int i = resourceMapper.deleteById(id);
        return i == 1;
    }


    // 得到资源
    @Autowired
    RecordsMapper recordsMapper;


    @PostMapping("/resource/getResourcePages")
    @ResponseBody
    CoverOtherClass getResourcePages(@RequestBody Map<String ,String> parm){
        Data data = new Data(1,1,1,10,0,5,100);
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");



        System.out.println(parm);

        if (parm.get("pageNumber") != null){
            int current = Integer.parseInt(parm.get("pageNumber"));
            data.setCurrent(current);
        }
        if (parm.get("pageSize") != null){
            int size = Integer.parseInt(parm.get("pageSize"));
            data.setSize(size);
        }

        String  id  =  parm.get("category"); //只会根据前端的category查询
//       查询
        if (!id.isEmpty()){
            int num = Integer.parseInt(id);
            data.setRecords(recordsMapper.getById(num));
              coverOtherClass.setData(data);
              return coverOtherClass;
        }

//        初始化
        List<Records> all = recordsMapper.getAll();
        data.setRecords(all);
        coverOtherClass.setData(data);

        return coverOtherClass;

    }


//    保存或更新资源
    @PostMapping("/resource/saveOrUpdate")
    @ResponseBody
    public boolean saveOrEditResource(@RequestBody Map<String ,String> parm){
        System.out.println(parm);
        if(parm.get("id")!= null && !Objects.equals(parm.get("id"), "")){
//            修改
            Records records = new Records();
            records.setId(Integer.parseInt(parm.get("id")));
            records.setUrl(parm.get("url"));
            records.setDescription(parm.get("description"));
            records.setName(parm.get("name"));

            return 1 == recordsMapper.update(records);

        }

//           更新
            Records records = new Records();
            records.setUrl(parm.get("url"));
            records.setDescription(parm.get("description"));
            records.setName(parm.get("name"));
            return 1 == recordsMapper.add(records);

    }


//    删除指定内容

    @DeleteMapping("/resource/{id}")
    @ResponseBody
    boolean deleteRecordsById(@PathVariable int id){

        return 1 == recordsMapper.deleteRecordsById(id);
    }



//   role管理

    @Autowired
    RoleMapper roleMapper;
//    获取role的信息 查询
    @PostMapping("/role/getRolePages")
    @ResponseBody
    CoverOtherClass getRolePages(@RequestBody Map<String ,String> param){


        Role role = new Role();  //查询需要容器
        int page = 1;
        int size = 10;
        //       page 当前页 size:显示页面大小
        if ( param.get("page")!= null && !param.get("page").isEmpty()){
            page = Integer.parseInt(param.get("page"));

        }

        if (param.get("size")!= null && !param.get("size").isEmpty()){

            size = Integer.parseInt(param.get("size"));

        }


//        初始化  分页依据
        DataOfAny data = new DataOfAny(1,page,size,roleMapper.getTotalRoles(),0);
        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setCode("000000");
//        根据name搜索
        if (param.get("name") != null && !param.get("name").isEmpty()) {
            String nameParam = "%" + param.get("name") + "%";
            role.setName(nameParam);
            List<Role> roleByName = roleMapper.getRoleByName(role);
            data.setRecords(roleByName);
            coverOtherClass.setData(data);
            return coverOtherClass;
        }

//        初始化


        List<Role> all = roleMapper.getRoleByPage(size,size*(page-1));
        data.setRecords(all);
        coverOtherClass.setData(data);

        return coverOtherClass;
    }




//    保存修改role

    @PostMapping("/role/saveOrUpdate")
    @ResponseBody
    public boolean saveOrUpdateRole(@RequestBody Map<String ,String> param){
        String name = param.get("name");
        String  description = param.get("description");

//        判空
        if (name.trim().isEmpty() || description.trim().isEmpty()){
            return false;
        }

        Role role = new Role();
        role.setDescription(description);
        role.setName(name);
        System.out.println(param);
//        修改
        if (param.get("id") != null && !param.get("id").isEmpty()){
            role.setId(Integer.parseInt(param.get("id")));
            return 1 == roleMapper.updateRole(role);
        }
//        新建
        return 1== roleMapper.saveRole(role);

    }
//删除role中数据
    @DeleteMapping("/role/remove/{id}")
    @ResponseBody
    public boolean removeRole(@PathVariable int id){
        return  1 == roleMapper.deleteRole(id);
    }



    @Autowired
    RoleService roleService;

//    得到role所关联的menu信息
    @GetMapping("/role/getRoleMenus")
    @ResponseBody
    CoverListData getRoleMenus(@RequestParam("roleId") String param){

        int id = Integer.parseInt(param);


        return roleService.roleMenu(id);

    }

//    分配role的menus
@PostMapping("/role/allocateRoleMenus")
@ResponseBody
public boolean allocateRoleMenus(@RequestBody Map<String ,Object> param ){


    // 在这里获取 requestBody 中的 roleId 和 resourceIdList 并进行处理
    return roleService.getParam(param.toString(),"menuList");


}




//    重新分配Role资源
    @PostMapping("/role/allocateRoleResource")
    @ResponseBody
    public boolean allocateRoleResource(@RequestBody Map<String ,Object> param ){


        // 在这里获取 requestBody 中的 roleId 和 resourceIdList 并进行处理
        return roleService.getParam(param.toString(),"resourceIdList");


    }


}


