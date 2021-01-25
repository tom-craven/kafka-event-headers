package com.tom.kafkaeventheaders;

import com.tom.kafkaeventheaders.event.EventModelCommands;
import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import static com.tom.kafkaeventheaders.event.EventModelHeaders.*;


public interface IKafkaProducer {

    static CompletableFuture<SendResult<String, byte[]>> getFuture(MessageBuilder<byte[]> message, KafkaTemplate<String, byte[]> kafkaTemplate, Logger log) {
        ListenableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send(message.build());

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, byte[]> result) {
                log.info("Kafka Producer sent message with result, {}", result.toString());

            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Kafka Producer Failed to send Message because exception {}", ex.getMessage());
            }

        });
        return future.completable();
    }

    static MessageBuilder<byte[]> getMessageBuilder(EventModelCommands.ServiceComponent service, EventModelCommands.EventState state, byte[] jobMetadata) {
        return MessageBuilder.
                withPayload(jobMetadata)
                .setHeader(SERVICE_NAME, service)
                .setHeader(STATUS, state)
                .setHeader(TIMESTAMP, Instant.now().toEpochMilli());
    }

}
