package com.pes.gcdclient.application.rest.dto;

import com.pes.gcdclient.domain.vo.GcdStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class GcdResultDto {
    private Long id;
    private GcdStatus status;
    private Long result;
    private String error;
}
