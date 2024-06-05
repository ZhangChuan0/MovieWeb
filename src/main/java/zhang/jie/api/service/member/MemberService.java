package zhang.jie.api.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhang.jie.api.domain.mapper.resources.DataOfAny;
import zhang.jie.api.domain.member.Member;
import zhang.jie.api.domain.member.MemberMapper;
import zhang.jie.api.domain.sales.OrderMapper;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/5/1$.
 */

@Service
public class MemberService {



    @Autowired
    MemberMapper memberMapper;
//接收Member对象 接收查询结果
    public List<Member> getMemberList(Member member){
        return memberMapper.getMemberByNameAndPhone(member);
    }
//判断是否存在
public boolean isSaved(Member member){
    return !memberMapper.getMemberByNameAndPhone(member).isEmpty();

}
//保存
public boolean saveMember(Member member){
        if (!isSaved(member)){
            memberMapper.insertMember(member);
            return true;
        }else {
            return false;
        }
}
//更新基本信息
    public boolean updateMember(Member member){
        return 1==memberMapper.updateMember(member);
    }

//    得到单条记录
    public List<Member> getMemberByName(Member member){


        return memberMapper.getMemberByNameAndPhone(member);
    }

//    查询余额
    public Member getMemberBalance(Member member){
        return memberMapper.getBalance(member);

    }

//    充值
    public boolean recharge(Member member){

        int balance = Integer.parseInt(member.getBalance());
        return 1 == memberMapper.recharge(member.getId(),balance);

    }

//    更改和
    public boolean updatePassword(Member member){



//        更新
        return 1 == memberMapper.updatePassword(member);

    }
//    重置密码
    public boolean resetPassword(Member member){
        member.setPassword("12345678");

        return 1 == memberMapper.updatePassword(member);
    }


//    分页

    public DataOfAny getUserPage(Member member , int num){

        int id = 0;

        for (Member me:memberMapper.getMemberByNameAndPhone(member)) {
            id = me.getId();
        }

        DataOfAny data = new DataOfAny(1,7,num,memberMapper.getOrderCount(id),0);
        data.setRecords(memberMapper.getOrderByPage(id, 7*(num-1)));

        return data;
    }


    //模糊查询
    public DataOfAny getUserPage(String itemName,Member member, int num){

        itemName = "%"+itemName.trim()+"%";


        int id = 0;

        for (Member me:memberMapper.getMemberByNameAndPhone(member)) {
            id = me.getId();
        }

        DataOfAny data = new DataOfAny(1,7,num,memberMapper.getOrderCountByItem(id,itemName),0);
        data.setRecords(memberMapper.getOrderByItem(id,itemName, 7*(num-1)));

        return data;
    }


    @Autowired
    OrderMapper orderMapper;
//    退票
    public void returnOrder(int id){

        orderMapper.updateOrder(id);
    }

}
