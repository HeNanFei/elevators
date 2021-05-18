package com.zjt.elevator.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * @author zjt.
 * @version 1.0
 * @Date: 2021/4/7 12:58
 */
@TableName("log_info")
public class LogInfor {
    private Long  id;
    private Integer  device_id;
    private String  reason;
    private LocalDateTime log_time;

    public LogInfor() {
    }

    public LogInfor(Long id, Integer device_id, String reason, LocalDateTime log_time) {
        this.id = id;
        this.device_id = device_id;
        this.reason = reason;
        this.log_time = log_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getLog_time() {
        return log_time;
    }

    public void setLog_time(LocalDateTime log_time) {
        this.log_time = log_time;
    }

    @Override
    public String toString() {
        return "LogInfor{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", reason='" + reason + '\'' +
                ", log_time=" + log_time +
                '}';
    }
}
