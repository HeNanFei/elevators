/*package com.zjt.elevator.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

*//**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/21 21:06
 *//*
@Component
public class OtherTest {

    @Qualifier(value = "schedulePoolExecutor")
    @Autowired
    private ScheduledThreadPoolExecutor schedulePoolExecutor;

    @PostConstruct
    public void any(){
        schedulePoolExecutor.scheduleAtFixedRate(new Thread(() -> System.out.println("jjjjjjjjjjjjjjjjjj")),20,20, TimeUnit.MILLISECONDS);
    }
}*/
