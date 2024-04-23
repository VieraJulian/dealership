package com.dealership.cars.application;

import com.dealership.cars.application.exception.CarNotFoundException;
import com.dealership.cars.infra.dto.CarDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@Sql("/test-mysql.sql")
public class CarUseCaseTest {

    @Autowired
    CarUseCase carUseCase;

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
    void testGetCar() throws CarNotFoundException {
        Long id = 1L;
        CarDTO car = carUseCase.getCar(id);

        assertEquals("Audi", car.getBrand());
        assertEquals("Q5", car.getModel());
        assertEquals(0, BigDecimal.valueOf(2000000).compareTo(car.getPrice()));
        assertEquals("2013", car.getYear());
    }

    @Test
    void testGetCarThrowsException() {
        Long id = 5L;

        assertThrows(CarNotFoundException.class, () -> {
            carUseCase.getCar(id);
        });
    }

    @Test
    void testCreateCar(){
        CarDTO newCar = carUseCase.createCar(carDTO);

        assertEquals("BMW", newCar.getBrand());
        assertEquals("X5", newCar.getModel());
        assertEquals(0, BigDecimal.valueOf(3000000).compareTo(newCar.getPrice()));
        assertEquals("2022", newCar.getYear());
    }

    @Test
    void testEditCar() throws CarNotFoundException {
        Long id = 1L;
        CarDTO carInfo = CarDTO.builder()
                .brand("Audi")
                .model("Q5")
                .price(BigDecimal.valueOf(2250000))
                .year("2015")
                .build();

        CarDTO editedCar = carUseCase.editCar(id, carInfo);

        assertEquals("Audi", editedCar.getBrand());
        assertEquals("Q5", editedCar.getModel());
        assertEquals(0, BigDecimal.valueOf(2250000).compareTo(editedCar.getPrice()));
        assertEquals("2015", editedCar.getYear());
    }

    @Test
    void testEditCarThrowsException() {
        Long id = 10L;
        CarDTO carInfo = CarDTO.builder()
                .brand("Audi")
                .model("Q5")
                .price(BigDecimal.valueOf(2250000))
                .year("2015")
                .build();

        assertThrows(CarNotFoundException.class, () -> {
            carUseCase.editCar(id, carInfo);
        });
    }

    @Test
    void testGetCarList() {
        int page = 0;
        int size = 2;

        List<CarDTO> carList = carUseCase.getCarList(page, size);

        assertEquals(2, carList.size());
    }

    @Test
    void testDeleteCar() throws CarNotFoundException {
        Long id = 3L;

        carUseCase.deleteCar(id);

        assertThrows(CarNotFoundException.class, () -> {
            carUseCase.getCar(id);
        });

    }




























}
