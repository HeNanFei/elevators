package com.zjt.elevator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/11 19:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("elevator_task")
public class ElevatorTask implements Serializable {
    @TableId(type=IdType.AUTO)
    private Integer id;

    private Integer layer_start;

    private Integer layer_end;
    private Integer elevator_id;
    private Integer task_state;
    private LocalDateTime createtime;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String debug_info;

}
