package com.pes.gcdclient.application.service;

import com.pes.gcdclient.application.event.EventSender;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.domain.storage.GcdStorageService;
import com.pes.gcdclient.domain.vo.Calculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class GcdService {

    private GcdStorageService gcdStorageService;

    private EventSender eventSender;

    @Autowired
    public GcdService(
            GcdStorageService gcdStorageService,
            EventSender eventSender
    ) {
        this.gcdStorageService = gcdStorageService;
        this.eventSender = eventSender;
    }

    @Transactional
    public Long calculateGcd(Long first, Long second) {
        Long id = gcdStorageService.saveNew(first, second);
        Calculation calculation = Calculation.builder()
                .id(id)
                .first(first)
                .second(second)
                .build();

        eventSender.sendEvent(calculation);

        return id;
    }

    @Transactional(readOnly = true)
    public GcdResultDto getGcdCalculationResult(Long gcdId) {
        return Optional.ofNullable(gcdStorageService.getGcdCalculation(gcdId))
                .map(calculation -> GcdResultDto.builder()
                        .id(calculation.getId())
                        .status(calculation.getStatus())
                        .result(calculation.getResult())
                        .error(calculation.getError())
                        .build()
                ).orElse(null);
    }
}
