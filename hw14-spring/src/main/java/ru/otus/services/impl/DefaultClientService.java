package ru.otus.services.impl;

import org.springframework.stereotype.Service;
import ru.otus.model.Client;
import ru.otus.services.ClientService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DefaultClientService implements ClientService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Client createClient(Client client) {
        entityManager.persist(client);
        entityManager.flush();
        entityManager.refresh(client);

        return client;
    }
    @Override
    public Client getClientById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    @Transactional
    public Client updateClient(Client client) {
        if (client != null) {
            Client dbClient = getClientById(client.getId());
            Optional.ofNullable(client.getName()).ifPresent(dbClient::setName);
            Optional.ofNullable(client.getAddress()).ifPresent(dbClient::setAddress);
            Optional.ofNullable(client.getPhones()).ifPresent(dbClient::setPhones);
            return entityManager.merge(dbClient);
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    @Override
    @Transactional
    public void deleteClientById(Long id) {
        Client client = getClientById(id);
        if (client != null) {
           entityManager.remove(client);
           entityManager.flush();
        } else {
            throw new RuntimeException("Client not found.");
        }

    }
}
