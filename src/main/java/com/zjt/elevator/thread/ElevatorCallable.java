package com.zjt.elevator.thread;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.entity.ElevatorKeys;
import com.zjt.elevator.event.ElevatorLisner;
import com.zjt.elevator.mapper.ElevatorKeysMapper;
import com.zjt.elevator.mapper.ElevatorMapper;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 10:13
 */
public class ElevatorCallable implements Callable<ElevatorInfo> {

    private ElevatorInfo elevatorInfo;

    private ElevatorMapper elevatorMapper;

    private ElevatorKeys elevatorKeys;

    private static Object object = new Object();

    public static double stop_time = 0;

    public ThreadLocal<Map<Integer,Integer>> mapThreadLocal = new ThreadLocal<>();

    public static ConcurrentHashMap<Integer,Integer> concurrentHashMap = new ConcurrentHashMap<>();

    {
        concurrentHashMap.put(4,0);
        concurrentHashMap.put(2,0);
        concurrentHashMap.put(3,0);
        mapThreadLocal.set(concurrentHashMap);

    }

    public ElevatorCallable() {
    }

    public ElevatorCallable(ElevatorKeys elevatorKeys,ElevatorInfo elevatorInfo,ElevatorMapper elevatorMapper) {
        this.elevatorInfo = elevatorInfo;
        this.elevatorMapper = elevatorMapper;
        this.elevatorKeys = elevatorKeys;
    }

    @Override
    public ElevatorInfo call() throws Exception {
        //if(concurrentHashMap.get(elevatorKeys.getElevator_id()) != -1) {
            //停止时间
            stop_time = elevatorInfo.getTime_stop_layer();

            ElevatorKeysMapper keyMapper = SpringUtil.getBean(ElevatorKeysMapper.class);
            int curr = elevatorInfo.getCurrent_layer();// 当前楼层
            Integer up_down_status = elevatorInfo.getUp_down_status();//电梯状态 0停层  1上行  2下行
            Integer elevatorInfoId = elevatorInfo.getId();//电梯梯号

            List<Integer> up = new ArrayList<Integer>();// 上
            List<Integer> down = new ArrayList<Integer>();// 下

            int go = 0;//目标楼层

            Boolean trueUpFalseDown = null;
            if(up_down_status ==1){
                trueUpFalseDown = true;
            }else{
                trueUpFalseDown = false;
            }

            if (go != -22 && elevatorKeys.getLayer_keys() != 0) {
                go = elevatorKeys.getLayer();
                /*if(up_down_status == 1){//电梯上行状态
                    List<ElevatorKeys> elevatorKeys = keyMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfoId).eq("layer_keys", 1));
                    ElevatorKeys elevatorKeys1 = elevatorKeys.stream().max(Comparator.comparing(n -> n.getLayer())).get();
                    Integer layer = elevatorKeys1.getLayer();
                    go = layer;
                }

                List<ElevatorKeys> elevatorKeysDown = keyMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfoId).eq("layer_keys", 1).lt("layer",curr));
                if(up_down_status == 2 *//*&& !CollectionUtils.isEmpty(elevatorKeysDown)*//*){//电梯下行状态
                    ElevatorKeys elevatorKeys1 = elevatorKeysDown.stream().max(Comparator.comparing(n -> n.getLayer())).get();
                    Integer layer = elevatorKeys1.getLayer();
                    go = layer;
                }*/
                if (go > curr)
                    up.add(go);
                if (go < curr)
                    down.add(go);
                //}
                up.sort(null);
                Collections.sort(down, Collections.reverseOrder());
                for (int i = 0; i < up.size(); i++) {
                    curr = runUp(curr, up.get(i), keyMapper, elevatorKeys, elevatorMapper, elevatorInfo);
                }
                up.clear();
                for (int i = 0; i < down.size(); i++) {
                    curr = runDown(curr, down.get(i), keyMapper, elevatorKeys, elevatorMapper, elevatorInfo);
                }
                down.clear();
                go = curr;
            }
        //}
      return elevatorInfo;
    }

    private static int runUp(int curr, int go,ElevatorKeysMapper keysMapper,ElevatorKeys elevatorKeys,ElevatorMapper elevatorMapper,ElevatorInfo elevatorInfo) throws InterruptedException {
        int temp = curr;
        for (int i = 0; i < go - temp; i++) {
            if(curr == 0){
                curr = 1;
                continue;
            }else {
                System.out.println("-" + curr + "楼-");
                elevatorInfo.setCurrent_layer(curr);
                elevatorInfo.setUp_down_status(1);
                elevatorMapper.updateById(elevatorInfo);
                for (int j = 0; j < 2; j++) {
                    System.out.println("···");
                    java.lang.Thread.sleep(1000);
                }
                //如果当前楼层有任务，开门
                List<ElevatorKeys> exist = keysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("layer", curr).in("layer_keys", 1, -1).eq("elevator_id", elevatorKeys.getElevator_id()));
                if(!CollectionUtils.isEmpty(exist)){
                    openTheDoor(elevatorMapper,elevatorInfo);
                    closeTheDoor(elevatorMapper,elevatorInfo);
                }

                Thread.sleep(500);
                if(curr<=keysMapper.getHH(elevatorInfo.getId()) ){
                   keysMapper.updateElevator_keysInfo2(0,curr,elevatorKeys.getElevator_id());
                }

                curr++;
            }
        }
        if (curr == go) {
            elevatorInfo.setCurrent_layer(curr);

            System.out.println("到达" + go + "楼");

            elevatorInfo.setUp_down_status(0);
            elevatorMapper.updateById(elevatorInfo);
            //elevatorKeys.setStatus(0);
            Thread.sleep(500);

            openTheDoor(elevatorMapper,elevatorInfo);
            closeTheDoor(elevatorMapper,elevatorInfo);


            keysMapper.updateElevator_keysInfo2(0,curr,elevatorKeys.getElevator_id());
        }
        return curr;
    }

    private  static int runDown(int curr, int go,ElevatorKeysMapper keysMapper,ElevatorKeys elevatorKeys,ElevatorMapper elevatorMapper,ElevatorInfo elevatorInfo) throws InterruptedException {
        int temp = curr;
        for (int i = 0; i < temp - go; i++) {
            if(curr == 0){
                curr = -1;
                continue;
            }else {

                elevatorInfo.setCurrent_layer(curr);
                System.out.println("-" + curr + "楼-");
                elevatorInfo.setUp_down_status(2);
                elevatorMapper.updateById(elevatorInfo);
                for (int j = 0; j < 2; j++) {
                    System.out.println("···");
                    java.lang.Thread.sleep(1000);
                }
                elevatorInfo.setUp_down_status(0);
                elevatorMapper.updateById(elevatorInfo);

                //如果当前楼层有任务，开门
                List<ElevatorKeys> exist = keysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("layer", curr).in("layer_keys", 1, -1).eq("elevator_id", elevatorKeys.getElevator_id()));
                if(!CollectionUtils.isEmpty(exist)){
                    openTheDoor(elevatorMapper,elevatorInfo);
                    closeTheDoor(elevatorMapper,elevatorInfo);
                    keysMapper.updateMoreThenCurrent(0,curr,elevatorKeys.getElevator_id());
                }

                Thread.sleep(500);
                if(curr<=keysMapper.getHH(elevatorInfo.getId()) ){
                    keysMapper.updateElevator_keysInfo2(0,curr,elevatorKeys.getElevator_id());
                    keysMapper.updateMoreThenCurrent(0,curr,elevatorKeys.getElevator_id());
                    //keysMapper.updateElevator_keysInfo(0,elevatorKeys.getElevator_id(),curr);
                }
                keysMapper.updateElevator_keysInfo2(0,curr,elevatorKeys.getElevator_id());

                curr--;
            }
        }
        if (curr == go) {
            elevatorInfo.setCurrent_layer(curr);

            System.out.println("到达" + go + "楼");

            elevatorInfo.setUp_down_status(0);
            elevatorMapper.updateById(elevatorInfo);

            openTheDoor(elevatorMapper,elevatorInfo);
            closeTheDoor(elevatorMapper,elevatorInfo);
            concurrentHashMap.put(elevatorKeys.getElevator_id(),0);

            keysMapper.updateElevator_keysInfo2(0,curr,elevatorKeys.getElevator_id());


        }
        return curr;
    }

    public static  void openTheDoor(ElevatorMapper elevatorMapper,ElevatorInfo elevatorInfo) throws InterruptedException {
        System.out.println("电梯门开放中.....");
        elevatorInfo.setDoor_status(1);
        elevatorMapper.updateById(elevatorInfo);
        Thread.sleep((long) (stop_time*1000));
    }

    public static  void closeTheDoor(ElevatorMapper elevatorMapper,ElevatorInfo elevatorInfo) throws InterruptedException {
        System.out.println("电梯门关闭.....");
        elevatorInfo.setDoor_status(0);
        elevatorMapper.updateById(elevatorInfo);
        //Thread.sleep(5000);
    }
}
