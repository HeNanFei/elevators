package com.zjt.elevator.event;

import com.zjt.elevator.mapper.ElevatorKeysMapper;
import org.springframework.context.ApplicationEvent;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/10 12:28
 */
public class MockElevatorEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    private ElevatorKeysMapper elevatorKeysMapper;


    public MockElevatorEvent(Object source) {
        super(source);
    }

    public MockElevatorEvent(Object source,ElevatorKeysMapper elevatorKeysMapper) {
        super(source);
        this.elevatorKeysMapper = elevatorKeysMapper;
    }

    public ElevatorKeysMapper getElevatorKeysMapper() {
        return elevatorKeysMapper;
    }

    public void setElevatorKeysMapper(ElevatorKeysMapper elevatorKeysMapper) {
        this.elevatorKeysMapper = elevatorKeysMapper;
    }
}
