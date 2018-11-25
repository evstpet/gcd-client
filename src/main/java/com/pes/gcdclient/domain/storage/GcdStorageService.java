package com.pes.gcdclient.domain.storage;

import com.pes.gcdclient.domain.vo.Calculation;
import com.pes.gcdclient.domain.vo.GcdStatus;
import com.pes.gcdclient.infrastructure.db.GcdRepository;
import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static java.util.Objects.isNull;

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
        GcdEntity gcdEntity = new GcdEntity();

        gcdEntity.setId(calculation.getId());
        gcdEntity.setResult(calculation.getResult());
        gcdEntity.setError(calculation.getError());

        repository.save(gcdEntity);
    }

    @Transactional(readOnly = true)
    public Calculation getGcdCalculation(Long gcdId) {
        return repository.findById(gcdId)
                .map(entity -> Calculation.builder()
                        .id(entity.getId())
                        .status(getGcdStatus(entity))
                        .result(entity.getResult())
                        .error(entity.getError())
                        .build()
                ).orElse(null);
    }

    private GcdStatus getGcdStatus(GcdEntity entity) {
        if (!StringUtils.isEmpty(entity.getError())) {
            return GcdStatus.ERROR;
        }

        if (isNull(entity.getResult())) {
            return GcdStatus.NOT_COMPLETED;
        }

        return GcdStatus.COMPLETED;
    }
}
