package com.darek.employee.CRUD_employee.rest;

import com.darek.employee.CRUD_employee.dao.EmployeeDAO;
import com.darek.employee.CRUD_employee.entity.Employee;
import com.darek.employee.CRUD_employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    //inject employee dao
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //return list of employees
    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
        //return - kiedy obiekt jest zwracany, w tle Spring wykorzystuje Jaskson, który zamienia POJO na JSON
    }

    //add mapping for GET /employees/{employeeId}
    //@PathVariable - to parametr, który dodaje się do url. Jego nazwa musi być taka sama jak element w {}: employeeId
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee id not exist in database: " + employeeId);
        }
        return employee;
         //return - kiedy obiekt jest zwracany, w tle Spring wykorzystuje Jaskson, który zamienia POJO na JSON
    }

    //add mapping for POST - add new employee to database
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        //set id to 0. Save not update
        employee.setId(0);
        employeeService.save(employee);
        return employee;
         //return - kiedy obiekt jest zwracany, w tle Spring wykorzystuje Jaskson, który zamienia POJO na JSON
    }

    //add maping for PUT
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return employee;
         //return - kiedy obiekt jest zwracany, w tle Spring wykorzystuje Jaskson, który zamienia POJO na JSON
    }

    //add mapping for DELETE
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee id not found " + employeeId);
        }
        employeeService.deleteEmployeeById(employeeId);
        return "Employee deleted with id: " + employeeId;
         //return - kiedy obiekt jest zwracany, w tle Spring wykorzystuje Jaskson, który zamienia POJO na JSON
    }
}
