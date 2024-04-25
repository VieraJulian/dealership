package com.example.clients.application;

import com.example.clients.domain.Car;
import com.example.clients.domain.Client;
import com.example.clients.infra.dto.CarDTO;
import com.example.clients.infra.dto.ClientCarDTO;
import com.example.clients.infra.exception.ClientNotFoundException;
import com.example.clients.infra.outputport.ICarServicePort;
import com.example.clients.infra.outputport.IClientMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Arrays;

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

    @BeforeEach
    void setup(){
        carDTO = CarDTO.builder()
                .id(1L)
                .brand("Audi")
                .model("Q5")
                .year("2013")
                .price(BigDecimal.valueOf(2000000))
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
}
