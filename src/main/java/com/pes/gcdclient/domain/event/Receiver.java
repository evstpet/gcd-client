package com.pes.gcdclient.domain.event;

import com.pes.gcdclient.domain.event.dto.CalculationResult;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
public class Receiver {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("${spring.rabbitmq.template.queue}"),
                    exchange = @Exchange(value = "calculator.topic", type = TOPIC)
            )
    })
    public void listen(Message<CalculationResult> calculationResult) {
        System.out.println(calculationResult.getPayload());
    }
}
