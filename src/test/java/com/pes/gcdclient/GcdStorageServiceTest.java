package com.pes.gcdclient;

import com.pes.gcdclient.domain.storage.GcdStorageService;
import com.pes.gcdclient.domain.vo.Calculation;
import com.pes.gcdclient.infrastructure.db.GcdRepository;
import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.pes.gcdclient.domain.vo.GcdStatus.ERROR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GcdStorageServiceTest {

    private GcdRepository repository;

    private GcdStorageService storageService;

    @BeforeClass
    public void tearDown() {
        repository = mock(GcdRepository.class);
        storageService = new GcdStorageService(repository);
    }

    @Test
    public void testSaveNew() {
        GcdEntity savedEntity = new GcdEntity();
        savedEntity.setId(111L);
        when(repository.save(any(GcdEntity.class)))
                .thenReturn(savedEntity);

        Long id = storageService.saveNew(1L, 1L);

        assertThat(id).isEqualTo(111L);
    }

    @Test
    public void testGetGcdCalculationWithErrorStatus() {
        GcdEntity savedEntity = new GcdEntity();
        savedEntity.setId(1L);
        savedEntity.setError("Some exception has occurred!");
        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(savedEntity));

        Calculation result = storageService.getGcdCalculation(1L);

        assertThat(result.getId()).isEqualTo(savedEntity.getId());
        assertThat(result.getResult()).isNull();
        assertThat(result.getStatus()).isEqualTo(ERROR);
        assertThat(result.getError()).isEqualTo(savedEntity.getError());
    }
}
