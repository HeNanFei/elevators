package com.zjt.elevator.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjt.elevator.entity.ElevatorInfo;
import com.zjt.elevator.mapper.ElevatorMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/4 21:14
 */
@Api(tags = "电梯相关")
@Controller
public class ElevatorController {
    @Autowired
    private ElevatorMapper elevatorInfo;

    @GetMapping("/getData")
    @ResponseBody
    @ApiOperation(value = "获取电梯所有数据")
    public ElevatorInfo getData(){
        List<ElevatorInfo> elevatorInfos = elevatorInfo.selectList(new QueryWrapper<>());
        if(!CollectionUtils.isEmpty(elevatorInfos)){
            ElevatorInfo elevatorInfo = elevatorInfos.get(elevatorInfos.size() - 1);
            return elevatorInfo;
        }
        return null;
    }

    @GetMapping("/getUrlWithData")
    @ResponseBody
    @ApiOperation(value = "根据远程Url获取电梯数据")
    public String getUrlWithData(){
        List<ElevatorInfo> elevatorInfos = elevatorInfo.selectList(new QueryWrapper<>());
        for (int i = 0; i < elevatorInfos.size(); i++) {
            String url  = elevatorInfos.get(i).getGet_devices_url();
            HttpResponse execute = HttpRequest.post(url).timeout(2000).execute();
            elevatorInfos.get(i).setGet_devices_result(execute.body());
            elevatorInfo.updateById(elevatorInfos.get(i));
        }
        return "修改成功";
    }

    @GetMapping("/getElevators")
    @ResponseBody
    public List<ElevatorInfo> getElevators(){
            List<ElevatorInfo> elevatorInfos = elevatorInfo.selectList(new QueryWrapper<>());
        if(!CollectionUtils.isEmpty(elevatorInfos)){
           return elevatorInfos;
        }
        throw  new RuntimeException("无电梯");
    }


   /* 2，接口
2.1，电梯状态查询接口
2.2，设置电梯为专梯模式，解除电梯专梯模式
2.3，乘梯指令（不指定电梯/指定电梯2种）
            2.4，查询乘梯指令状态*/
   @ApiOperation(value = "查询所有电梯状态")
    @GetMapping("/getElevatorsStatus")
    @ResponseBody
    public List<ElevatorInfo> getElevatorsStatus(){
        List<ElevatorInfo> elevatorInfos = elevatorInfo.selectList(new QueryWrapper<>());
        if(!CollectionUtils.isEmpty(elevatorInfos)){
            return elevatorInfos;
        }
        throw  new RuntimeException("无电梯");
    }

    @ApiOperation(value = "根据id查询电梯状态")
    @GetMapping("/getElevatorsStatusById")
    @ResponseBody
    public ElevatorInfo getElevatorsStatusById(@RequestParam Long id){
        return elevatorInfo.selectById(id);
    }

    @ApiOperation(value = "设置专梯模式")
    @GetMapping("/setPrivateMode")
    @ResponseBody
    public ElevatorInfo setPrivateMode(@RequestParam Long id){
        ElevatorInfo elevatorInfos = elevatorInfo.selectById(id);
        elevatorInfos.setModel(1);
        elevatorInfo.updateById(elevatorInfos);
        return elevatorInfos;
    }
    @ApiOperation(value = "设置非专梯模式")
    @GetMapping("/setPublicMode")
    @ResponseBody
    public ElevatorInfo setPublicMode(@RequestParam Long id){
        ElevatorInfo elevatorInfos = elevatorInfo.selectById(id);
        elevatorInfos.setModel(2);
        elevatorInfo.updateById(elevatorInfos);
        return elevatorInfos;
    }
}
