package com.zjt.elevator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/4 21:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("elevator_info")
public class ElevatorInfo implements Serializable {
    private Integer id;
    private Integer door_status;
    private Integer elevator_task_state;
    private Integer up_down_status;
    private Integer next_up_down_status;
    private Integer current_layer;
    private Integer current_people;
    private String  token;
    private Integer max_people;
    private Integer layer_min;
    private Integer layer_max;
    private Integer active;
    private String simulator_id;
    private Integer status_update_success;
    private Double time_run_layer;
    private Double time_stop_layer;
    private Integer intercept_layer;
    private LocalDateTime status_updatetime;
    private String get_devices_url;
    private String get_devices_result;
    private Integer model;
    private Integer elevator_mode;
    private LocalDateTime status_fail_updatetime;



}
