package com.dealership.cars.infra.inputAdapter;

import com.dealership.cars.infra.dto.CarDTO;
import com.dealership.cars.infra.inputport.ICarInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cars")
public class CarController {

    @Autowired
    private ICarInputPort carInputPort;

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCar(@PathVariable Long id) {
        try {
            CarDTO car = carInputPort.getCar(id);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO car){
        try {
            CarDTO newCar = carInputPort.createCar(car);
            return new ResponseEntity<>(newCar, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CarDTO> editCar(@PathVariable Long id, @RequestBody CarDTO car){
        try {
            CarDTO eCar = carInputPort.editCar(id, car);
            return new ResponseEntity<>(eCar, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarDTO>> getCarList(@RequestParam int page, @RequestParam int size){
        try {
            List<CarDTO> carList = carInputPort.getCarList(page, size);
            return new ResponseEntity<>(carList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id){
        try {
            String msg = carInputPort.deleteCar(id);

            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
