package com.zjt.elevator.thread;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.entity.ElevatorKeys;
import com.zjt.elevator.mapper.ElevatorKeysMapper;
import com.zjt.elevator.mapper.ElevatorMapper;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/15 20:34
 */
public class ElevatorCallableLast implements Callable<String> {

    private Integer elevator_id;

    private  ElevatorMapper elevatorMapper;

    private ElevatorKeysMapper elevatorKeysMappe;

    public static  ThreadLocal<String> elevator_status = new ThreadLocal<>();

    public static double stop_time = 0;

    {
        elevator_status.set("上行");
    }

    public ElevatorCallableLast(Integer id,ElevatorMapper elevatorMapper1,ElevatorKeysMapper elevatorKeysMapper) {
        this.elevator_id = id;
        this.elevatorMapper = elevatorMapper1;
        this.elevatorKeysMappe = elevatorKeysMapper;
    }

    @Override
    public String call() throws Exception {

        while (true) {
            try{
                System.out.println(elevator_status.get());
                ElevatorInfo elevatorInfo = elevatorMapper.selectById(elevator_id);
                stop_time = elevatorInfo.getTime_stop_layer();
                int i = runUp(elevatorInfo, elevatorMapper, elevatorKeysMappe);

                System.out.println("结果"+i);
                if(elevator_status.get().equals("上行")) {
                    List<ElevatorKeys> elevatorKeys = elevatorKeysMappe.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).eq("layer_keys", 1).gt("layer", i));
                    if (!CollectionUtils.isEmpty(elevatorKeys)) {
                        elevator_status.set("上行");
                    }else{
                        elevator_status.set("下行");
                    }
                }
                if(elevator_status.get().equals("下行")) {
                    List<ElevatorKeys> elevatorKeys = elevatorKeysMappe.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).eq("layer_keys", 1).lt("layer", i));
                    if (!CollectionUtils.isEmpty(elevatorKeys)) {
                        elevator_status.set("下行");
                    }else{
                        elevator_status.set("蓄势待发");

                    }
                }


                if(CollectionUtils.isEmpty(elevatorKeysMappe.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).eq("layer_keys", 1)))){
                    elevator_status.set("蓄势待发");
                };



            }catch (Exception e){e.printStackTrace();}
        }

    }


    private static int runUp(ElevatorInfo elevatorInfo,ElevatorMapper elevatorMapper,ElevatorKeysMapper elevatorKeysMapper) throws InterruptedException {
        int curr = 0;
        int go = 0;
        curr = elevatorInfo.getCurrent_layer();

        if(elevator_status.get() == null){
            elevator_status.set("上行");
        }
        if(elevator_status.get() == "上行"){
            List<ElevatorKeys> elevatorKeysMax = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).gt("layer", curr).eq("layer_keys", 1));
            ElevatorKeys elevatorKeys1Max = elevatorKeysMax.stream().max(Comparator.comparing(n -> n.getLayer())).orElse(null);
            if(elevatorKeys1Max != null){
                go = elevatorKeys1Max.getLayer();
            }else{
                List<ElevatorKeys> elevatorKeysMin = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).lt("layer", curr).eq("layer_keys", 1));
                ElevatorKeys elevatorKeys1Min = elevatorKeysMin.stream().min(Comparator.comparing(n -> n.getLayer())).orElse(null);
                if(elevatorKeys1Min != null){
                    elevator_status.set("下行");
                    go = elevatorKeys1Min.getLayer();
                }else{
                    go = 0;
                }
            }
        }else if(elevator_status.get() == "下行"){
            List<ElevatorKeys> elevatorKeysMin = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).lt("layer", curr).eq("layer_keys", 1));
            ElevatorKeys elevatorKeys1Min = elevatorKeysMin.stream().min(Comparator.comparing(n -> n.getLayer())).orElse(null);
            if(elevatorKeys1Min != null){
                elevator_status.set("下行");
                go = elevatorKeys1Min.getLayer();
            }else{
                List<ElevatorKeys> elevatorKeysMax = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).gt("layer", curr).eq("layer_keys", 1));
                ElevatorKeys elevatorKeys1Max = elevatorKeysMax.stream().max(Comparator.comparing(n -> n.getLayer())).orElse(null);
                if(elevatorKeys1Max != null) {
                    elevator_status.set("上行");
                    go = elevatorKeys1Max.getLayer();
                }else{
                    go = 0;
                }
            }
        }else if(elevator_status.get() == "蓄势待发") {
            List<ElevatorKeys> elevatorKeysMin = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).lt("layer", curr).eq("layer_keys", 1));
            ElevatorKeys elevatorKeys1Min = elevatorKeysMin.stream().min(Comparator.comparing(n -> n.getLayer())).orElse(null);
            if (elevatorKeys1Min != null) {
                elevator_status.set("下行");
                go = elevatorKeys1Min.getLayer();
            } else {
                List<ElevatorKeys> elevatorKeysMax = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id", elevatorInfo.getId()).gt("layer", curr).eq("layer_keys", 1));
                ElevatorKeys elevatorKeys1Max = elevatorKeysMax.stream().max(Comparator.comparing(n -> n.getLayer())).orElse(null);
                if (elevatorKeys1Max != null) {
                    elevator_status.set("上行");
                    go = elevatorKeys1Max.getLayer();
                } else {
                    go = 0;
                }
            }
        }
        if(elevator_status.get().equals("上行")){
            int temp = curr;
            for (int i = 0; i < go - temp; i++) {
                if (curr == 0) {
                    curr = 1;
                    continue;
                } else {
                    if(elevatorKeysMapper.selectOne(new QueryWrapper<ElevatorKeys>().eq("elevator_id",elevatorInfo.getId()).eq("layer_keys",1).eq("layer",go)) != null) {
                        System.out.println("-" + curr + "楼-");
                        elevatorInfo.setCurrent_layer(curr);
                        elevatorInfo.setUp_down_status(1);
                        //elevatorMapper.updateById(elevatorInfo);
                        elevatorMapper.updateStatus(curr,1,elevatorInfo.getId());

                        for (int j = 0; j < 3; j++) {
                            System.out.println("···");
                            Thread.sleep(900);
                        }
                        //如果当前楼层有任务，开门
                        List<ElevatorKeys> exist = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("layer", curr).in("layer_keys", 1).eq("elevator_id", elevatorInfo.getId()));
                        if (!CollectionUtils.isEmpty(exist) || i == 0) {
                            openTheDoor(elevatorMapper, elevatorInfo);
                            closeTheDoor(elevatorMapper, elevatorInfo);
                            Thread.sleep(500);
                            //elevatorKeysMapper.updateElevator_keysInfo2(0, curr, elevatorInfo.getId());
                        }
                        curr++;
                    }
                }
            }
            if (curr == go) {
                if(curr == 0){
                    curr = -1;
                }
                elevatorInfo.setCurrent_layer(curr);

                System.out.println("到达" + go + "楼");

                elevatorInfo.setUp_down_status(0);
                //elevatorMapper.updateById(elevatorInfo);
                elevatorMapper.updateStatus(curr,0,elevatorInfo.getId());

                //elevatorKeys.setStatus(0);
                Thread.sleep(500);

                openTheDoor(elevatorMapper,elevatorInfo);
                closeTheDoor(elevatorMapper,elevatorInfo);


                elevatorKeysMapper.updateElevator_keysInfo2(0,curr,elevatorInfo.getId());
            }
        }else if("下行".equals(elevator_status.get())){
            runDown(elevatorInfo,elevatorMapper,elevatorKeysMapper,go);
        }


        return go;
    }

    public static Integer judgeStatus(){
        if(elevator_status.get() == "蓄势待发"){
            return 0;
        }
        if(elevator_status.get() == "上行"){
            return 1;
        }
        if(elevator_status.get() == "下行"){
            return 2;
        }
        return  0;
    }

    private static int runDown(ElevatorInfo elevatorInfo,ElevatorMapper elevatorMapper,ElevatorKeysMapper elevatorKeysMapper,int go) throws InterruptedException {
        int curr = 0;

        curr = elevatorInfo.getCurrent_layer();

        int temp = curr;
        for (int i = 0; i < temp - go; i++) {
            if(curr == 0){
                curr = -1;
                continue;
            }else {
                if(elevatorKeysMapper.selectOne(new QueryWrapper<ElevatorKeys>().eq("elevator_id",elevatorInfo.getId()).eq("layer_keys",1).eq("layer",go)) != null) {
                    elevatorInfo.setCurrent_layer(curr);
                    System.out.println("-" + curr + "楼-");
                    elevatorInfo.setUp_down_status(2);
                    //elevatorMapper.updateById(elevatorInfo);
                    elevatorMapper.updateStatus(curr,2,elevatorInfo.getId());

                    for (int j = 0; j < 3; j++) {
                        System.out.println("···");
                        Thread.sleep(900);
                    }
                    elevatorInfo.setUp_down_status(0);
                    //elevatorMapper.updateById(elevatorInfo);
                    elevatorMapper.updateStatus(curr,0,elevatorInfo.getId());


                    //如果当前楼层有任务，开门
                    List<ElevatorKeys> exist = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("layer", curr).in("layer_keys", 1, -1).eq("elevator_id", elevatorInfo.getId()));
                    if (!CollectionUtils.isEmpty(exist) || i == 0 ) {
                        openTheDoor(elevatorMapper, elevatorInfo);
                        closeTheDoor(elevatorMapper, elevatorInfo);
                        //elevatorKeysMapper.updateElevator_keysInfo2(0, curr, elevatorInfo.getId());
                    }

                    curr--;
                }            }
        }
        if (curr == go) {
            if(curr == 0){
                curr = -1;
                go = -1;
            }
            elevatorInfo.setCurrent_layer(curr);

            System.out.println("到达" + go + "楼");

            elevatorInfo.setUp_down_status(0);
            //elevatorMapper.updateById(elevatorInfo);
            elevatorMapper.updateStatus(curr,0,elevatorInfo.getId());

            openTheDoor(elevatorMapper,elevatorInfo);
            closeTheDoor(elevatorMapper,elevatorInfo);
            elevatorKeysMapper.updateElevator_keysInfo2(0,curr,elevatorInfo.getId());

        }
        return curr;
    }


    //判断电梯状态
    public static  void setThreadLocalStatus(ElevatorInfo elevatorInfo){
        Integer up_down_status = elevatorInfo.getUp_down_status();
        if(up_down_status == 1){
            elevator_status.set("上行");
        }
        if(up_down_status == 2){
            elevator_status.set("下行");
        }
        if(up_down_status == 0){
            elevator_status.set("蓄势待发");
        }
    }

    //返回电梯楼层
    public static Integer getLayerBack(Integer elevator_id,ElevatorKeysMapper elevatorKeysMapper){
        if(elevator_status.get() == null){
            List<ElevatorKeys> elevatorKeysList = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id",elevator_id).eq("layer_keys",1));
            if(!CollectionUtils.isEmpty(elevatorKeysList)){
                elevator_status.set("上行");
                return elevatorKeysList.stream().max(Comparator.comparing(n -> n.getLayer())).get().getLayer();
            }
        }
        if(elevator_status.get() == "蓄势待发"){
            List<ElevatorKeys> elevatorKeysList = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id",elevator_id).eq("layer_keys",1));
            if(!CollectionUtils.isEmpty(elevatorKeysList)){
                elevator_status.set("上行");
                return elevatorKeysList.stream().max(Comparator.comparing(n -> n.getLayer())).get().getLayer();
            }
        }
        if(elevator_status.get() == "下行"){
            List<ElevatorKeys> elevatorKeysList = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id",elevator_id).eq("layer_keys",1));
            if(!CollectionUtils.isEmpty(elevatorKeysList)){
                return elevatorKeysList.stream().min(Comparator.comparing(n -> n.getLayer())).get().getLayer();
            }
        }
        if(elevator_status.get() == "上行"){
            List<ElevatorKeys> elevatorKeysList = elevatorKeysMapper.selectList(new QueryWrapper<ElevatorKeys>().eq("elevator_id",elevator_id).eq("layer_keys",1));
            if(!CollectionUtils.isEmpty(elevatorKeysList)){
                return elevatorKeysList.stream().max(Comparator.comparing(n -> n.getLayer())).get().getLayer();
            }
        }
        return 0;
    }

    public static  void openTheDoor(ElevatorMapper elevatorMapper,ElevatorInfo elevatorInfo) throws InterruptedException {
        System.out.println("电梯门开放中.....");
        elevatorInfo.setDoor_status(1);

        elevatorMapper.updateById(elevatorInfo);
        //elevatorMapper.updateStatus(elevatorInfo.getCurrent_layer(),1,elevatorInfo.getId());

        Thread.sleep((long) (stop_time*1000));
    }

    public static  void closeTheDoor(ElevatorMapper elevatorMapper,ElevatorInfo elevatorInfo) throws InterruptedException {
        System.out.println("电梯门关闭.....");
        elevatorInfo.setDoor_status(0);
        elevatorMapper.updateById(elevatorInfo);
        //elevatorMapper.updateStatus(elevatorInfo.getCurrent_layer(),0,elevatorInfo.getId());

        //Thread.sleep(5000);
    }




    public Integer getElevator_id() {
        return elevator_id;
    }

    public void setElevator_id(Integer elevator_id) {
        this.elevator_id = elevator_id;
    }

    public ElevatorMapper getElevatorMapper() {
        return elevatorMapper;
    }

    public void setElevatorMapper(ElevatorMapper elevatorMapper) {
        this.elevatorMapper = elevatorMapper;
    }

    public ElevatorKeysMapper getElevatorKeysMappe() {
        return elevatorKeysMappe;
    }

    public void setElevatorKeysMappe(ElevatorKeysMapper elevatorKeysMappe) {
        this.elevatorKeysMappe = elevatorKeysMappe;
    }

    public ThreadLocal<String> getElevator_status() {
        return elevator_status;
    }

    public void setElevator_status(ThreadLocal<String> elevator_status) {
        this.elevator_status = elevator_status;
    }
}
