package zhang.jie.api.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.mapper.resources.CoverOtherClass;
import zhang.jie.api.domain.sales.QueryMapper;

import java.util.Map;

/**
 * Created by zhouhuanping$ on 2024/5/26$.
 */
@RequestMapping("/query/")
@Controller
public class QueryApi {

    @Autowired
    QueryMapper queryMapper;
    @GetMapping("/getYear")
    @ResponseBody
    public CoverOtherClass getYear(@RequestParam Integer year){
        CoverOtherClass coverOtherClass = new CoverOtherClass();

        coverOtherClass.setData(queryMapper.queryDate(year));
        return coverOtherClass;
    }

    @PostMapping("/getType")
    @ResponseBody
    public CoverOtherClass getType(@RequestBody Map<String,String> type){
        System.out.println("type    "+type);
        CoverOtherClass coverOtherClass = new CoverOtherClass();

        coverOtherClass.setData(queryMapper.queryType(type.get("data")));
        return coverOtherClass;
    }

    @PostMapping("/getLanguage")
    @ResponseBody
    public CoverOtherClass getLanguage(@RequestBody Map<String,String> language){

        CoverOtherClass coverOtherClass = new CoverOtherClass();
        coverOtherClass.setData(queryMapper.queryLanguage(language.get("data")));
        return coverOtherClass;
    }

}
