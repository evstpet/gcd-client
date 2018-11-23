package com.pes.gcdclient.application.rest;

import com.pes.gcdclient.application.rest.dto.GcdRequest;
import com.pes.gcdclient.domain.service.GcdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GcdClientController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcdClientController.class);

    private GcdService gcdService;

    @Autowired
    public GcdClientController(GcdService gcdService) {
        this.gcdService = gcdService;
    }

    @RequestMapping("/calculate-gcd")
    public Long calculateGcd(@RequestBody GcdRequest gcdRequest) {
        LOGGER.info("Request: " + gcdRequest);
        return gcdService.calculateGcd(gcdRequest.getFirst(), gcdRequest.getSecond());
    }

}
