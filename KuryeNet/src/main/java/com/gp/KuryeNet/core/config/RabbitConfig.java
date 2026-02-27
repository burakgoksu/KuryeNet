package com.gp.KuryeNet.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gp.KuryeNet.core.config.properties.MessagingProperties;

@Configuration
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true")
public class RabbitConfig {

    @Bean
    public TopicExchange orderExchange(MessagingProperties properties) {
        return new TopicExchange(properties.getOrderExchange());
    }

    @Bean
    public Queue orderQueue(MessagingProperties properties) {
        return new Queue(properties.getOrderQueue(), true);
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange orderExchange, MessagingProperties properties) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(properties.getOrderRoutingKey());
    }

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter rabbitMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(rabbitMessageConverter);
        return template;
    }
}
