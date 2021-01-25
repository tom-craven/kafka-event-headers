package com.tom.kafkaeventheaders;

import com.tom.kafkaeventheaders.event.EventModelCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static com.tom.kafkaeventheaders.IKafkaProducer.getFuture;
import static com.tom.kafkaeventheaders.IKafkaProducer.getMessageBuilder;

@Slf4j
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Send an Event Message
     *
     * @param service sending the event
     * @param state   change that has occured
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public SendResult<String, byte[]> sendEventMessage(EventModelCommands.ServiceComponent service, EventModelCommands.EventState state, byte[] jobMetadata) throws ExecutionException, InterruptedException {
        MessageBuilder<byte[]> message = getMessageBuilder(service, state, jobMetadata);
        return getFuture(message, kafkaTemplate, log).get();
    }

}
