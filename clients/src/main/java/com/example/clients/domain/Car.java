package com.example.clients.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cars")
public class Car {

    @Id
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;
}
