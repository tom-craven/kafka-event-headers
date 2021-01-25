package com.tom.kafkaeventheaders;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.ExecutionException;

import static com.tom.kafkaeventheaders.event.EventModelCommands.EventState.SUCCESS;
import static com.tom.kafkaeventheaders.event.EventModelCommands.ServiceComponent.FOO;

// no assertions just a demo, run the application to see the events from this test consumed
@Slf4j
@SpringBootTest
class KafkaEventHeadersApplicationTests {

    @Autowired
    KafkaProducer kafkaProducer;

    @Test
    void itShouldSendEventMessageSuccessfully() throws InterruptedException, ExecutionException, JsonProcessingException {
        byte[] payload = "helloworld".getBytes();
        SendResult<String, byte[]> result = kafkaProducer.sendEventMessage(FOO, SUCCESS, payload);
        log.info(result.toString());
    }

}
