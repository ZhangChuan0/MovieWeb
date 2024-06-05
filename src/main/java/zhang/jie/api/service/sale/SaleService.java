package zhang.jie.api.service.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhang.jie.api.domain.Seat;
import zhang.jie.api.domain.mapper.courses.Course_info;
import zhang.jie.api.domain.member.Member;
import zhang.jie.api.domain.sales.Order;
import zhang.jie.api.domain.sales.OrderMapper;
import zhang.jie.api.domain.sales.shoppingData;
import zhang.jie.api.domain.users.imp.SeatMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zhouhuanping$ on 2024/5/4$.
 */

@Service
public class SaleService {

//    验证会员

    @Autowired
    OrderMapper orderMapper;

    public String validateMember(Member member){
       if (null != orderMapper.getUser(member)){
           return "000000";
       }
       return "111111";
    }

//    拿到会员的余额

    public String getBalance(Member member){
        if (validateMember(member).equals("000000")){
            return orderMapper.getUser(member).getBalance();
        }
        return "0";
    }


//    处理前端的订单信息----会员---根据价格扣掉余额
public String handleOrder(shoppingData data){

    List<Order> shopping = data.getShopping();
    Member user = data.getUser();

    int shoppingValue = 0,balance = 0;

    balance = Integer.parseInt(getBalance(user));

    for (Order order:shopping){
        System.out.println("line ---->"+order);
        shoppingValue=Integer.parseInt(order.getTotalAmount());
    }
//        判断是否足够支付

    if (balance-shoppingValue >=0) {
//        扣费
        user.setBalance(String.valueOf(balance-shoppingValue));
        int i = orderMapper.updateBalance(user);
        operationStock(data);//处理库存减少
        writeOrder(data);
        return "000000";
    }

    return "111111";
}

//判断库存和本次的消费是否冲突tool 和food的余额

    public void operationStock(shoppingData data){
        List<Order> shopping = data.getShopping();

        for (Order or :shopping){
          if (or.getItem_type().equals("tool")){
              if (orderMapper.getStock(or.getId()) - Integer.parseInt(or.getItem_total()) >=0 ){
                  int i = orderMapper.getStock(or.getId()) - Integer.parseInt(or.getItem_total());
                  orderMapper.updateStock(or.getId(),i);
              }

          }else if (or.getItem_type().equals("food")){
              if (Integer.parseInt(orderMapper.getPassword(or.getId())) - Integer.parseInt(or.getItem_total()) >=0 ){
                  int i = Integer.parseInt(orderMapper.getPassword(or.getId())) - Integer.parseInt(or.getItem_total());
                  orderMapper.updatePassword(or.getId(), String.valueOf(i));
              }

          }else {
              //pass 电影
          }

        }


    }


//    普通支付方式
 public void handleNormal(List<Order> orderList){
     int times = 0;

     for (Order or :orderList){
         if (or.getItem_type().equals("tool")){
             if (orderMapper.getStock(or.getId()) - Integer.parseInt(or.getItem_total()) >=0 ){
                 int i = orderMapper.getStock(or.getId()) - Integer.parseInt(or.getItem_total());
                 orderMapper.updateStock(or.getId(),i);
                 times++;
             }

         }else if (or.getItem_type().equals("food")){
             if (Integer.parseInt(orderMapper.getPassword(or.getId())) - Integer.parseInt(or.getItem_total()) >=0 ){
                 int i = Integer.parseInt(orderMapper.getPassword(or.getId())) - Integer.parseInt(or.getItem_total());
                 orderMapper.updatePassword(or.getId(), String.valueOf(i));
                 times++;
             }

         }else {
             //pass 电影
         }


     }

     if (orderList.size() == times){
         writeOrder(orderList);
     }



 }

// 写入订单

    public void writeOrder(shoppingData data){ //会员订单

        List<Order> shopping = data.getShopping();
        Member user = data.getUser();

        int id = orderMapper.getUser(user).getId();


        for (Order or :shopping){
            or.setUserId(id);
            or.setStatus("已完成");
            or.setRemark("会员");
            orderMapper.insertOrder(or);
        }

    }
    public void writeOrder(List<Order> orderList){ //普通订单


        for (Order or :orderList){
            or.setUserId(0000000000);
            or.setStatus("已完成");
            or.setRemark("普通");
            orderMapper.insertOrder(or);
        }



    }

//    座位信息
    @Autowired
    SeatMapper seatMapper;
    public List<Seat> writeSeat(int movieId){
        return seatMapper.getAllSeat(movieId);
    }



//    分离座位信息，将需要买入的拿出来
public  List<Seat> operationSeat(Map<String,Object> data){
    List<LinkedHashMap<String, Object>> ordered = (List<LinkedHashMap<String, Object>>) data.get("ordered");
    List<Integer> o = (List) data.get("buy");

    List<Seat> seatList = new ArrayList<>();
    for (LinkedHashMap<String, Object> map : ordered) {
        Seat seat = new Seat();
        seat.setId((int) map.get("id"));
        seat.setStatus((String) map.get("status"));
        // 如果还有其他属性，继续添加

        seatList.add(seat);
    }

    List<Seat> collect = seatList.stream().filter(e -> e.getStatus().equals("-1")).collect(Collectors.toList());

    for (Integer s: o) {
        Seat seat = new Seat();
        seat.setId(s);
        seat.setStatus("-1");
        collect.add(seat);
    }

//    普通--直接锁单创建出订单

//    会员---加上会员信息创建订单

return collect;
}

//锁定座位
    public String lockSeat(Map<String,Object> data){

        List<Seat> collect = operationSeat(data);
        int i = 0 ,j = collect.size();
        for (Seat seat : collect) {
            i += seatMapper.updateSeat(seat);
        }
        if (i == j){
            createMovieOrder(data);
            return "000000";
        }
        return "111111";

    }


//    创建电影订单    //        普通订单信息

    public void createMovieOrder(Map<String,Object> data){
        int userId = (int) data.get("userId");
        String type = (String) data.get("type");
        int price = (int) data.get("price");
        int movie_id = (int) data.get("movie");
        String payMethod = (String) data.get("payMethod");
        List<Integer> o = (List) data.get("buy");

        Order order = new Order();
        if (userId == 0){
        userId = 0000000000;

        }
        order.setUserId(userId);
        order.setItem_name(orderMapper.getCourseName(movie_id));

        order.setRemark(changeSeat(o));

        order.setStatus("已支付");
        order.setItem_total(String.valueOf(o.size())); //几张票
        order.setPayMethod(payMethod);
        order.setPrice_type(type);
        order.setItem_type("movie");
        order.setTotalAmount(String.valueOf(price*o.size()));  //总金额

        orderMapper.insertOrderMovie(order);

    }

//    座位号改名字
    public String changeSeat(List<Integer> data){
        List<String> seatList = orderMapper.getSeat(data);
        List<String> formattedSeatList = new ArrayList<>();

        for (String seat : seatList) {
            String formattedSeat = seat.replace("-", "排") + "座";
            formattedSeatList.add(formattedSeat);
        }

        return formattedSeatList.toString();
    }



//    会员通道
    public String operationSeatOfMember(Map<String,Map<String,Object>> data){
        Member member = new Member();

        Map<String, Object> requestData = data.get("data");
        Map<String, Object> user = data.get("user");

        List<Seat> collect = operationSeat(requestData);

        member.setName((String) user.get("name"));
        member.setPassword((String) user.get("password"));

        int price = (int) requestData.get("price");

        List<Integer> o = (List) requestData.get("buy");

        requestData.replace("userId", orderMapper.getId(member));

        System.out.println("user-info"+member);
        System.out.println(orderMapper.getUser(member).getBalance());
        System.out.println("price"+price+"total"+o.size()+"="+price*o.size());


        if (Integer.parseInt(orderMapper.getUser(member).getBalance())-price*o.size()<0){
            return "111111";
        }
        member.setBalance(String.valueOf(Integer.parseInt(orderMapper.getUser(member).getBalance())-price*o.size()));

//        锁定位置了
        int i = 0 ,j = collect.size();
        for (Seat seat : collect) {
            i += seatMapper.updateSeat(seat);
        }

        if (i == j){
            orderMapper.updateBalance(member);
            createMovieOrder(requestData);
            return "000000";
        }

        return "111111";

    }

    public void getMovieList() {


        // 获取全部电影信息
        List<Course_info> movieList = orderMapper.getAllName();

        // 获取全部订单信息
        List<Order> orderList = orderMapper.getOrder();

        ArrayList<Course_info> objects = new ArrayList<>();
        // 遍历电影列表
        movieList.forEach(movie -> {
            String movieName = movie.getCourseName(); // 获取电影名称
            int totalAmount = 0; // 用于统计总销售额

            // 遍历订单列表，找到与当前电影名称相同的订单项，并统计销售额
            for (Order order : orderList) {
                if (order.getItem_name().equals(movieName)) {
                    totalAmount += Integer.parseInt(order.getTotalAmount());
                }
            }

            // 判断总销售额是否大于电影的活动库存
            if (totalAmount > movie.getActivityStock()) {
                // 如果是，则创建新的电影信息，并存入数据库
                Course_info courseInfo = new Course_info();
                courseInfo.setCourseName(movieName);
                courseInfo.setActivityStock(totalAmount);
                objects.add(courseInfo);
            }
        });

        System.out.println(objects);

        for (Course_info object : objects){
            orderMapper.updateCourse_large(object);
        }


    }




}
