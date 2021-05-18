package com.zjt.elevator.task;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.entity.ElevatorKeys;
import com.zjt.elevator.event.ElevatorEvent;
import com.zjt.elevator.mapper.ElevatorKeysMapper;
import com.zjt.elevator.mapper.ElevatorMapper;
import com.zjt.elevator.mapper.LogInforMapper;
import com.zjt.elevator.thread.ElevatorCallable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 9:26
 */
/*
@Component
public class TaskThird {
    @Autowired
    private ElevatorMapper elevatorMapper;

    @Autowired
    private LogInforMapper logInforMapper;

    @Autowired
    private ElevatorKeysMapper elevatorKeysMapper;


    @Autowired
    private ApplicationContext applicationContext;

    private ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    public static  final  Object loc = new Object();

    public AtomicInteger atomicInteger = new AtomicInteger(1);



    @Scheduled(cron = "0/1 * * * * ?")
    public void doSomething() throws InterruptedException {
        ThreadPoolExecutor localThreadPoolExecutor = SpringUtil.getBean("localThreadPoolExecutor");
        */
/*List<ElevatorKeys> elevatorKeys = elevatorKeysMapper.selectList(new QueryWrapper<>());
        System.out.println(elevatorKeys);*//*

        System.out.println("scanning..");
        try{
        List<ElevatorKeys> runningTask = elevatorKeysMapper.getRunningTask();
        List<ElevatorCallable> callableList = new ArrayList<>();
        System.out.println("runningTask"+runningTask);
        if(!CollectionUtils.isEmpty(runningTask)){
            for (int i = 0; i < runningTask.size(); i++) {
                Integer taskWaiting = elevatorKeysMapper.getTaskWaiting(runningTask.get(i).getElevator_id());
                if ( runningTask.get(i).getLayer_keys() == 1 && taskWaiting == 0) {
                    ElevatorInfo elevatorInfo = elevatorMapper.selectById(runningTask.get(i).getElevator_id());
                    ElevatorCallable elevatorCallable = new ElevatorCallable(runningTask.get(i), elevatorInfo, elevatorMapper);
                    FutureTask futureTask = new FutureTask(elevatorCallable);

                    ElevatorKeys elevatorKeys = runningTask.get(i);
                    elevatorKeys.setLayer_keys(-1);
                    elevatorKeysMapper.updateById(elevatorKeys);
                    localThreadPoolExecutor.submit(futureTask);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
*/
