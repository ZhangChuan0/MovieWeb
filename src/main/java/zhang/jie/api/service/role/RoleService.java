package zhang.jie.api.service.role;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.mapper.role.CoverOfName;
import zhang.jie.api.domain.mapper.role.Name;
import zhang.jie.api.domain.mapper.role.iml.NameMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    NameMapper nameMapper;

    public CoverListData roleMenu(int id) {

        CoverOfName menu = new CoverOfName(id);
        CoverOfName time = new CoverOfName(id);
        CoverListData coverListData = new CoverListData();
        Name mItem = new Name(id);
        Name TItem = new Name(id);
        menu.setName("菜单管理");
        time.setName("时间管理");
        menu.setRecords(nameMapper.getMenuAll(mItem));
        time.setRecords(nameMapper.getTimeAll(TItem));
        coverListData.addOne(menu);
        coverListData.addOne(time);
        return coverListData;
    }

    public boolean getParam(String param,String name) {

        Gson gson = new Gson();

        // 解析 JSON 字符串
        JsonObject jsonObject = gson.fromJson(param, JsonObject.class);
        JsonArray menuList = jsonObject.getAsJsonArray(name);
        int  roleId  = jsonObject.get("roleId").getAsInt();

        List objects = new ArrayList<>();

        for (JsonElement element : menuList) {
            if (element.isJsonNull()) {
                // 处理 JsonNull 对象的情况
                continue;
            } else {
                int index = element.getAsInt();
                objects.add(index);
            }

        }

        if (!objects.isEmpty()){
            nameMapper.updateMenuAll1(objects,roleId);
            nameMapper.updateTimeAll1(objects,roleId);
            return true;
        }
       return false;


    }
}
