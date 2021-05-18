package com.zjt.elevator;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ServletComponentScan
@MapperScan("com.zjt.elevator.mapper")
@SpringBootApplication
@EnableAsync
public class ElevatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElevatorApplication.class, args);
    }

}
