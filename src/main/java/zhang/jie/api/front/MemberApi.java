package zhang.jie.api.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.member.Member;
import zhang.jie.api.service.member.MemberService;

import java.util.Map;

/**
 * Created by zhouhuanping$ on 2024/5/1$.
 */

@Controller
@RequestMapping("/members/")
public class MemberApi {


    @Autowired
    MemberService memberService;

    @PostMapping("/isSave")
    @ResponseBody
    public CoverListData isSave(@RequestBody Member member){
        CoverListData coverListData = new CoverListData();
        if (!memberService.isSaved(member)){
            coverListData.setCode("111111");
        }
        return coverListData;
    }



    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public CoverListData saveOrUpdate(@RequestBody Member member){
        CoverListData coverListData = new CoverListData();

//        更新
        if (member.getId()!=0){
            if (memberService.updateMember(member)){
                coverListData.setCode("222222");
                return coverListData;
            }
        }else {
//            保存
           if (memberService.saveMember(member)){

               return coverListData;

           }else {
               coverListData.setCode("333333");
               return coverListData;
           }
        }

        return coverListData;
    }


//    指定的member用户
@PostMapping("/getMemberByIdAndName")
@ResponseBody
public CoverListData getMemberByIdAndName(@RequestBody Member member){
    CoverListData coverListData = new CoverListData();
    coverListData.setRecords(memberService.getMemberByName(member));
    return coverListData;
}


//充值
@PostMapping("/recharge")
@ResponseBody
public CoverListData recharge(@RequestBody Member member){
    CoverListData coverListData = new CoverListData();

    if (!memberService.recharge(member)){
        coverListData.setCode("111111");
        return coverListData;
    }
    return coverListData;
}


//更改密码

    @PostMapping("/changePassword")
    @ResponseBody
    public CoverListData changePassword(@RequestBody Member member){
        CoverListData coverListData = new CoverListData();

        if (!memberService.updatePassword(member)){
            coverListData.setCode("111111");
            return coverListData;
        }
        return coverListData;
    }

//    重置密码

    @PostMapping("/resetPassword")
    @ResponseBody
    public CoverListData resetPassword(@RequestBody Member member){
        CoverListData coverListData = new CoverListData();

        System.out.println(member);
        if (!memberService.resetPassword(member)){

            coverListData.setCode("111111");
        }

        return coverListData;
    }


//    会员消费历史

    @PostMapping("/getMemberHistoryConsume")
    @ResponseBody
    public CoverListData getMemberConsumeHistory(@RequestBody Map<String ,Object> member){
        CoverListData coverListData = new CoverListData();
        Member member1 = new Member();
        member1.setName((String) member.get("name"));
        member1.setPhone((String) member.get("phone"));

        int item = (int) member.get("item");

//        模糊查询
        if (member.get("search")!=null && !member.get("search").equals("")){


            coverListData.setData(memberService.getUserPage((String)member.get("search"),member1,item));
            return coverListData;
        }



        coverListData.setData(memberService.getUserPage(member1,item));
        return coverListData;
    }


    @GetMapping("/returnTicket")
    @ResponseBody
    public CoverListData returnTicket(@RequestParam int id){
        CoverListData coverListData = new CoverListData();
        System.out.println(id);


        memberService.returnOrder(id);
        return coverListData;
    }


//


}
