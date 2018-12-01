package com.pes.gcdclient.domain.storage;

import com.pes.gcdclient.domain.mapper.Gcds;
import com.pes.gcdclient.domain.vo.Calculation;
import com.pes.gcdclient.infrastructure.db.GcdRepository;
import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pes.gcdclient.domain.mapper.Gcds.gcdEntityFromCalculation;

@Service
public class GcdStorageService {

    private GcdRepository repository;

    @Autowired
    public GcdStorageService(GcdRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Long saveNew(Long first, Long second) {
        GcdEntity newGcdEntity = new GcdEntity();
        newGcdEntity.setFirst(first);
        newGcdEntity.setSecond(second);

        newGcdEntity = repository.save(newGcdEntity);
        return newGcdEntity.getId();
    }

    @Transactional
    public void save(Calculation calculation) {
        repository.save(gcdEntityFromCalculation(calculation));
    }

    @Transactional(readOnly = true)
    public Calculation getGcdCalculation(Long gcdId) {
        return repository.findById(gcdId)
                .map(Gcds::calculationFromGcdEntity)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Calculation getGcdCalculationByFirstAndSecond(Long first, Long second) {
        return repository.findByFirstAndSecond(first, second)
                .map(Gcds::calculationFromGcdEntity)
                .orElse(null);
    }
}
