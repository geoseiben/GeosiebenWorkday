package com.geosieben.gsbworkday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {
    @Bean
    public  Queue messageQueue() {
        return new Queue("messageQueue", true);
    }
    
}
