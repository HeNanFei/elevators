package com.zjt.elevator.thread;

import com.zjt.elevator.entity.ElevatorInfo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/15 20:36
 */

public class ElevatorLastFuture extends FutureTask<String> {
    public ElevatorLastFuture(Callable<String> callable) {
        super(callable);
    }

    public ElevatorLastFuture(Runnable runnable, String result) {
        super(runnable, result);
    }



}
