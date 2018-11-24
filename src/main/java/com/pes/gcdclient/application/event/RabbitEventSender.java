package com.pes.gcdclient.application.event;

import com.pes.gcdclient.application.event.dto.CalculationRequest;
import com.pes.gcdclient.domain.vo.Calculation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitEventSender implements EventSender {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitEventSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendEvent(Calculation calculation) {
        CalculationRequest result = CalculationRequest.builder()
                .id(calculation.getId())
                .first(calculation.getFirst())
                .second(calculation.getSecond())
                .build();

        rabbitTemplate.convertAndSend(
                "gcd.client.exchange",
                "gcd.client.routing.key",
                result
        );
    }
}
