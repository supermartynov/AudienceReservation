package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDAO {
    public List<Client> getAllEmployees();

    public void saveEmployee (Client employee);

    public Client getEmployee(int id);

    public void deleteEmployee(int id);

}
