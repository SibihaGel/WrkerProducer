package org.employeeservice.worktimeservice.service;

import lombok.*;
import org.springframework.stereotype.Component;


@Component
@ToString
@Getter
@Setter
public class Robot2 extends GenerateMassege implements RobotInterFace {


    private final String robot2;

    public Robot2() {
        this.robot2 = "robot2";
    }


    @Override
    public void sendMessege() {


        System.out.println(" Робот2 отправляет ПОЧТУ");
        sign();
    }

    @Override
    public String getName() {
        return this.robot2;
    }
}

