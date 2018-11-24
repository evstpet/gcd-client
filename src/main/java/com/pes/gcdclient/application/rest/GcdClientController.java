package com.pes.gcdclient.application.rest;

import com.pes.gcdclient.application.rest.dto.GcdRequestDto;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.application.service.GcdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class GcdClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcdClientController.class);

    private GcdService gcdService;

    @Autowired
    public GcdClientController(GcdService gcdService) {
        this.gcdService = gcdService;
    }

    @RequestMapping(value = "/calculate-gcd", consumes = {"application/json"})
    public Long calculateGcd(@Valid @RequestBody GcdRequestDto gcdRequestDto) {
        LOGGER.info("Request: " + gcdRequestDto);
        return gcdService.calculateGcd(gcdRequestDto.getFirst(), gcdRequestDto.getSecond());
    }

    @RequestMapping("/get-result/{gcd-id}")
    public GcdResultDto getGcd(@PathVariable("gcd-id") Long gcdId) {
        LOGGER.info("Looking for gcd with id: " + gcdId);
        return gcdService.getGcdCalculationResult(gcdId);
    }
}
