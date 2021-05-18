package com.zjt.elevator.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/14 19:58
 */
@Api(tags = "其他")
@Controller
@RequestMapping("/view")
public class ViewController {
    @ApiOperation(value = "楼层信息")
    @RequestMapping("/floor")
    public String toOther(){
        return "other";
    }

    @ApiOperation(value = "消息提示")
    @RequestMapping("/message")
    public String message(){
        return "message";
    }
    @ApiOperation(value = "人脸识别")
    @RequestMapping("/identifier")
    public String identifier(){
        return "identifier";
    }

    @ApiOperation(value = "any")
    @RequestMapping("/model")
    public String lyear_ui_modals(){
        return "lyear_ui_modals";
    }

}
