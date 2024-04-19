package com.example.clients.infra.inputport;

import com.example.clients.infra.dto.ClientCarDTO;
import com.example.clients.infra.dto.ClientDTO;
import com.example.clients.infra.exception.ClientNotFoundException;

import java.util.List;

public interface IClientInputPort {

    public ClientCarDTO getClientById(Long id) throws ClientNotFoundException;

    public ClientDTO createClient(ClientDTO client);

    public ClientDTO editClient(Long id, ClientDTO client) throws ClientNotFoundException;

    public ClientCarDTO buyCar(Long clientId, Long carId) throws ClientNotFoundException;

    public List<ClientDTO> getClientList(int page, int size);

    public String deleteClient(Long id);
}
