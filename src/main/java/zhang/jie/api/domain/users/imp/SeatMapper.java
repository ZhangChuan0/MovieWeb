package zhang.jie.api.domain.users.imp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zhang.jie.api.domain.Seat;

import java.util.List;

/**
 * Created by zhouhuanping$ on 2024/5/5$.
 *
 */

@Mapper
public interface SeatMapper {

//    全部位置信息
    @Select("select * from seat where movie_id=#{id}")
    List<Seat> getAllSeat(int id);

//    将指定id的座位状态改为已售就是-1 是一个集合的形式
    @Update("update seat set status = '-1' where id=#{id}")
    int updateSeat(Seat seat);

}
