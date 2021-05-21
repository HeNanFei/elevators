package com.zjt.elevator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjt.elevator.entity.ElevatorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/4 21:15
 */
@Mapper
public interface ElevatorMapper extends BaseMapper<ElevatorInfo> {

    @Update("update elevator_info set get_devices_result = #{result}, status_updatetime = #{updateTime} where id = #{id}")
    public void update(@Param("result") String result,@Param("updateTime") LocalDateTime updateTime,@Param("id") Integer id);

    @Update("update elevator_info set current_layer = #{current_layer},up_down_status = #{up_down_status} where id = #{id}")
    public void updateStatus(@Param("current_layer") Integer current_layer,@Param("up_down_status") Integer up_down_status,@Param("id") Integer id);

    @Update("update elevator_info set door_status = #{door_status} where id = #{id}")
    public void updatedoor_status(@Param("door_status") Integer door_status,@Param("id") Integer id);
}
