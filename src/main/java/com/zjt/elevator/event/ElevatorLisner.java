package com.zjt.elevator.event;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.mapper.ElevatorMapper;
import com.zjt.elevator.mapper.LogInforMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 11:08
 */
@Component
public class ElevatorLisner implements ApplicationListener<ElevatorEvent> {


    @Autowired
    private ElevatorMapper elevatorMapper;

    @Autowired
    private LogInforMapper logInforMapper;

    public ThreadLocal<LocalDateTime> threadLocal = new ThreadLocal<>();

    public static  final  Object loc = new Object();

    public AtomicInteger atomicInteger = new AtomicInteger(1);

    @SneakyThrows
    @Async
    @Override
    public void onApplicationEvent(ElevatorEvent event) {
        System.out.println("物理电梯启动");
       /* int andAdd = atomicInteger.getAndAdd(1);
        synchronized (loc) {
            List<ElevatorInfo> elevatorInfos = elevatorMapper.selectList(new QueryWrapper<>());
            for (int i = 0; i < elevatorInfos.size(); i++) {
                String url = elevatorInfos.get(i).getGet_devices_url();
                Integer status_update_success = elevatorInfos.get(i).getStatus_update_success();
                HttpResponse execute = HttpRequest.post(url).timeout(2000).execute();
                elevatorMapper.update(execute.body(),LocalDateTime.now(),elevatorInfos.get(i).getId());
                //System.out.println("执行时间"+LocalDateTime.now());
                //System.out.println("执行批次"+andAdd);
            }
        }*/
    }
}





