package com.dealership.cars.infra.outputport;

import com.dealership.cars.application.exception.CarNotFoundException;
import com.dealership.cars.domain.Car;

import java.util.List;

public interface ICarMethods {

    public Car getById(Long id) throws CarNotFoundException;

    public Car createCar(Car car);

    public Car editCar(Long id, Car car) throws CarNotFoundException;

    public List<Car> getCarList(int page, int size);

    public void deleteCar(Long id);
}
