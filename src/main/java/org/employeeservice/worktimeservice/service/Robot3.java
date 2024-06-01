package org.employeeservice.worktimeservice.service;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class Robot3 extends GenerateMassege implements RobotInterFace {


    private final String robot3;

    public Robot3() {
        this.robot3 = "robot3";
    }

    @Override
    public void sendMessege() {

        System.out.println(" Робот3 печатает");
        sign();
    }

    @Override
    public String getName() {

        return this.robot3;
    }
}

