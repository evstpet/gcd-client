package com.pes.gcdclient.domain.service;

import com.pes.gcdclient.domain.storage.GcdStorageService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GcdService {

    private GcdStorageService gcdStorageService;

    public GcdService(GcdStorageService gcdStorageService) {
        this.gcdStorageService = gcdStorageService;
    }

    @Transactional
    public Long calculateGcd(Long first, Long second) {
        return gcdStorageService.saveNew(first, second);
    }
}
