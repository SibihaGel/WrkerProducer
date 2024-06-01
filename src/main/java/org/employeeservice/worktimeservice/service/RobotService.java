package org.employeeservice.worktimeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RobotService {

    @Autowired
    List<RobotInterFace> robots;

    @Autowired
    Map<String, RobotInterFace> map;

    public void doPrintRobot() {

        for (RobotInterFace rob : robots) {
            rob.getName();
            rob.sendMessege();
        }

    }

    public void doPrintRobot1FromMap() {
        for (Map.Entry<String, RobotInterFace> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : "  + entry.getValue());
        }
    }

    public void doPrintRobot2FromMap() {
        for (Map.Entry<String, RobotInterFace> entry : map.entrySet()) {
            if(entry.getKey().equals("robot1"))
               entry.getValue().sendMessege();
        }
    }



}
