package com.pes.gcdclient.application.rest.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
public class GcdRequestDto {
    @NotNull
    private Long first;
    @NotNull
    private Long second;
}
