package com.zjt.elevator.thread;

import com.zjt.elevator.entity.ElevatorInfo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 10:03
 */
public class ElevatorFutureTask extends FutureTask<ElevatorInfo> {
    public ElevatorFutureTask(Callable<ElevatorInfo> callable) {
        super(callable);
    }

    public ElevatorFutureTask(Runnable runnable, ElevatorInfo result) {
        super(runnable, result);
    }
}
