package com.example.clients.infra.outputAdapter;

import com.example.clients.infra.dto.CarDTO;
import com.example.clients.infra.outputport.ICarServicePort;

public class CarServiceAdapterMock implements CarServiceAdapter{

    private ICarServicePort carServicePort;

    public CarServiceAdapterMock(ICarServicePort iCarServicePort) {
        this.carServicePort = iCarServicePort;
    }
    @Override
    public CarDTO getCar(Long id) {
        return carServicePort.getCar(id);
    }
}
