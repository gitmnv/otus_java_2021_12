package ru.otus.services;

import ru.otus.model.Client;

public interface ClientService {
    Client createClient(Client client);

    Client getClientById(Long id);

    Client updateClient(Client client);

    void deleteClientById(Long id);

}
