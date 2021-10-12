package com.audience.booking.server.dao;

import com.audience.booking.server.entity.Employee;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EmployeeDAOImplementation implements EmployeeDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        Session session = entityManager.unwrap(Session.class);
        List<Employee> allEmployees = session.createQuery("from Employee", Employee.class).getResultList();
        return allEmployees;
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(employee);
    }

    @Override
    @Transactional
    public Employee getEmployee(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Employee.class, id);
    }

    @Override
    @Transactional
    public void deleteEmployee(int id) {
        Session session = entityManager.unwrap(Session.class);
        Query employeeQuery = session.createQuery("delete from Employee " +
                "where id =:employeeId");
        employeeQuery.setParameter("employeeId", id);
        employeeQuery.executeUpdate();
    }
}
