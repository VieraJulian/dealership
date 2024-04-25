package com.example.clients.infra.outputAdapter;

import com.example.clients.domain.Client;
import com.example.clients.infra.exception.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@Sql("/test-mysql.sql")
public class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setup(){

        client = Client.builder()
                .name("Julian")
                .email("ju@gmail.com")
                .phone("182638721")
                .build();
    }

    @Test
    void testGetById() throws ClientNotFoundException {
        Long id = 1l;

        Client fClient = clientRepository.getById(id);

        assertEquals(1, fClient.getId());
        assertEquals("Julian", fClient.getName());
        assertEquals("ju@gmail.com", fClient.getEmail());
        assertEquals("182638721", fClient.getPhone());

    }

    @Test
    void testGetByIdThrowsException() {
        Long id = 5l;

        assertThrows(ClientNotFoundException.class, () -> {
            clientRepository.getById(id);
        });
    }

    @Test
    void testSaveClient(){
        Client eClient = clientRepository.saveClient(client);

        assertEquals(4, eClient.getId());
        assertEquals("Julian", eClient.getName());
        assertEquals("ju@gmail.com", eClient.getEmail());
        assertEquals("182638721", eClient.getPhone());
    }

    @Test
    void testGetClientsList() {
        int page = 0;
        int size = 3;

        List<Client> clients = clientRepository.getClientList(page, size);
        
        assertEquals(3, clients.size());
    }

    @Test
    void testDeleteClient() {
        Long id = 1l;

        clientRepository.deleteClient(id);

        assertThrows(ClientNotFoundException.class, () -> {
            clientRepository.getById(id);
        });
    }
}
