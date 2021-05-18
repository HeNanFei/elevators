package com.zjt.elevator.mapper;

import com.baomidou.mybatisplus.core.mapper.*;
import com.zjt.elevator.entity.*;
import java.util.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ElevatorKeysMapper extends BaseMapper<ElevatorKeys>
{
    @Select({ "select a.* from elevator_keys a where layer = (select min(layer) from elevator_keys where elevator_id = a.elevator_id and layer_keys = 1)  order by a.elevator_id" })
   /* @Select({"select id,max(layer) layer,\n" +
            "elevator_id,\n" +
            "up_key,\n" +
            "down_key,\n" +
            "layer_keys,\n" +
            "layer_keys_execute,\n" +
            "layer_keys_execute_datetime,\n" +
            "create_time\n" +
            " from elevator_keys where layer_keys =1 GROUP BY elevator_id,id "})*/
    List<ElevatorKeys> getMinRunningTask();

    @Select({ "select a.* from elevator_keys a where layer = (select max(layer) from elevator_keys where elevator_id = a.elevator_id and layer_keys = 1)  order by a.elevator_id" })
   /* @Select({"select id,max(layer) layer,\n" +
            "elevator_id,\n" +
            "up_key,\n" +
            "down_key,\n" +
            "layer_keys,\n" +
            "layer_keys_execute,\n" +
            "layer_keys_execute_datetime,\n" +
            "create_time\n" +
            " from elevator_keys where layer_keys =1 GROUP BY elevator_id,id "})*/
    List<ElevatorKeys> getMaxRunningTask();

    @Select({ "SELECT count(1) FROM `elevator_keys`  where layer_keys = -1 and  elevator_id = #{elevator_id}" })
    Integer getTaskWaiting(Integer elevator_id);

    @Update({"update elevator_keys set layer_keys = #{layer_keys} where id = #{id} and layer = #{layer}"})
    Integer updateElevator_keysInfo(@Param("layer_keys") Integer layer_keys,@Param("id") Integer id,@Param("layer") Integer layer);

    @Select("select IFNULL(max(layer),0) layer from elevator_keys where elevator_id = #{elevatorId} and layer_keys = -1")
    Integer getHH(@Param("elevatorId") Integer elevatorId);

    @Update({"update elevator_keys set layer_keys = #{layer_keys} where   layer = #{layer} and elevator_id = #{elevator_id}"})
    Integer updateElevator_keysInfo2(@Param("layer_keys") Integer layer_keys,@Param("layer") Integer layer,@Param("elevator_id") Integer elevator_id);


    @Update({"update elevator_keys set layer_keys = #{layer_keys} where   layer <= #{layer} and elevator_id = #{elevator_id}"})
    Integer updateLessThenCurrent(@Param("layer_keys") Integer layer_keys,@Param("layer") Integer layer,@Param("elevator_id") Integer elevator_id);

    @Update({"update elevator_keys set layer_keys = #{layer_keys} where   layer >= #{layer} and elevator_id = #{elevator_id}"})
    Integer updateMoreThenCurrent(@Param("layer_keys") Integer layer_keys,@Param("layer") Integer layer,@Param("elevator_id") Integer elevator_id);

}
