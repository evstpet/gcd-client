package com.pes.gcdclient.application.event;

import com.pes.gcdclient.application.event.dto.CalculationRequestEvent;
import com.pes.gcdclient.domain.vo.Calculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitEventSender implements EventSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitEventSender.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitEventSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendEvent(Calculation calculation) {
        CalculationRequestEvent requestEvent = CalculationRequestEvent.builder()
                .id(calculation.getId())
                .first(calculation.getFirst())
                .second(calculation.getSecond())
                .build();

        LOGGER.info("Send gcd calculation request for id = " + requestEvent.getId());

        rabbitTemplate.convertAndSend(
                "gcd.client.exchange",
                "gcd.client.routing.key",
                requestEvent
        );
    }
}
