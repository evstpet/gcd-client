package com.pes.gcdclient.domain;

import com.pes.gcdclient.domain.storage.GcdStorageService;
import com.pes.gcdclient.domain.vo.Calculation;
import com.pes.gcdclient.infrastructure.db.GcdRepository;
import com.pes.gcdclient.infrastructure.db.entity.GcdEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static com.pes.gcdclient.domain.vo.GcdStatus.COMPLETED;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

@SpringBootTest(classes = {GcdStorageService.class})
public class GcdStorageServiceTest extends AbstractTestNGSpringContextTests {

    @MockBean
    private GcdRepository repository = mock(GcdRepository.class);

    private GcdStorageService storageService;

    private static final Long ID = 1L;
    private static final Long FIRST = 11L;
    private static final Long SECOND = 22L;
    private static final Long RESULT = 11L;

    @BeforeClass
    public void setUpClass() {
        storageService = new GcdStorageService(repository);
    }

    @Test
    public void testSave() {
        GcdEntity entity = createSimpleGcdEntity();

        storageService.save(createSimpleCalculation());

        verify(repository).save(entity);
    }

    @Test
    public void testGcdCalculation() {
        Calculation expectedCalculation = createSimpleCalculation();

        GcdEntity entity = createSimpleGcdEntity();
        when(repository.findById(ID)).thenReturn(Optional.of(entity));

        Calculation actualCalculation = storageService.getGcdCalculation(ID);

        assertEquals(actualCalculation, expectedCalculation);
    }

    @Test
    public void testGcdCalculationByFirstAndSecond() {

        Calculation expectedCalculation = createSimpleCalculation();

        GcdEntity entity = createSimpleGcdEntity();
        when(repository.findByFirstAndSecond(FIRST, SECOND)).thenReturn(Optional.of(entity));

        Calculation actualCalculation = storageService.getGcdCalculationByFirstAndSecond(FIRST, SECOND);

        assertEquals(actualCalculation, expectedCalculation);

    }

    private static Calculation createSimpleCalculation() {
        return Calculation.builder()
                .id(ID)
                .first(FIRST)
                .second(SECOND)
                .status(COMPLETED)
                .result(RESULT)
                .build();
    }

    private static GcdEntity createSimpleGcdEntity() {
        GcdEntity entity = new GcdEntity();

        entity.setId(ID);
        entity.setFirst(FIRST);
        entity.setSecond(SECOND);
        entity.setResult(RESULT);

        return entity;
    }
}
