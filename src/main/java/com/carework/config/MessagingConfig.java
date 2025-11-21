package com.carework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class MessagingConfig {

    @Bean
    public Executor checkinExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("checkin-queue-");
        executor.initialize();
        return executor;
    }

    @Bean
    public MessageChannel checkinEventsChannel(Executor checkinExecutor) {
        return new ExecutorChannel(checkinExecutor);
    }
}
