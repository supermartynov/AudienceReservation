package com.audience.booking.server.service;


import com.audience.booking.server.entity.Client;

import java.util.List;


public interface EmployeeService {
    public List<Client> getAllEmployees();

    public void saveEmployee (Client employee);

    public Client getEmployee(int id);

    public void deleteEmployee(int id);

}
