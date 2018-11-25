package com.pes.gcdclient;

import com.pes.gcdclient.application.event.EventSender;
import com.pes.gcdclient.application.event.RabbitEventSender;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.application.service.GcdService;
import com.pes.gcdclient.domain.storage.GcdStorageService;
import com.pes.gcdclient.domain.vo.Calculation;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.pes.gcdclient.domain.vo.GcdStatus.COMPLETED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class GcdServiceTest {

    private GcdStorageService storageService = mock(GcdStorageService.class);

    private EventSender eventSender = mock(RabbitEventSender.class);

    private GcdService gcdService;

    @BeforeClass
    public void setUp() {
        when(storageService.saveNew(anyLong(), anyLong())).thenReturn(1L);
        when(storageService.getGcdCalculation(anyLong()))
                .thenReturn(
                        Calculation.builder()
                                .id(1L)
                                .result(10L)
                                .status(COMPLETED)
                                .error(null)
                                .build()
                );
        gcdService = new GcdService(storageService, eventSender);
    }

    @Test
    public void testCalculateGcd() {
        Long res = gcdService.calculateGcd(1L, 2L);

        assertThat(res).isEqualTo(1L);
        verify(eventSender, times(1)).sendEvent(any(Calculation.class));
    }

    @Test
    public void testGetGcdCalculationResult() {
        GcdResultDto result = gcdService.getGcdCalculationResult(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getResult()).isEqualTo(10L);
        assertThat(result.getStatus()).isEqualTo(COMPLETED);
        assertThat(result.getError()).isNull();
    }
}
