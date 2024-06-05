package zhang.jie.api.front;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zhang.jie.api.domain.CoverListData;
import zhang.jie.api.domain.member.Member;
import zhang.jie.api.domain.sales.Order;
import zhang.jie.api.domain.sales.OrderMapper;
import zhang.jie.api.domain.sales.shoppingData;
import zhang.jie.api.service.sale.SaleService;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouhuanping$ on 2024/5/4$.
 */

@Controller
@RequestMapping("/sales/")
public class SalesApi {

    @Autowired
    SaleService saleService;

    @PostMapping("/shopping")  //普通
    @ResponseBody
    public CoverListData shopping(@RequestBody List<Order> orderList){
        CoverListData coverListData = new CoverListData();
        saleService.handleNormal(orderList);
        return coverListData;
    }

    //会员
    @PostMapping("/shoppingByUser")  //会员
    @ResponseBody
    public CoverListData shoppingByUser(@RequestBody shoppingData data){
        CoverListData coverListData = new CoverListData();
//        扣钱
        coverListData.setCode(saleService.handleOrder(data));
        return coverListData;
    }



//验证 用户
@PostMapping("/validateUser")
@ResponseBody
public CoverListData validateUser(@RequestBody Member member){
    CoverListData coverListData = new CoverListData();
    System.out.println("member "+member);
    coverListData.setCode(saleService.validateMember(member));
    coverListData.setData(saleService.getBalance(member));
    return coverListData;
}

//座位信息
    @GetMapping("/getSeat")
    @ResponseBody
    public CoverListData getSeat(@Param("movieId") int movieId){


//      根据电影id拿到对应的座位表
        CoverListData coverListData = new CoverListData();

        coverListData.setRecords(saleService.writeSeat(movieId));
        return coverListData;
    }

//    购票 --- 普通
    @PostMapping("/buyTicket")
    @ResponseBody
    public CoverListData buyTicket(@RequestBody Map<String,Object> data){
        CoverListData coverListData = new CoverListData();

        coverListData.setCode(saleService.lockSeat(data));
        return coverListData;
    }

//    会员
@PostMapping("/buyMovie")
@ResponseBody
public CoverListData buyMovie(@RequestBody Map<String,Map<String,Object>> data){
    CoverListData coverListData = new CoverListData();

    coverListData.setCode(saleService.operationSeatOfMember(data));

    return coverListData;
}


    @Autowired
    OrderMapper orderMapper;

    //得到全部订单
    @GetMapping("/getOrderAllInfo")
    @ResponseBody
    public CoverListData getOrderAllInfo(){
//      根据电影id拿到对应的座位表
        CoverListData coverListData = new CoverListData();

        coverListData.setRecords(orderMapper.getAllOrder());
        return coverListData;
    }


//    更新票房
    @GetMapping("/updateBoxOffice")
    @ResponseBody
    public CoverListData updateBoxOffice(){
        CoverListData coverListData = new CoverListData();
        System.out.println("updateBoxOffice");
        saleService.getMovieList();
        return coverListData;
    }


}
