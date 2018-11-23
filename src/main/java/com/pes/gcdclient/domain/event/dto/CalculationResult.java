package com.pes.gcdclient.domain.event.dto;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class CalculationResult implements Serializable {
    private Long id;
    private Long result;
    private String error;
}
