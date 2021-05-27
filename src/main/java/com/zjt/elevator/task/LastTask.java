package com.zjt.elevator.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.entity.ElevatorKeys;
import com.zjt.elevator.event.ElevatorEvent;
import com.zjt.elevator.event.MockElevatorEvent;
import com.zjt.elevator.mapper.ElevatorKeysMapper;
import com.zjt.elevator.mapper.ElevatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/10 12:23
 */
@Component
public class LastTask {

    @Autowired
    private ElevatorMapper elevatorMapper;

    @Autowired
    private ElevatorKeysMapper elevatorKeysMapper;

    @Autowired
    private ApplicationContext applicationContext;

   /* @Scheduled(cron = "0/1 * * * * ?")
    public void doTask(){
        //0模拟电梯 1 物理电梯
        List<ElevatorInfo> elevator_mode = elevatorMapper.selectList(new QueryWrapper<ElevatorInfo>().eq("elevator_mode", 0));
        if(CollectionUtils.isEmpty(elevator_mode)) {
            applicationContext.publishEvent(new ElevatorEvent(applicationContext));
        }else{
            applicationContext.publishEvent(new MockElevatorEvent(applicationContext));
        }*/
   // }
}
