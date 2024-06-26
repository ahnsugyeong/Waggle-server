package com.example.waggle.global.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka.waggle")
public class KafkaProperties {

    private String topic;
    private String groupId;
    private String broker;

}
