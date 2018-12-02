package com.pes.gcdclient.application.rest;

import com.pes.gcdclient.application.rest.dto.GcdRequestDto;
import com.pes.gcdclient.application.rest.dto.GcdResultDto;
import com.pes.gcdclient.application.service.GcdService;
import com.pes.gcdclient.domain.vo.GcdStatus;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.pes.gcdclient.domain.vo.GcdStatus.COMPLETED;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@SpringBootTest(classes = {GcdClientController.class})
public class ControllerTests extends AbstractTestNGSpringContextTests {

    @MockBean
    private GcdService gcdService = Mockito.mock(GcdService.class);

    private GcdClientController controller;

    private static final Long ID = 111L;
    private static final Long FIRST = 1L;
    private static final Long SECOND = 2L;
    private static final Long RESULT = 1L;
    private static final GcdStatus STATUS = COMPLETED;
    private static final String ERROR = "Error!";

    @BeforeClass
    public void setUpClass() {
        controller = new GcdClientController(gcdService);
    }

    @Test
    public void testPost() {
        GcdRequestDto gcdRequestDto = new GcdRequestDto();
        gcdRequestDto.setFirst(FIRST);
        gcdRequestDto.setSecond(SECOND);

        controller.calculateGcd(gcdRequestDto);

        verify(gcdService).calculateGcd(FIRST, SECOND);
    }

    @Test
    public void testGet() {
        GcdResultDto expectedGcdResultDto = GcdResultDto.builder()
                .id(ID)
                .result(RESULT)
                .status(STATUS)
                .error(ERROR)
                .build();

        when(gcdService.getGcdCalculationResult(ID)).thenReturn(expectedGcdResultDto);

        GcdResultDto actualGcdResultDto = controller.getGcd(ID);

        assertEquals(actualGcdResultDto, expectedGcdResultDto);
    }
}
