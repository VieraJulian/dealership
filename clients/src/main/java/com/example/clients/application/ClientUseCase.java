package com.example.clients.application;

import com.example.clients.domain.Client;
import com.example.clients.infra.dto.CarDTO;
import com.example.clients.infra.dto.ClientCarDTO;
import com.example.clients.infra.dto.ClientDTO;
import com.example.clients.infra.exception.ClientNotFoundException;
import com.example.clients.infra.inputport.IClientInputPort;
import com.example.clients.infra.outputport.ICarServicePort;
import com.example.clients.infra.outputport.IClientMethods;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientUseCase implements IClientInputPort {

    @Autowired
    private IClientMethods clientMethods;

    @Autowired
    private ICarServicePort carServicePort;

    @Override
    @CircuitBreaker(name = "cars", fallbackMethod = "fallbackGetCars")
    public ClientCarDTO getClientById(Long id) throws ClientNotFoundException {
        Client client = clientMethods.getById(id);
        List<CarDTO> carList = new ArrayList<>();

        if (client.getCarsIds() != null) {
            for (Long carId : client.getCarsIds()) {
                CarDTO car = carServicePort.getCar(carId);

                carList.add(car);
            }
        }

        return ClientCarDTO.builder()
                .client(ClientDTO.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .email(client.getEmail())
                        .phone(client.getPhone())
                        .build())
                .cars(carList)
                .build();
    }

    @Override
    public ClientDTO createClient(ClientDTO client) {
        Client clientInfo = Client.builder()
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();

        Client newClient = clientMethods.saveClient(clientInfo);

        return ClientDTO.builder()
                .id(newClient.getId())
                .name(newClient.getName())
                .email(newClient.getEmail())
                .phone(newClient.getPhone())
                .build();
    }

    @Override
    public ClientDTO editClient(Long id, ClientDTO client) throws ClientNotFoundException {
        Client clientDB = clientMethods.getById(id);

        clientDB.setName(client.getName());
        clientDB.setEmail(client.getEmail());
        clientDB.setPhone(client.getPhone());

        Client editedClient = clientMethods.saveClient(clientDB);

        return ClientDTO.builder()
                .id(editedClient.getId())
                .name(editedClient.getName())
                .email(editedClient.getEmail())
                .phone(editedClient.getPhone())
                .build();
    }

    @Override
    @CircuitBreaker(name = "cars", fallbackMethod = "fallbackGetCars")
    public ClientCarDTO buyCar(Long clientId, Long carId) throws ClientNotFoundException {
        Client clientDB = clientMethods.getById(clientId);
        CarDTO car = carServicePort.getCar(carId);
        List<CarDTO> carsList = new ArrayList<>();

        if (clientDB.getCarsIds() == null){
            clientDB.setCarsIds(new ArrayList<>());
        }

        clientDB.getCarsIds().add(car.getId());

        Client client = clientMethods.saveClient(clientDB);

        ClientDTO clientDTO = ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();

        for (Long cId : client.getCarsIds()) {
            CarDTO carDTO = carServicePort.getCar(cId);
            carsList.add(carDTO);
        }

        return ClientCarDTO.builder()
                .client(clientDTO)
                .cars(carsList)
                .build();
    }

    @Override
    public List<ClientDTO> getClientList(int page, int size) {
        List<Client> clients = clientMethods.getClientList(page, size);
        List<ClientDTO> clientList = new ArrayList<>();

        for (Client c : clients) {
            clientList.add(ClientDTO.builder()
                            .id(c.getId())
                            .name(c.getName())
                            .email(c.getEmail())
                            .phone(c.getPhone())
                            .build());
        }

        return clientList;
    }

    @Override
    public String deleteClient(Long id) {
        clientMethods.deleteClient(id);

        return "Client deleted successfully";
    }

    public ClientCarDTO fallbackGetCars(Throwable throwable){
        return ClientCarDTO.builder()
                .client(null)
                .cars(null)
                .build();
    }
}
