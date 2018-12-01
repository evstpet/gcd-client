package com.pes.gcdclient.application.event.dto;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class CalculationResultEvent implements Serializable {
    private Long id;
    private Long first;
    private Long second;
    private Long result;
    private String error;
}
