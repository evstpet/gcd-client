package com.pes.gcdclient.application.event.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CalculationRequestEvent implements Serializable {
    private Long id;
    private Long first;
    private Long second;
}
