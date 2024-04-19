package com.example.clients.infra.outputAdapter;

import com.example.clients.domain.Client;
import com.example.clients.infra.exception.ClientNotFoundException;
import com.example.clients.infra.outputport.IClientMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientRepository implements IClientMethods {

    @Autowired
    private IMySQLRepository myRepository;

    @Override
    public Client getById(Long id) throws ClientNotFoundException {
        return myRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found"));
    }

    @Override
    public Client saveClient(Client client) {
        return myRepository.save(client);
    }

    @Override
    public List<Client> getClientList(int page, int size) {
        return myRepository.getClientList(PageRequest.of(page, size));
    }

    @Override
    public void deleteClient(Long id) {
        myRepository.deleteById(id);
    }
}
