package com.pes.gcdclient.application.event;

import com.pes.gcdclient.application.event.dto.CalculationResultEvent;
import com.pes.gcdclient.domain.mapper.Gcds;
import com.pes.gcdclient.domain.storage.GcdStorageService;
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
public class EventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

    private GcdStorageService storageService;

    @Autowired
    public EventListener(GcdStorageService storageService) {
        this.storageService = storageService;
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("${spring.rabbitmq.template.queue}"),
                    exchange = @Exchange(value = "${calculator.exchange}", type = TOPIC),
                    key = {"${client.routing.key}"}
            )
    })
    public void listen(Message<CalculationResultEvent> message) {
        CalculationResultEvent result = message.getPayload();
        LOGGER.info("Received result: " + result);

        storageService.save(
                Optional.ofNullable(result)
                        .map(Gcds::calculationFromCalculationResultEvent)
                        .orElse(null)
        );
    }
}
