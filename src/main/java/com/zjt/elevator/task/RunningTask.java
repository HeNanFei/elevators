package com.zjt.elevator.task;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.mapper.ElevatorKeysMapper;
import com.zjt.elevator.mapper.ElevatorMapper;
import com.zjt.elevator.thread.ElevatorCallableLast;
import com.zjt.elevator.thread.ElevatorLastFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/15 20:30
 */
@Component
public class RunningTask {
    @Autowired
    private ElevatorMapper elevatorMapper;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private ElevatorKeysMapper elevatorKeysMapper;

    @PostConstruct
    public void doSomething(){
        List<ElevatorInfo> elevatorInfos = elevatorMapper.selectList(new QueryWrapper<>());
        for (int i = 0; i < elevatorInfos.size(); i++) {
            ElevatorCallableLast elevatorCallableLast = new ElevatorCallableLast(elevatorInfos.get(i).getId(),elevatorMapper,elevatorKeysMapper);
            threadPoolExecutor.submit(elevatorCallableLast);
        }

        /*    ElevatorCallableLast elevatorCallableLast = new ElevatorCallableLast(elevatorInfos.get(0).getId(),elevatorMapper,elevatorKeysMapper);
            threadPoolExecutor.submit(elevatorCallableLast);*/

    }
}
