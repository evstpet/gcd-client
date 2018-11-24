package com.pes.gcdclient.application.event;

import com.pes.gcdclient.application.event.dto.CalculationResult;
import com.pes.gcdclient.domain.storage.GcdStorageService;
import com.pes.gcdclient.domain.vo.Calculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private GcdStorageService storageService;

    @Autowired
    public Receiver(GcdStorageService storageService) {
        this.storageService = storageService;
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("${spring.rabbitmq.template.queue}"),
                    exchange = @Exchange(value = "gcd.calculator.exchange", type = TOPIC),
                    key = {"gcd.calculator.routing.key"}
            )
    })
    public void listen(Message<CalculationResult> message) {
        CalculationResult result = message.getPayload();
        LOGGER.info("Received result: " + result);

        storageService.save(
                Optional.ofNullable(result)
                        .map(
                                calculationResult -> Calculation.builder()
                                        .id(calculationResult.getId())
                                        .result(calculationResult.getResult())
                                        .error(calculationResult.getError())
                                        .build()
                        ).orElse(null)
        );
    }
}
