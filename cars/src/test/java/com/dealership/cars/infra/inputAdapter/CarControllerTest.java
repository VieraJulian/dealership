package com.dealership.cars.infra.inputAdapter;

import com.dealership.cars.domain.Car;
import com.dealership.cars.infra.dto.CarDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@Sql("/test-mysql.sql")
@AutoConfigureWebTestClient
public class CarControllerTest {

    @Autowired
    WebTestClient client;

    private CarDTO carDTO;

    @BeforeEach
    void setup() {
        carDTO = CarDTO.builder()
                .brand("BMW")
                .model("X5")
                .price(BigDecimal.valueOf(3000000))
                .year("2022")
                .build();
    }

    @Test
    void testGetCar(){
        Long id = 1L;

        client.get().uri("http://localhost:8000/cars/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(1)
                .jsonPath("brand").isEqualTo("Audi")
                .jsonPath("model").isEqualTo("Q5")
                .jsonPath("price").isEqualTo(2000000)
                .jsonPath("year").isEqualTo("2013");
    }

    @Test
    void testCreateCar(){
        client.post().uri("http://localhost:8000/cars/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(carDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("id").isEqualTo(4)
                .jsonPath("brand").isEqualTo("BMW")
                .jsonPath("model").isEqualTo("X5")
                .jsonPath("price").isEqualTo(3000000)
                .jsonPath("year").isEqualTo("2022");

    }

    @Test
    void testEditCar(){
        Long id = 2L;

        CarDTO carDTO1 = CarDTO.builder()
                .brand("Toyota")
                .model("Hilux")
                .price(BigDecimal.valueOf(3250000))
                .year("2024")
                .build();

        client.put().uri("http://localhost:8000/cars/edit/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(carDTO1)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(2)
                .jsonPath("brand").isEqualTo("Toyota")
                .jsonPath("model").isEqualTo("Hilux")
                .jsonPath("price").isEqualTo(3250000)
                .jsonPath("year").isEqualTo("2024");
    }

    @Test
    void testGetCarList(){
        int page = 0;
        int size = 3;

        client.get().uri("http://localhost:8000/cars/all?page=" + page +"&size=" + size)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CarDTO.class)
                .consumeWith(response -> {
                    List<CarDTO> cars = response.getResponseBody();
                    assertNotNull(cars);
                    assertEquals(3, cars.size());
                });
    }

    @Test
    void testDeleteCar(){
        Long id = 1L;

        client.delete().uri("http://localhost:8000/cars/delete/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Car deleted successfully");
    }

}
