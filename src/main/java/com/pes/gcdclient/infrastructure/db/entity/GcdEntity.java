package com.pes.gcdclient.infrastructure.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class GcdEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long first;
    private Long second;
    private Long result;
    private String error;
}
