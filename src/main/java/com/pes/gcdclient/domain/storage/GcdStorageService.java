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
        GcdEntity newGcd = new GcdEntity();
        newGcd.setFirst(first);
        newGcd.setSecond(second);

        newGcd = repository.save(newGcd);
        return newGcd.getId();
    }

    @Transactional
    public void save(Calculation calculation) {
        GcdEntity entity = new GcdEntity();

        entity.setId(calculation.getId());
        entity.setResult(calculation.getResult());
        entity.setError(calculation.getError());

        repository.save(entity);
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
