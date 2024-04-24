package com.dealership.cars.infra.outputAdapter;

import com.dealership.cars.domain.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.config.enabled=false",
        "eureka.client.enabled=false"
})
@Sql("/test-mysql.sql")
public class IMySQLRepositoryTest {

    @Autowired
    IMySQLRepository myRepository;

    @Test
    void testFindAllCars(){
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size);

        List<Car> carList = myRepository.findAllCars(pageable);

        assertEquals(3, carList.size());
    }
}
