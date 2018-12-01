package com.pes.gcdclient.application.rest;

import com.pes.gcdclient.application.rest.dto.GcdRequestDto;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.application.service.GcdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class GcdClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcdClientController.class);

    private GcdService gcdService;

    @Autowired
    public GcdClientController(GcdService gcdService) {
        this.gcdService = gcdService;
    }

    @RequestMapping(value = "/calculate-gcd",
                    method = POST,
                    consumes = {APPLICATION_JSON_VALUE},
                    produces = {TEXT_PLAIN_VALUE})
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
