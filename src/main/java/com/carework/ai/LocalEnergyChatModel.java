package com.carework.ai;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalEnergyChatModel implements ChatModel {

    private static final Pattern METRIC_PATTERN = Pattern.compile("(MOOD|STRESS|SLEEP):(-?\\d+(?:\\.\\d+)?)");

    @Override
    public ChatResponse call(Prompt prompt) {
        String contents = prompt.getContents() == null ? "" : prompt.getContents().toUpperCase(Locale.ROOT);
        double mood = extract(contents, "MOOD", 3.0);
        double stress = extract(contents, "STRESS", 3.0);
        double sleep = extract(contents, "SLEEP", 3.0);
        String message = craftMessage(mood, stress, sleep);
        Generation generation = new Generation(new AssistantMessage(message));
        return new ChatResponse(List.of(generation));
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return new LocalChatOptions();
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        return Flux.just(call(prompt));
    }

    private double extract(String contents, String key, double defaultValue) {
        Matcher matcher = METRIC_PATTERN.matcher(contents);
        double value = defaultValue;
        while (matcher.find()) {
            if (matcher.group(1).equals(key)) {
                try {
                    value = Double.parseDouble(matcher.group(2));
                } catch (NumberFormatException ignored) {
                    value = defaultValue;
                }
            }
        }
        return value;
    }

    private String craftMessage(double mood, double stress, double sleep) {
        if (mood <= 2 || stress >= 4) {
            return "Equipe atenta: os indicadores emocionais do turno estão baixos. Reduza o ritmo da operação, valide os checklists e peça apoio se notar qualquer risco.";
        }
        if (mood >= 4 && sleep >= 3.5) {
            return "Excelente equilíbrio energético! Continue compartilhando aprendizados para manter a geração estável e segura.";
        }
        return "Os indicadores estão medianos. Ajuste pausas, hidrate-se e revise alarmes críticos antes da próxima manobra.";
    }

    private static final class LocalChatOptions implements ChatOptions {

        @Override
        public String getModel() {
            return "carework-local-energy";
        }

        @Override
        public Float getFrequencyPenalty() {
            return null;
        }

        @Override
        public Integer getMaxTokens() {
            return 120;
        }

        @Override
        public Float getPresencePenalty() {
            return null;
        }

        @Override
        public List<String> getStopSequences() {
            return List.of();
        }

        @Override
        public Float getTemperature() {
            return 0.4f;
        }

        @Override
        public Integer getTopK() {
            return null;
        }

        @Override
        public Float getTopP() {
            return null;
        }

        @Override
        public ChatOptions copy() {
            return this;
        }
    }
}
