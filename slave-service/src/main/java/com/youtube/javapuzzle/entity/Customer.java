package com.youtube.javapuzzle.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
}
