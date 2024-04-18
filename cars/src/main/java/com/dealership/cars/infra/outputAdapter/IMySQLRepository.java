package com.dealership.cars.infra.outputAdapter;

import com.dealership.cars.domain.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMySQLRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT c FROM Car c")
    List<Car> findAllCars(Pageable pageable);
}
