package com.zjt.elevator.event;

import com.zjt.elevator.thread.ElevatorCallable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

import java.util.List;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/9 11:04
 */
public class ElevatorEvent extends ApplicationContextEvent {
    /**
     * Create a new ContextStartedEvent.
     *
     * @param source the {@code ApplicationContext} that the event is raised for
     *               (must not be {@code null})
     */

    private List<ElevatorCallable> elevatorCallableList;


    public List<ElevatorCallable> getElevatorCallableList() {
        return elevatorCallableList;
    }

    public void setElevatorCallableList(List<ElevatorCallable> elevatorCallableList) {
        this.elevatorCallableList = elevatorCallableList;
    }

    public ElevatorEvent(ApplicationContext source) {
        super(source);
    }

    public ElevatorEvent(ApplicationContext source,List<ElevatorCallable> elevatorCallables) {
        super(source);
        this.elevatorCallableList = elevatorCallables;
    }
}
