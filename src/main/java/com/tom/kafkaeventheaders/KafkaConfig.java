package com.tom.kafkaeventheaders;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.converter.MessagingMessageConverter;


@Slf4j
@EnableKafka
@Configuration
public class KafkaConfig {

    private final String APP_PACKAGE_NAME;
    private final ConsumerFactory<String, byte[]> consumerFactory;
    private final KafkaAdmin kafkaAdmin;

    @Autowired
    public KafkaConfig(@Value("${spring.application.package-name}") String packageName,
                       ConsumerFactory<String, byte[]> consumerFactory, KafkaAdmin kafkaAdmin) {
        this.APP_PACKAGE_NAME = packageName;
        this.consumerFactory = consumerFactory;
        this.kafkaAdmin = kafkaAdmin;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory(@Autowired MessagingMessageConverter messagingMessageConverter) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler());
        factory.setMessageConverter(messagingMessageConverter);
        return factory;
    }

    @Bean
    @Primary
    public MessagingMessageConverter simpleMapperConverter() {
        MessagingMessageConverter messagingMessageConverter = new MessagingMessageConverter();
        DefaultKafkaHeaderMapper defaultKafkaHeaderMapper = new DefaultKafkaHeaderMapper();
        defaultKafkaHeaderMapper.addTrustedPackages(APP_PACKAGE_NAME);
        messagingMessageConverter.setHeaderMapper(defaultKafkaHeaderMapper);
        return messagingMessageConverter;
    }

}









