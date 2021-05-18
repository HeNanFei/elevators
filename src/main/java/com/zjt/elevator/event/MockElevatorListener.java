/*
package com.zjt.elevator.event;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.zjt.elevator.mapper.*;
import java.time.*;
import java.util.concurrent.atomic.*;
import cn.hutool.extra.spring.*;
import com.zjt.elevator.thread.*;
import org.springframework.util.*;
import com.zjt.elevator.entity.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.*;
import org.springframework.context.*;

@Component
public class MockElevatorListener implements ApplicationListener<MockElevatorEvent>
{
    @Autowired
    private ElevatorMapper elevatorMapper;
    @Autowired
    private LogInforMapper logInforMapper;
    @Autowired
    private ElevatorKeysMapper elevatorKeysMapper;
    @Autowired
    private ApplicationContext applicationContext;
    private ThreadLocal<LocalDateTime> threadLocal;
    public static  Object loc;
    public AtomicInteger atomicInteger;

    public static  ConcurrentHashMap<Integer,Integer> concurrentHashMap = new ConcurrentHashMap<>();

    {
        concurrentHashMap.put(2,0);
        concurrentHashMap.put(3,0);
        concurrentHashMap.put(4,0);
    }

    public MockElevatorListener() {
        this.threadLocal = new ThreadLocal<LocalDateTime>();
        this.atomicInteger = new AtomicInteger(1);
    }

    @Async
    @Override
    public void onApplicationEvent( MockElevatorEvent event) {
        //System.out.println("mock one");
        ThreadPoolExecutor localThreadPoolExecutor = (ThreadPoolExecutor)SpringUtil.getBean("localThreadPoolExecutor");
        //System.out.println("scanning..");
        try {
             List<ElevatorKeys> runningTask = elevatorKeysMapper.getRunningTask();
            //Map<Integer, List<ElevatorKeys>> groupResult = runningTask.stream().collect(Collectors.groupingBy(n -> n.getElevator_id()));

            List<ElevatorCallable> callableList = new ArrayList<ElevatorCallable>();
            System.out.println("runningTask" + runningTask);
            if (!CollectionUtils.isEmpty((Collection)runningTask)) {
                for (int i = 0; i < runningTask.size(); ++i) {
                     //Integer taskWaiting = this.elevatorKeysMapper.getTaskWaiting(runningTask.get(i).getElevator_id());
                    if (runningTask.get(i).getLayer_keys() != 0 ) {
                        Integer taskWaiting2 = this.elevatorKeysMapper.getTaskWaiting(runningTask.get(i).getElevator_id());
                       if(taskWaiting2 == 0) {
                            //如果当前电梯为下行状态，那么电梯只下不上
                           ElevatorKeys runningElevator = runningTask.get(i);
                           ElevatorInfo elevatorInfoStatus = elevatorMapper.selectById(runningElevator.getElevator_id());
                           if(elevatorInfoStatus.getUp_down_status() == 2){
                               List<ElevatorKeys> elevatorKeys = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().lt("layer", elevatorInfoStatus.getCurrent_layer()).eq("layer_keys", -1).orderByDesc("layer_keys"));
                               ElevatorInfo elevatorInfo = elevatorMapper.selectById(elevatorKeys.get(0).getElevator_id());

                               ElevatorCallable elevatorCallable = new ElevatorCallable(elevatorKeys.get(0), elevatorInfo, this.elevatorMapper);
                               ElevatorFutureTask futureTask = new ElevatorFutureTask(elevatorCallable);
                               this.elevatorKeysMapper.updateElevator_keysInfo(-1, elevatorKeys.get(0).getId(), elevatorKeys.get(0).getLayer());
                               localThreadPoolExecutor.submit(futureTask);
                           }else {
                               //end

                               ElevatorInfo elevatorInfo = elevatorMapper.selectById(runningTask.get(i).getElevator_id());
                               ElevatorCallable elevatorCallable = new ElevatorCallable((ElevatorKeys) runningTask.get(i), elevatorInfo, this.elevatorMapper);
                               ElevatorFutureTask futureTask = new ElevatorFutureTask(elevatorCallable);
                               ElevatorKeys elevatorKeys = runningTask.get(i);
                               this.elevatorKeysMapper.updateElevator_keysInfo(-1, elevatorKeys.getId(), elevatorKeys.getLayer());
                               localThreadPoolExecutor.submit(futureTask);
                           }
                        */
/*ElevatorInfo elevatorInfo1 = futureTask.get();
                        System.out.println("执行结束"+elevatorInfo1);*//*

                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        loc = new Object();
    }
}
*/
