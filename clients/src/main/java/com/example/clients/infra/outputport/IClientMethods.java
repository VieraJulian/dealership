package com.example.clients.infra.outputport;

import com.example.clients.domain.Client;
import com.example.clients.infra.exception.ClientNotFoundException;

import java.util.List;

public interface IClientMethods {

    public Client getById(Long id) throws ClientNotFoundException;

    public Client saveClient(Client client);

    public List<Client> getClientList(int page, int size);

    public void deleteClient(Long id);
}
