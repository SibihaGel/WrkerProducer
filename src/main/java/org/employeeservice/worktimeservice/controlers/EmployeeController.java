package org.employeeservice.worktimeservice.controlers;


import org.employeeservice.worktimeservice.DAO.EmployeeRepository;
import org.employeeservice.worktimeservice.model.Employee;
import org.employeeservice.worktimeservice.service.EmployeeRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ... другие методы контроллера ...

    @PostMapping("/record-arrival")
    public void recordEmployeeArrival(@RequestBody EmployeeRequest request) {
        String employeeId = request.getEmployeeId();
        LocalDateTime arrivalTime = LocalDateTime.now();

        Employee employee = new Employee();

        employee.setLoginTime(arrivalTime);
        employeeRepository.save(employee);
    }

    @PostMapping("/record-departure")
    public void recordEmployeeDeparture(@RequestBody EmployeeRequest request) {
        String employeeId = request.getEmployeeId();
        LocalDateTime departureTime = LocalDateTime.now();

        Employee employee = employeeRepository.findById(employeeId).orElse(null);

        if (employee != null) {
            LocalDateTime loginTime = employee.getLoginTime();

            // Вычислить продолжительность работы в часах
            long hours = loginTime.until(departureTime, ChronoUnit.HOURS);

            // Увеличить значение shiftCount на основе продолжительности работы
            int updatedShiftCount = employee.getShiftCount() + (int) hours;
            employee.setShiftCount(updatedShiftCount);
            employee.setLogoutTime(departureTime);

            employeeRepository.save(employee);
        }
    }

    @GetMapping("/shiftCount/{id}")
    public int getShiftCount(@PathVariable String id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return employee != null ? employee.getShiftCount() : 0;
    }

    @GetMapping("/tooLateCount/{id}")
    public int getTooLateCount(@PathVariable String id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee != null) {
            int count = employee.getTooLateCount();
            LocalTime loginTime = employee.getLoginTime().toLocalTime();
            LocalTime thresholdTime = LocalTime.of(8, 0, 0);

            if (loginTime.isAfter(thresholdTime)) {
                count++;
            }
            employee.setTooLateCount(count);
            employeeRepository.save(employee);
            return count;
        }

        return 0;
    }

    @GetMapping()
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
}








