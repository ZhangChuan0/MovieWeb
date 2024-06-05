package zhang.jie.api.service.set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhang.jie.api.domain.mapper.resources.Role;
import zhang.jie.api.domain.set.SetMapper;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/4/30$.
 */

@Service
public class SetService {
    @Autowired
    SetMapper setMapper;

//语言
    public List<Role> getLanguageList(){

        return setMapper.getLanguage();

    }
//    影厅
public List<Role> getRoomList(){

    return setMapper.getRoom();
}

    public List<Role> getPositionList(){

        return setMapper.getPosition();
    }



}
