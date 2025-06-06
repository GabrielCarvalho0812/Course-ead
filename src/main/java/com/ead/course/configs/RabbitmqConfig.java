package com.ead.course.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    final CachingConnectionFactory connectionFactory;

    public RabbitmqConfig(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate Template = new RabbitTemplate(connectionFactory);
        Template.setMessageConverter(messageConverter());
        return Template;
    }


    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
