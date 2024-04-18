package com.dealership.cars.infra.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {

    private Long id;
    private String brand;
    private String model;
    private String year;
    private BigDecimal price;
}
