package com.pes.gcdclient.application.event;

import com.pes.gcdclient.application.event.dto.CalculationRequestEvent;
import com.pes.gcdclient.domain.vo.Calculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.pes.gcdclient.domain.mapper.Gcds.calculationRequestEventFromCalculation;

@Service
public class RabbitEventSender implements EventSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitEventSender.class);

    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    private String outcomeExchange;

    @Value("${calculator.routing.key}")
    private String routingKey;

    @Autowired
    public RabbitEventSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendEvent(Calculation calculation) {
        CalculationRequestEvent requestEvent = calculationRequestEventFromCalculation(calculation);

        LOGGER.info("Send gcd calculation request for id = " + requestEvent.getId());

        rabbitTemplate.convertAndSend(
                outcomeExchange,
                routingKey,
                requestEvent
        );
    }
}
