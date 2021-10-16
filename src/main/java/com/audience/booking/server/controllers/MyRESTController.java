package com.audience.booking.server.controllers;



import com.audience.booking.server.entity.Client;
import com.audience.booking.server.exeption_handling.NoSuchEmployeeException;
import com.audience.booking.server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MyRESTController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Client> showAllEmployees(Model model) {
         return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public Client getEmployee(@PathVariable int id) {
        Client employee = employeeService.getEmployee(id);
        if (employee == null) {
            throw new NoSuchEmployeeException("There is no employee with id = " + id + "in data base");
        }
        return employee;
    }

    @PostMapping("/employees")
    public Client addEmployee(@RequestBody Client employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @PutMapping("/employees")
    public Client updateEmployee(@RequestBody Client employee) {
        employeeService.saveEmployee(employee);
        return employee;
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "employee with id = " + id + "was deleted";
    }

}
