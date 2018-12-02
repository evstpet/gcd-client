package com.pes.gcdclient.application.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class GcdRequestDto {
    @NotNull
    private Long first;
    @NotNull
    private Long second;
}
