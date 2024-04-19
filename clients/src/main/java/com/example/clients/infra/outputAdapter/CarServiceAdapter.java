package com.example.clients.infra.outputAdapter;

import com.example.clients.infra.dto.CarDTO;
import com.example.clients.infra.outputport.ICarServicePort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cars")
public interface CarServiceAdapter extends ICarServicePort {
    @Override
    @GetMapping("/cars/{id}")
    public CarDTO getCar(@PathVariable Long id);
}
