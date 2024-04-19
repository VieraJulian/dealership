package com.example.clients.infra.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCarDTO {
    private ClientDTO client;
    private List<CarDTO> cars;
}
