package com.pes.gcdclient.application.event;


import com.pes.gcdclient.application.event.dto.CalculationRequestEvent;
import com.pes.gcdclient.application.event.dto.CalculationResultEvent;
import com.pes.gcdclient.domain.storage.GcdStorageService;
import com.pes.gcdclient.domain.vo.Calculation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.pes.gcdclient.domain.mapper.Gcds.calculationFromCalculationResultEvent;
import static com.pes.gcdclient.domain.mapper.Gcds.calculationRequestEventFromCalculation;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {EventListener.class, RabbitEventSender.class})
public class EventsTests extends AbstractTestNGSpringContextTests {

    @MockBean
    private GcdStorageService gcdStorageService = mock(GcdStorageService.class);

    @MockBean
    private RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);

    private EventListener listener;

    private RabbitEventSender eventSender;

    private static final String OUTCOME_EXCHANGE = "outcome.exchange";
    private static final String ROUTING_KEY = "routing.key";

    private static final Long ID = 1L;
    private static final Long FIRST = 11L;
    private static final Long SECOND = 22L;
    private static final Long RESULT = 11L;
    private static final String ERROR = "Error";

    @BeforeClass
    public void setUpClass() {
        listener = new EventListener(gcdStorageService);
        eventSender = new RabbitEventSender(rabbitTemplate, OUTCOME_EXCHANGE, ROUTING_KEY);
    }

    @Test(dataProvider = "eventListenerTestData")
    public void testEventListener(Long result, String error) {
        CalculationResultEvent receivedEvent = new CalculationResultEvent();
        receivedEvent.setId(ID);
        receivedEvent.setFirst(FIRST);
        receivedEvent.setSecond(SECOND);
        receivedEvent.setResult(result);
        receivedEvent.setError(error);

        Message<CalculationResultEvent> message = new GenericMessage<>(receivedEvent);

        Calculation savedResult = calculationFromCalculationResultEvent(receivedEvent);

        listener.listen(message);

        verify(gcdStorageService).save(savedResult);
    }

    @DataProvider(name = "eventListenerTestData")
    private static Object[][] eventListenerTestData() {
        return new Object[][]{
                {RESULT, null},
                {null, ERROR}
        };
    }

    @Test
    public void testEventSender() {
        Calculation sentCalculation = Calculation.builder()
                .id(ID)
                .first(FIRST)
                .second(SECOND)
                .build();

        CalculationRequestEvent sentRequestEvent = calculationRequestEventFromCalculation(sentCalculation);

        eventSender.sendEvent(sentCalculation);

        verify(rabbitTemplate).convertAndSend(
                OUTCOME_EXCHANGE,
                ROUTING_KEY,
                sentRequestEvent
        );
    }
}
