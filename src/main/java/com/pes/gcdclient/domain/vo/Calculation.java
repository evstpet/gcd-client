package com.pes.gcdclient.domain.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Calculation {
    private Long id;
    private Long first;
    private Long second;
    private GcdStatus status;
    private Long result;
    private String error;
}
