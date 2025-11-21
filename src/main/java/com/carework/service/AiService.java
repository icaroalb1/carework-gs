package com.carework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiService {

    private final ObjectProvider<ChatClient> chatClientProvider;
    private final ObjectProvider<ChatModel> chatModelProvider;

    public AiService(ObjectProvider<ChatClient> chatClientProvider, ObjectProvider<ChatModel> chatModelProvider) {
        this.chatClientProvider = chatClientProvider;
        this.chatModelProvider = chatModelProvider;
    }

    public String generateMessage(double averageMood, double averageStress, double averageSleep) {
        ChatClient chatClient = chatClientProvider.getIfAvailable();
        ChatModel chatModel = chatModelProvider.getIfAvailable();
        
        // Log para identificar qual modelo está sendo usado
        if (chatModel != null) {
            String modelClass = chatModel.getClass().getSimpleName();
            log.info("🤖 Usando ChatModel: {} para gerar mensagem de IA", modelClass);
            if (modelClass.contains("OpenAI") || modelClass.contains("OpenAi")) {
                log.info("✅ OpenAI está sendo usada! Modelo: {}", modelClass);
            }
        } else {
            log.warn("⚠️  ChatModel não encontrado, usando fallback local");
        }
        
        if (chatClient != null) {
            String prompt = String.format(
                "Você é um assistente especializado em bem-estar e segurança operacional para equipes de trabalho em turnos. " +
                "Analise os seguintes indicadores de um turno de trabalho e gere uma mensagem curta, clara e acolhedora (máximo 120 palavras) " +
                "com recomendações práticas baseadas nos dados:\n\n" +
                "Humor médio: %.2f (escala de 1-5, onde 1=muito baixo e 5=muito alto)\n" +
                "Estresse médio: %.2f (escala de 1-5, onde 1=muito baixo e 5=muito alto)\n" +
                "Qualidade do sono médio: %.2f (escala de 1-5, onde 1=muito baixo e 5=muito alto)\n\n" +
                "Gere uma mensagem em português brasileiro que seja: " +
                "1) Empática e acolhedora, 2) Prática com recomendações específicas, 3) Focada em segurança operacional quando necessário, " +
                "4) Motivadora quando os indicadores estão bons.",
                averageMood, averageStress, averageSleep
            );
            log.debug("📝 Enviando prompt para IA: mood={}, stress={}, sleep={}", averageMood, averageStress, averageSleep);
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
            log.info("✅ Resposta da IA recebida ({} caracteres)", response.length());
            return response;
        }
        log.warn("⚠️  ChatClient não disponível, usando mensagem de fallback");
        return fallbackMessage(averageMood);
    }

    private String fallbackMessage(double moodAverage) {
        if (moodAverage <= 2) {
            return "Parece que os turnos ficaram pesados. Faça uma pausa e conte com o time para manter a operação segura.";
        }
        if (moodAverage >= 4) {
            return "Ótimo desempenho operacional! Continue cuidando da rotina para manter a energia lá em cima.";
        }
        return "Obrigado pelo check-in. Pequenos ajustes já ajudam a manter a performance energética equilibrada.";
    }
}
