package com.example.clients.infra.outputport;

import com.example.clients.infra.dto.CarDTO;
public interface ICarServicePort {

    public CarDTO getCar(Long id);
}
