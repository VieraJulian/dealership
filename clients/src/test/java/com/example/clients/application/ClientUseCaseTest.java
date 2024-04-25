package com.example.clients.application;

import com.example.clients.domain.Car;
import com.example.clients.domain.Client;
import com.example.clients.infra.dto.CarDTO;
import com.example.clients.infra.dto.ClientCarDTO;
import com.example.clients.infra.dto.ClientDTO;
import com.example.clients.infra.exception.ClientNotFoundException;
import com.example.clients.infra.outputport.ICarServicePort;
import com.example.clients.infra.outputport.IClientMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@Sql("/test-mysql.sql")
public class ClientUseCaseTest {

    @Mock
    ICarServicePort carServicePort;

    @Mock
    IClientMethods clientMethods;

    @InjectMocks
    ClientUseCase useCase;

    private CarDTO carDTO;

    private Client client;

    private ClientDTO clientDTO;

    @BeforeEach
    void setup(){
        carDTO = CarDTO.builder()
                .id(1L)
                .brand("Audi")
                .model("Q5")
                .year("2013")
                .price(BigDecimal.valueOf(2000000))
                .build();

        clientDTO = ClientDTO.builder()
                .name("Julian")
                .email("ju@gmail.com")
                .phone("182638721")
                .build();

        client = Client.builder()
                .id(1L)
                .name("Julian")
                .email("ju@gmail.com")
                .phone("182638721")
                .build();

        Car car = Car.builder()
                .id(1L)
                .client(client)
                .build();

        client.setCars(Arrays.asList(car));

    }

    @Test
    void testGetClientById() throws ClientNotFoundException {
        Long id = 1L;

        when(carServicePort.getCar(anyLong())).thenReturn(carDTO);
        when(clientMethods.getById(anyLong())).thenReturn(client);

        ClientCarDTO clientCarDTO = useCase.getClientById(id);

        assertEquals("Julian", clientCarDTO.getClient().getName());
    }

    @Test
    void testGetClientThrowsException() throws ClientNotFoundException {
        Long id = 5L;

        when(carServicePort.getCar(anyLong())).thenReturn(carDTO);
        when(clientMethods.getById(anyLong())).thenThrow(new ClientNotFoundException("Client not found"));

        assertThrows(ClientNotFoundException.class, () -> {
            useCase.getClientById(id);
        });
    }

    @Test
    void testCreateClient(){
        when(clientMethods.saveClient(any(Client.class))).thenReturn(client);

        ClientDTO clientDTO1 = useCase.createClient(clientDTO);

        assertEquals(1, clientDTO1.getId());
        assertEquals("Julian", clientDTO1.getName());
        assertEquals("ju@gmail.com", clientDTO1.getEmail());
        assertEquals("182638721", clientDTO1.getPhone());

    }

    @Test
    void testEditClient() throws ClientNotFoundException {
        Long id = 1L;

        when(clientMethods.getById(anyLong())).thenReturn(client);
        when(clientMethods.saveClient(any(Client.class))).thenReturn(client);

        ClientDTO clientDTO1 = useCase.editClient(id, clientDTO);

        assertEquals(1, clientDTO1.getId());
        assertEquals("Julian", clientDTO1.getName());
        assertEquals("ju@gmail.com", clientDTO1.getEmail());
        assertEquals("182638721", clientDTO1.getPhone());
    }

    @Test
    void testEditClientThrowsException() throws ClientNotFoundException {
        Long id = 1L;

        when(clientMethods.getById(anyLong())).thenThrow(new ClientNotFoundException("Client not found"));

        assertThrows(ClientNotFoundException.class, () -> {
            useCase.editClient(id, clientDTO);
        });
    }

    @Test
    void testBuyCar() throws ClientNotFoundException {
        Long clientId = 1L;
        Long carId = 1L;

        Client clientGetById = Client.builder()
                .id(1L)
                .name("Julian")
                .email("ju@gmail.com")
                .phone("182638721")
                .build();

        Client clientSave = Client.builder()
                .id(1L)
                .name("Julian")
                .email("ju@gmail.com")
                .phone("182638721")
                .cars(Arrays.asList(Car.builder().id(carId).client(clientGetById).build()))
                .build();

        when(clientMethods.getById(anyLong())).thenReturn(clientGetById);
        when(carServicePort.getCar(anyLong())).thenReturn(carDTO);
        when(clientMethods.saveClient(any(Client.class))).thenReturn(clientSave);

        ClientCarDTO clientCarDTO = useCase.buyCar(clientId, carId);

        assertEquals(1, clientCarDTO.getClient().getId());
        assertEquals("Julian", clientCarDTO.getClient().getName());
        assertEquals("ju@gmail.com", clientCarDTO.getClient().getEmail());
        assertEquals("182638721", clientCarDTO.getClient().getPhone());
    }

    @Test
    void testBuyCarThrowsException() throws ClientNotFoundException {
        Long clientId = 1L;
        Long carId = 1L;

        when(clientMethods.getById(anyLong())).thenThrow(new ClientNotFoundException("Client not found"));

        assertThrows(ClientNotFoundException.class, () -> {
            useCase.buyCar(clientId, carId);
        });

    }

    @Test
    void testGetClientList() throws ClientNotFoundException {
        int page = 0;
        int size = 1;

        when(clientMethods.getClientList(anyInt(), anyInt())).thenReturn(Arrays.asList(client));

        List<ClientDTO> clientDTOs = useCase.getClientList(page, size);

        assertEquals(1, clientDTOs.size());
    }

    @Test
    void testDeleteClient(){
        Long id = 1l;

        doNothing().when(clientMethods).deleteClient(anyLong());

        useCase.deleteClient(id);

        verify(clientMethods, times(1)).deleteClient(anyLong());
    }

}
