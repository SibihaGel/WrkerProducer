package org.employeeservice.worktimeservice.service;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@ToString
@Getter
@Setter
public class Robot1 extends GenerateMassege implements RobotInterFace{

    private final String name = "robot1";



    @Override
    public void sendMessege(){



        System.out.println(" Робот1 отправляет СМС");

        sign();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
