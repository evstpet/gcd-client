package com.pes.gcdclient.domain.vo;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Calculation {
    private Long id;
    private Long first;
    private Long second;
    private GcdStatus status;
    private Long result;
    private String error;
}
