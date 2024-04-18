package com.dealership.cars.infra.outputAdapter;

import com.dealership.cars.application.exception.CarNotFoundException;
import com.dealership.cars.domain.Car;
import com.dealership.cars.infra.outputport.ICarMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarRepository implements ICarMethods {

    @Autowired
    private IMySQLRepository myRepository;

    @Override
    public Car getById(Long id) throws CarNotFoundException {
        return myRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));
    }

    @Override
    public Car createCar(Car car) {
        return myRepository.save(car);
    }

    @Override
    public Car editCar(Long id, Car car) throws CarNotFoundException {
        Car car1 = myRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car not found"));

        car1.setBrand(car.getBrand());
        car1.setModel(car.getModel());
        car1.setYear(car.getYear());
        car1.setPrice(car.getPrice());

        return myRepository.save(car1);
    }

    @Override
    public List<Car> getCarList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return myRepository.findAllCars(pageable);
    }

    @Override
    public void deleteCar(Long id) {
        myRepository.deleteById(id);
    }
}
