package com.tom.kafkaeventheaders;


import com.tom.kafkaeventheaders.event.EventModelHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "${spring.kafka.template.default-topic}",
        containerFactory = "kafkaListenerContainerFactory")
public class KafkaConsumer {

    @KafkaHandler
    public void consumeEventMessage(
            @Header(value = EventModelHeaders.SERVICE_NAME, required = false) String service,
            @Header(value = EventModelHeaders.STATUS, required = false) String status,
            @Header(value = EventModelHeaders.TIMESTAMP, required = false) Long epochMillis,
            @Payload byte[] payload
    ) {
        log.info("received\nService: {}\nStatus: {}\nTimestamp: {}\nPayload: {}", service, status, epochMillis, payload);
    }
}
