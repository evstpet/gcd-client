package com.pes.gcdclient.application.rest;

import com.pes.gcdclient.application.rest.dto.GcdRequestDto;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.application.service.GcdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Slf4j
public class GcdClientController {

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
        log.info("Request: " + gcdRequestDto);
        return gcdService.calculateGcd(gcdRequestDto.getFirst(), gcdRequestDto.getSecond());
    }

    @RequestMapping("/get-result/{gcd-id}")
    public GcdResultDto getGcd(@PathVariable("gcd-id") Long gcdId) {
        log.info("Looking for gcd with id: " + gcdId);
        return gcdService.getGcdCalculationResult(gcdId);
    }
}
