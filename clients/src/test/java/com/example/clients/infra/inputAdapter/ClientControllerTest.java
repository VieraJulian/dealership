package com.example.clients.infra.inputAdapter;

import com.example.clients.infra.dto.CarDTO;
import com.example.clients.infra.dto.ClientCarDTO;
import com.example.clients.infra.dto.ClientDTO;
import com.example.clients.infra.exception.ClientNotFoundException;
import com.example.clients.infra.inputport.IClientInputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.Matchers.is;
import org.springframework.http.MediaType;

@WebMvcTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IClientInputPort clientInputPort;

    private ClientCarDTO clientCarDTO;

    private ClientDTO clientDTO;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        clientDTO = ClientDTO.builder()
                .id(1l)
                .name("Julian")
                .email("ju@gmail.com")
                .phone("182638721")
                .build();

        CarDTO carDTO = CarDTO.builder()
                .id(1L)
                .brand("Audi")
                .model("Q5")
                .year("2013")
                .price(BigDecimal.valueOf(2000000))
                .build();

        List<CarDTO> carDTOList = new ArrayList<>();
        carDTOList.add(carDTO);

        clientCarDTO = ClientCarDTO.builder()
                .client(clientDTO)
                .cars(carDTOList)
                .build();
    }

    @Test
    void testGetClientById() throws Exception {
        Long id = 1L;

        when(clientInputPort.getClientById(anyLong())).thenReturn(clientCarDTO);

        mockMvc.perform(get("/clients/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("client.id", is(1)))
                .andExpect(jsonPath("client.name", is("Julian")))
                .andExpect(jsonPath("client.email", is("ju@gmail.com")))
                .andExpect(jsonPath("client.phone", is("182638721")))
                .andExpect(jsonPath("cars[0].id", is(1)));

    }

    @Test
    void testGetClientList() throws Exception {
        int page = 0;
        int size = 1;

        when(clientInputPort.getClientList(anyInt(), anyInt())).thenReturn(List.of(clientDTO));

        mockMvc.perform(get("/clients/all?page=" + page + "&size=" + size))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("[0].id", is(1)));
    }

    @Test
    void testCreateClient() throws Exception {
        when(clientInputPort.createClient(any(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(post("/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Julian")))
                .andExpect(jsonPath("$.email", is("ju@gmail.com")))
                .andExpect(jsonPath("$.phone", is("182638721")));
    }

    @Test
    void testEditClient() throws Exception {
        Long id = 1l;

        when(clientInputPort.editClient(anyLong(), any(ClientDTO.class))).thenReturn(clientDTO);

        mockMvc.perform(put("/clients/edit/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Julian")))
                .andExpect(jsonPath("$.email", is("ju@gmail.com")))
                .andExpect(jsonPath("$.phone", is("182638721")));
    }

    @Test
    void testBuyCar() throws Exception {
        Long clientId = 1l;
        Long cardId = 1l;

        when(clientInputPort.buyCar(anyLong(), anyLong())).thenReturn(clientCarDTO);

        mockMvc.perform(post("/clients/buyCar/" + clientId + "/" + cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("client.id", is(1)))
                .andExpect(jsonPath("client.name", is("Julian")))
                .andExpect(jsonPath("client.email", is("ju@gmail.com")))
                .andExpect(jsonPath("client.phone", is("182638721")))
                .andExpect(jsonPath("cars[0].id", is(1)));
    }

    @Test
    void testDeleteClient() throws Exception {
        Long id = 1l;

        when(clientInputPort.deleteClient(anyLong())).thenReturn("Client deleted successfully");

        mockMvc.perform(delete("/clients/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Client deleted successfully")));
    }
}
