package com.eturn.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long idGroup;

    private int status;
}
