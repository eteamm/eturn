package com.eturn.domain;

import jakarta.persistence.*;

@Entity
@Table
public class allowGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idTurn;
    private Long idUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTurn() {
        return idTurn;
    }

    public void setIdTurn(Long idTurn) {
        this.idTurn = idTurn;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
