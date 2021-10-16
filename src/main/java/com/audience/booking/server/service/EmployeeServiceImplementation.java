package com.audience.booking.server.service;


import com.audience.booking.server.dao.EmployeeDAO;
import com.audience.booking.server.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService{

    @Autowired
    private EmployeeDAO employeeDAO;


    @Override
    @Transactional
    public List<Client> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    @Transactional
    public void saveEmployee(Client employee) {
        employeeDAO.saveEmployee(employee);
    }

    @Override
    @Transactional
    public Client getEmployee(int id) {
        return employeeDAO.getEmployee(id);
    }

    @Override
    @Transactional
    public void deleteEmployee(int id) {
        employeeDAO.deleteEmployee(id);
    }

}
