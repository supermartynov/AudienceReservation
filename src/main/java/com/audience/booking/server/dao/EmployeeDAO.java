package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDAO {
    public List<Employee> getAllEmployees();

    public void saveEmployee (Employee employee);

    public Employee getEmployee(int id);

    public void deleteEmployee(int id);

}
