package zhang.jie.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhang.jie.api.demos.vue.loggin.Manager;
import zhang.jie.api.demos.vue.mapper.ManagerMapper;

/**
 * Created by zhouhuanping$ on 2024/4/25$.
 */
@Service
public class loginService {


    @Autowired
    ManagerMapper managerMapper;
    public boolean login(String ph, String pw,String role) {
        Manager manager = new Manager();
            manager.setPhone(ph);
            manager.setPassword(pw);
            manager.setRole(role);
        return null != managerMapper.login(manager);

    }

    public Manager getLoginInfo(String ph, String pw,String role) {
        Manager manager = new Manager();
        manager.setPhone(ph);
        manager.setPassword(pw);
        manager.setRole(role);
        return managerMapper.login(manager);

    }


}
