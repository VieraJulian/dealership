package com.dealership.cars.application;

import com.dealership.cars.application.exception.CarNotFoundException;
import com.dealership.cars.domain.Car;
import com.dealership.cars.infra.dto.CarDTO;
import com.dealership.cars.infra.inputport.ICarInputPort;
import com.dealership.cars.infra.outputport.ICarMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarUseCase implements ICarInputPort {

    @Autowired
    private ICarMethods carMethods;

    @Override
    public CarDTO getCar(Long id) throws CarNotFoundException {
        Car car = carMethods.getById(id);

        return CarDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .year(car.getYear())
                .model(car.getModel())
                .price(car.getPrice())
                .build();
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Car car = Car.builder()
                .brand(carDTO.getBrand())
                .year(carDTO.getYear())
                .model(carDTO.getModel())
                .price(carDTO.getPrice())
                .build();

        Car newCar = carMethods.createCar(car);

        return CarDTO.builder()
                .id(newCar.getId())
                .brand(newCar.getBrand())
                .model(newCar.getModel())
                .year(newCar.getYear())
                .price(newCar.getPrice())
                .build();
    }

    @Override
    public CarDTO editCar(Long id, CarDTO carDTO) throws CarNotFoundException {
        Car car = Car.builder()
                .brand(carDTO.getBrand())
                .model(carDTO.getModel())
                .year(carDTO.getYear())
                .price(carDTO.getPrice())
                .build();

        Car eCar = carMethods.editCar(id, car);

        return CarDTO.builder()
                .id(eCar.getId())
                .brand(eCar.getBrand())
                .model(eCar.getModel())
                .year(eCar.getYear())
                .price(eCar.getPrice())
                .build();
    }

    @Override
    public List<CarDTO> getCarList(int page, int size) {
        List<Car> carList = carMethods.getCarList(page, size);
        List<CarDTO> carDtoList = new ArrayList<>();

        for (Car car : carList) {
            carDtoList.add(CarDTO.builder()
                            .id(car.getId())
                            .brand(car.getBrand())
                            .model(car.getModel())
                            .year(car.getYear())
                            .price(car.getPrice())
                            .build());
        }

        return carDtoList;
    }

    @Override
    public String deleteCar(Long id) {
        carMethods.deleteCar(id);
        return "Car deleted successfully";
    }
}
