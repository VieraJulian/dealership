package com.dealership.cars.infra.outputAdapter;

import com.dealership.cars.application.exception.CarNotFoundException;
import com.dealership.cars.domain.Car;
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
public class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    private Car car;

    @BeforeEach
    void setup(){
        car = Car.builder()
                .brand("BMW")
                .model("X5")
                .price(BigDecimal.valueOf(3000000))
                .year("2022")
                .build();
    }

    @Test
    void testGetById() throws CarNotFoundException {
        Long id = 1L;

        Car car1 = carRepository.getById(id);

        assertEquals(1, car1.getId());
        assertEquals("Audi", car1.getBrand());
        assertEquals("Q5", car1.getModel());
        assertEquals("2013", car1.getYear());
        assertEquals(0, car1.getPrice().compareTo(BigDecimal.valueOf(2000000)));
    }

    @Test
    void testGetByIdThrowsException() {
        Long id = 5L;

        assertThrows(CarNotFoundException.class, () -> {
            carRepository.getById(id);
        });
    }

    @Test
    void testCreateCar(){
        Car newCar = carRepository.createCar(car);

        assertEquals(4, newCar.getId());
        assertEquals("BMW", newCar.getBrand());
        assertEquals("X5", newCar.getModel());
        assertEquals("2022", newCar.getYear());
        assertEquals(0, newCar.getPrice().compareTo(BigDecimal.valueOf(3000000)));
    }

    @Test
    void testEditCar() throws CarNotFoundException {
        Long id = 2L;

        Car carInfo = Car.builder()
                .brand("Toyota")
                .model("Hilux")
                .price(BigDecimal.valueOf(3250000))
                .year("2024")
                .build();

        Car editCar = carRepository.editCar(id, carInfo);

        assertEquals(2, editCar.getId());
        assertEquals("Toyota", editCar.getBrand());
        assertEquals("Hilux", editCar.getModel());
        assertEquals("2024", editCar.getYear());
        assertEquals(0, editCar.getPrice().compareTo(BigDecimal.valueOf(3250000)));
    }

    @Test
    void testEditCarThrowsException(){
        Long id = 5L;

        Car carInfo = Car.builder()
                .brand("Toyota")
                .model("Hilux")
                .price(BigDecimal.valueOf(3250000))
                .year("2024")
                .build();

        assertThrows(CarNotFoundException.class, () -> {
            carRepository.editCar(id, carInfo);
        });
    }

    @Test
    void testGetCarList(){
        int page = 0;
        int size = 3;

        List<Car> carList = carRepository.getCarList(page, size);

        assertEquals(3, carList.size());
    }

    @Test
    void testDeleteCar() {
        Long id = 1L;

        carRepository.deleteCar(id);

        assertThrows(CarNotFoundException.class, () -> {
            carRepository.getById(id);
        });
    }
}
