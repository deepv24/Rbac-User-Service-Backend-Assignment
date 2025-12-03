package com.example.rbac_user_management_service.Kafka;

import com.example.rbac_user_management_service.Service.KafkaEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.contains;

@ExtendWith(MockitoExtension.class)
class KafkaEventProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaEventProducer kafkaEventProducer;

    @Test
    void testPublishEvent() {
        kafkaEventProducer.publishEvent("user-events", Map.of("msg", "test"));

        verify(kafkaTemplate, times(1))
                .send(eq("user-events"), contains("test"));
    }
}
