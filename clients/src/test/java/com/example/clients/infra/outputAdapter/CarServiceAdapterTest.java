package com.example.clients.infra.outputAdapter;

import com.example.clients.infra.dto.CarDTO;
import com.example.clients.infra.outputport.ICarServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceAdapterTest {

    @Mock
    ICarServicePort iCarServicePort;

    @InjectMocks
    CarServiceAdapterMock carServiceAdapter;

    @Test
    void testGetCar(){
        Long id = 1L;

        CarDTO car = CarDTO.builder()
                .brand("BMW")
                .model("X5")
                .price(BigDecimal.valueOf(3000000))
                .year("2022")
                .build();

        when(iCarServicePort.getCar(anyLong())).thenReturn(car);

        CarDTO carDTO = carServiceAdapter.getCar(id);

        assertEquals(car, carDTO);
    }
}
