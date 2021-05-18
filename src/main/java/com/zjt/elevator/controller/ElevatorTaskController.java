package com.zjt.elevator.controller;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorTask;
import com.zjt.elevator.mapper.ElevatorTaskMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/11 19:41
 */

@Api(tags = "乘梯指令相关")
@Controller
@RequestMapping("/task")
public class ElevatorTaskController {

    @Autowired
    private ElevatorTaskMapper elevatorTaskMapper;

    @GetMapping("/getOrderStatus")
    @ApiOperation(value = "指令状态")
    @ResponseBody
    public ElevatorTask getOrderStatus(Long id){
        return elevatorTaskMapper.selectById(id);
    }

    @ApiOperation(value = "指令列表")
    @GetMapping("/getTasks")
    @ResponseBody
    public List<ElevatorTask> getTaskk(){
        return elevatorTaskMapper.selectList(new QueryWrapper<>());
    }

    @PostMapping("/insertWithElevator")
    @ResponseBody
    @ApiOperation(value = "电梯指令")
    public String getTaskk(ElevatorTask elevatorTask){
        String message = "success";
        try{
            elevatorTask.setCreatetime(LocalDateTime.now());
            elevatorTask.setDebug_info("from User");
            //int[] ints = NumberUtil.generateRandomNumber(1, 8000, 1);
            //elevatorTask.setId(ints[0]);
            elevatorTaskMapper.insert(elevatorTask);
            Thread.sleep(777);
            ElevatorTask elevatorTask1 = elevatorTaskMapper.selectById(elevatorTask.getId());
            return String.valueOf(elevatorTask1.getElevator_id());
        }catch (Exception e){
             message = e.toString();
             e.printStackTrace();
        }
        return  message;

    }



}
