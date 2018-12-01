package com.pes.gcdclient.domain.mapper;

import com.pes.gcdclient.application.event.dto.CalculationRequestEvent;
import com.pes.gcdclient.application.event.dto.CalculationResultEvent;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.domain.vo.Calculation;
import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;

import static com.pes.gcdclient.domain.vo.GcdStatus.getStatus;

public final class Gcds {

    private Gcds() {
    }

    public static GcdEntity gcdEntityFromCalculation(Calculation calculation) {
        GcdEntity gcdEntity = new GcdEntity();

        gcdEntity.setId(calculation.getId());
        gcdEntity.setFirst(calculation.getFirst());
        gcdEntity.setSecond(calculation.getSecond());
        gcdEntity.setResult(calculation.getResult());
        gcdEntity.setError(calculation.getError());

        return gcdEntity;
    }

    public static Calculation calculationFromGcdEntity(GcdEntity entity) {
        return Calculation.builder()
                .id(entity.getId())
                .first(entity.getFirst())
                .second(entity.getSecond())
                .result(entity.getResult())
                .error(entity.getError())
                .status(getStatus(entity.getResult(), entity.getError()))
                .build();
    }

    public static Calculation calculationFromCalculationResultEvent(CalculationResultEvent event) {
        return Calculation.builder()
                .id(event.getId())
                .first(event.getFirst())
                .second(event.getSecond())
                .result(event.getResult())
                .error(event.getError())
                .status(getStatus(event.getResult(), event.getError()))
                .build();
    }

    public static CalculationRequestEvent calculationRequestEventFromCalculation(Calculation calculation) {
        return CalculationRequestEvent.builder()
                .id(calculation.getId())
                .first(calculation.getFirst())
                .second(calculation.getSecond())
                .build();
    }

    public static GcdResultDto gcdResultDtoFromCalculation(Calculation calculation) {
        return GcdResultDto.builder()
                .id(calculation.getId())
                .status(calculation.getStatus())
                .result(calculation.getResult())
                .error(calculation.getError())
                .build();
    }
}
