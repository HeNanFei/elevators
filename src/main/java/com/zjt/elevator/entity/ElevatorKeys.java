package com.zjt.elevator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 9:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("elevator_keys")
public class ElevatorKeys {
private Integer id;
private Integer elevator_id;
private Integer layer;
private Integer up_key;
private Integer down_key;
private Integer layer_keys;
private Integer layer_keys_execute;
private LocalDateTime layer_keys_execute_datetime;

}
