package com.carework.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CareworkAiConfig {

    /**
     * Configura o ChatClient usando o ChatModel da OpenAI (se configurado)
     * ou o LocalEnergyChatModel como fallback.
     * 
     * O Spring AI auto-configura o ChatModel da OpenAI quando a propriedade
     * spring.ai.openai.api-key estiver definida.
     */
    @Bean
    @Primary
    public ChatClient chatClient(@Autowired(required = false) ChatModel chatModel) {
        if (chatModel != null) {
            return ChatClient.builder(chatModel).build();
        }
        // Fallback para LocalEnergyChatModel se OpenAI não estiver configurado
        return ChatClient.builder(new com.carework.ai.LocalEnergyChatModel()).build();
    }
}
