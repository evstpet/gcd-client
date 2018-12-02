package com.pes.gcdclient.application.event;

import com.pes.gcdclient.application.event.dto.CalculationRequestEvent;
import com.pes.gcdclient.domain.vo.Calculation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.pes.gcdclient.domain.mapper.Gcds.calculationRequestEventFromCalculation;

@Service
@Slf4j
public class RabbitEventSender implements EventSender {

    private RabbitTemplate rabbitTemplate;

    private String outcomeExchange;

    private String routingKey;

    @Autowired
    public RabbitEventSender(
            RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.template.exchange}")
            String outcomeExchange,
            @Value("${calculator.routing.key}")
            String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.outcomeExchange = outcomeExchange;
        this.routingKey = routingKey;
    }

    @Override
    public void sendEvent(Calculation calculation) {
        CalculationRequestEvent requestEvent = calculationRequestEventFromCalculation(calculation);

        log.info("Send gcd calculation request for id = " + requestEvent.getId());

        rabbitTemplate.convertAndSend(
                outcomeExchange,
                routingKey,
                requestEvent
        );
    }
}
