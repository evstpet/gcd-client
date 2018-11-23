package com.pes.gcdclient.domain.storage;

import com.pes.gcdclient.infrastructure.db.GcdRepository;
import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GcdStorageService {

    private GcdRepository repository;

    @Autowired
    public GcdStorageService(GcdRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Long saveNew(Long first, Long second) {
        GcdEntity newGcd = new GcdEntity();
        newGcd = repository.save(newGcd);
        return newGcd.getId();
    }

}
