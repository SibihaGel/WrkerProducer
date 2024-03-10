package org.employeeservice.worktimeservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "work_day_table")
@Getter
@Setter

public class Employee {

    private String id;
    private String firstName;
    private String lastName;
    private String surname;
    private Boolean status;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private int shiftCount;
    private int tooLateCount;
    private boolean isBlue;


    @Builder
    public Employee(String id, String firstName, String lastName, String surname, Boolean status, LocalDateTime loginTime, LocalDateTime logoutTime, int shiftCount, int tooLateCount, boolean isBlue) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.surname = surname;
        this.status = status;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.shiftCount = shiftCount;
        this.tooLateCount = tooLateCount;
        this.isBlue = isBlue;
    }
}
