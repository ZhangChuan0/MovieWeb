package zhang.jie.api.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhang.jie.api.demos.vue.loggin.Manager;
import zhang.jie.api.demos.vue.mapper.ManagerMapper;
import zhang.jie.api.domain.users.UserRole;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhouhuanping$ on 2024/4/24$.
 */

@Service
public class ManagerService {
    @Autowired
    ManagerMapper managerMapper;


//    得到全部的职员信息
    public ArrayList<Manager> getAll(){

        return managerMapper.getAllStaff();
    }

//    职位信息
    public ArrayList<UserRole> getAllPosition(){

        return managerMapper.getAllPosition();
    }

//    改变登录权限

    public boolean changeStaffLogin(String status,String id){

        return 1==managerMapper.updateRole(status, Integer.parseInt(id));
    }


//    模糊查询姓名或职位
    public ArrayList<Manager> getByRough(String name,ArrayList<String> position){

        Iterator<String> iterator = position.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            if (next.isEmpty()){
                iterator.remove();
            }
        }
        name ="%"+name+"%";
        return managerMapper.getByBlur(name,position);
    }

//    处理前端的一坨数据

    public Manager getAllStaffFromFront(Map<String ,Object> param){
        Manager manager = new Manager();
        for (Map.Entry<String, Object> entry : param.entrySet()){
            if (entry.getValue()!=null && !entry.getValue().equals("")){
                if (entry.getKey().equals("id")){
                    manager.setId(Integer.parseInt(entry.getValue().toString()));
                }
                if (entry.getKey().equals("name")){
                    manager.setName(entry.getValue().toString());
                }
                if (entry.getKey().equals("position")){
                    manager.setPosition(entry.getValue().toString());
                }
                if (entry.getKey().equals("password")){
                    manager.setPassword(entry.getValue().toString());
                }
                if (entry.getKey().equals("phone")){
                    manager.setPhone(entry.getValue().toString());
                }
                if (entry.getKey().equals("salary")){
                    manager.setSalary_info(Integer.parseInt(entry.getValue().toString()));
                }
                if (entry.getKey().equals("note")){
                    manager.setNote(entry.getValue().toString());
                }
                if (entry.getKey().equals("data_of_birth")){
                    manager.setData_of_birth(entry.getValue().toString());
                }
                if (entry.getKey().equals("gender")){
                    manager.setGender(entry.getValue().toString());
                }
                if (entry.getKey().equals("portrait")){
                    manager.setPortrait(entry.getValue().toString());
                }
            }


        }




        return manager;
    }

//    保存数据

    public boolean saveStaff(Map<String ,Object> param) {

      return 1==managerMapper.saveManager(getAllStaffFromFront(param));

    }

//    更新

    public boolean updateStaff(Map<String ,Object> param) {

        return 1 == managerMapper.updateManagerById(getAllStaffFromFront(param));

    }



//    查找某id员工
    public Manager getStaffById(int id){
        return managerMapper.getManagerById(id);
    }


//    删除
    public boolean deleteStaff(int id){

        return 1==managerMapper.deleteManagerById(id);
    }

}
