package com.dealership.cars.infra.inputport;

import com.dealership.cars.application.exception.CarNotFoundException;
import com.dealership.cars.infra.dto.CarDTO;

import java.util.List;

public interface ICarInputPort {

    public CarDTO getCar(Long id) throws CarNotFoundException;
    public CarDTO createCar(CarDTO carDTO);
    public CarDTO editCar(Long id, CarDTO carDTO) throws CarNotFoundException;
    public List<CarDTO> getCarList(int page, int size);
    public String deleteCar(Long id);

}
