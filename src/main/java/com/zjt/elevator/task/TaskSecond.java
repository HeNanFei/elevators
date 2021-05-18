package com.zjt.elevator.task;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.mapper.ElevatorMapper;
import com.zjt.elevator.mapper.LogInforMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;




@Component
public class TaskSecond {

    @Autowired
    private ElevatorMapper elevatorMapper;

    @Autowired
    private LogInforMapper logInforMapper;

    private ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    public static  final  Object loc = new Object();

    public AtomicInteger atomicInteger = new AtomicInteger(1);

    @Scheduled(cron = "0/20 * * * * ?")
    public void doTask(){
        int andAdd = atomicInteger.getAndAdd(1);
        synchronized (loc) {
           // List<ElevatorInfo> elevatorInfos = elevatorMapper.selectList(new QueryWrapper<ElevatorInfo>().eq("elevator_mode", 1));
            List<ElevatorInfo> elevatorInfos = elevatorMapper.selectList(new QueryWrapper<ElevatorInfo>());
            if (!CollectionUtils.isEmpty(elevatorInfos)) {
                ElevatorInfo elevatorInfo = elevatorInfos.get(0);
                System.out.println("物理电梯启动");
                if (elevatorInfo.getStatus_updatetime() == null || Duration.between(elevatorInfo.getStatus_updatetime(), LocalDateTime.now()).getSeconds() > 45) {
                    //String url = elevatorInfos.get(i).getGet_devices_url();
                    System.out.println("start awaking");
                    String url = "http://39.108.153.214/admin/app/dispatch_system/elevator_itlong_single_run.php";
                    //Integer status_update_success = elevatorInfos.get(i).getStatus_update_success();
                    HttpResponse execute = HttpRequest.get(url).timeout(2000).execute();
                    elevatorMapper.update(execute.body(), LocalDateTime.now(), elevatorInfos.get(0).getId());
                    System.out.println("执行时间" + LocalDateTime.now());
                    //System.out.println("执行批次"+andAdd);
                }
            }
        }
        //System.out.println(elevatorInfos);
    }
}

