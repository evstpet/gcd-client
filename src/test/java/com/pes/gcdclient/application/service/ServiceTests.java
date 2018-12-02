package com.pes.gcdclient.application.service;

import com.pes.gcdclient.application.event.EventSender;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.domain.storage.GcdStorageService;
import com.pes.gcdclient.domain.vo.Calculation;
import com.pes.gcdclient.domain.vo.GcdStatus;
import com.pes.gcdclient.infrastructure.exception.ResourceNotFoundException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.pes.gcdclient.domain.vo.GcdStatus.COMPLETED;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

@SpringBootTest(classes = {GcdService.class})
public class ServiceTests extends AbstractTestNGSpringContextTests {

    @MockBean
    private static GcdStorageService gcdStorageService = mock(GcdStorageService.class);

    @MockBean
    private static EventSender eventSender = mock(EventSender.class);

    private GcdService gcdService;

    private static final Long ID = 1L;
    private static final Long NEW_ID = 12L;
    private static final Long FIRST = 2L;
    private static final Long SECOND = 4L;
    private static final Long RESULT = 2L;
    private static final String ERROR_MESSAGE = "Can't find gcd for id: 1";


    @BeforeClass
    public void setUp() {
        gcdService = new GcdService(gcdStorageService,
                                    eventSender);
    }

    @Test(dataProvider = "calculateGcdData")
    public void testCalculateGcd(
            Runnable preconditions,
            Runnable assertions,
            Long id
    ) {
        preconditions.run();

        Long actaulId = gcdService.calculateGcd(FIRST, SECOND);

        assertEquals(actaulId, id);
        assertions.run();
    }

    @DataProvider(name = "calculateGcdData")
    public static Object[][] calculateGcdData() {
        return new Object[][]{
                {
                        (Runnable) () -> when(gcdStorageService.getGcdCalculationByFirstAndSecond(FIRST, SECOND))
                                .thenReturn(createSimpleCalculation(ID, RESULT, null)),
                        (Runnable) () -> {
                            verify(gcdStorageService, times(0)).saveNew(FIRST, SECOND);
                            verify(eventSender, times(0)).sendEvent(createSimpleCalculation(ID, RESULT, null));
                        },
                        ID
                },
                {
                        (Runnable) () -> {
                            when(gcdStorageService.getGcdCalculationByFirstAndSecond(FIRST, SECOND)).thenReturn(null);
                            when(gcdStorageService.saveNew(FIRST, SECOND)).thenReturn(NEW_ID);
                        },
                        (Runnable) () ->
                                verify(eventSender, times(1)).sendEvent(createSimpleCalculation(NEW_ID, null, null))
                        ,
                        NEW_ID
                }
        };
    }

    @Test
    public void testCalculationResultSuccess() {
        when(gcdStorageService.getGcdCalculation(ID)).thenReturn(createSimpleCalculation(ID, RESULT, COMPLETED));
        GcdResultDto expectedResultDto = GcdResultDto.builder()
                .id(ID)
                .result(RESULT)
                .status(COMPLETED)
                .build();

        GcdResultDto resultDto = gcdService.getGcdCalculationResult(ID);

        assertEquals(resultDto, expectedResultDto);
    }

    @Test(
            expectedExceptions = {ResourceNotFoundException.class},
            expectedExceptionsMessageRegExp = ERROR_MESSAGE
    )
    public void testCalculationResultError() {
        when(gcdStorageService.getGcdCalculation(ID)).thenReturn(null);

        gcdService.getGcdCalculationResult(ID);
    }

    private static Calculation createSimpleCalculation(
            Long id,
            Long result,
            GcdStatus status
    ) {
        return Calculation.builder()
                .id(id)
                .first(FIRST)
                .second(SECOND)
                .status(status)
                .result(result)
                .build();
    }
}
