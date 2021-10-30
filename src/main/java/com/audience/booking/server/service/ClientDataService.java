package com.audience.booking.server.service;

import com.audience.booking.server.dao.ClientsDAO;
import com.audience.booking.server.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClientDataService {

    @Autowired
    private ClientsDAO clientsCrudRepository;

    @Transactional
    public List<Client> getAllClients() {
        return (List<Client>) clientsCrudRepository.findAll();
    }

    @Transactional
    public void saveClient(Client client) {
        clientsCrudRepository.save(client);
    }

    @Transactional
    public void deleteClient(int id) {
        clientsCrudRepository.deleteById(id);
    }

    @Transactional
    public Client getClient(int id) {
        return clientsCrudRepository.findById(id).get();
    }

}
