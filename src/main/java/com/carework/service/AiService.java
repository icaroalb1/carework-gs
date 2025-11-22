package com.carework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiService {

    private final ObjectProvider<ChatClient> chatClientProvider;
    private final ObjectProvider<ChatModel> chatModelProvider;
    private final com.carework.ai.LocalEnergyChatModel localFallback;

    public AiService(ObjectProvider<ChatClient> chatClientProvider, ObjectProvider<ChatModel> chatModelProvider) {
        this.chatClientProvider = chatClientProvider;
        this.chatModelProvider = chatModelProvider;
        this.localFallback = new com.carework.ai.LocalEnergyChatModel();
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
        
        // Tenta usar OpenAI primeiro
        if (chatClient != null && chatModel != null) {
            try {
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
            } catch (Exception e) {
                log.warn("⚠️  Erro ao chamar OpenAI: {} - Usando fallback local", e.getMessage());
                log.debug("Detalhes do erro:", e);
                // Continua para usar o fallback local
            }
        }
        
        // Fallback: usa LocalEnergyChatModel
        log.info("🔄 Usando LocalEnergyChatModel como fallback");
        try {
            String promptText = String.format(
                "MOOD:%.2f STRESS:%.2f SLEEP:%.2f",
                averageMood, averageStress, averageSleep
            );
            Prompt prompt = new Prompt(promptText);
            var chatResponse = localFallback.call(prompt);
            String response = chatResponse.getResult().getOutput().getContent();
            log.info("✅ Mensagem de fallback gerada ({} caracteres)", response.length());
            return response;
        } catch (Exception e) {
            log.error("❌ Erro ao gerar mensagem de fallback: {}", e.getMessage(), e);
            return fallbackMessage(averageMood, averageStress, averageSleep);
        }
    }

    private String fallbackMessage(double moodAverage, double stressAverage, double sleepAverage) {
        if (moodAverage <= 2 || stressAverage >= 4) {
            return "Equipe atenta: os indicadores emocionais do turno estão baixos. Reduza o ritmo da operação, valide os checklists e peça apoio se notar qualquer risco.";
        }
        if (moodAverage >= 4 && sleepAverage >= 3.5) {
            return "Excelente equilíbrio energético! Continue compartilhando aprendizados para manter a geração estável e segura.";
        }
        return "Os indicadores estão medianos. Ajuste pausas, hidrate-se e revise alarmes críticos antes da próxima manobra.";
    }
}
