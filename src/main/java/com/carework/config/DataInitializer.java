package com.carework.config;

import com.carework.model.Tip;
import com.carework.model.User;
import com.carework.repository.TipRepository;
import com.carework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CareworkProperties properties;

    @Bean
    CommandLineRunner loadData(UserRepository userRepository,
                               TipRepository tipRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            UUID userId = properties.getDemo().getUserId();
            userRepository.findById(userId).ifPresentOrElse(
                    user -> {},
                    () -> {
                        User user = new User();
                        user.setId(userId);
                        user.setName("Marina Costa");
                        user.setEmail("marina@carework.energy");
                        user.setPassword(passwordEncoder.encode("123456"));
                        user.setTeam("Operação Termelétrica Norte");
                        userRepository.save(user);
                    }
            );

            if (tipRepository.count() == 0) {
                Tip tip1 = new Tip();
                tip1.setTitle("Checklist rápido na troca de turno");
                tip1.setDescription("Reserve 3 minutos para validar indicadores críticos antes de liberar a próxima equipe.");
                Tip tip2 = new Tip();
                tip2.setTitle("Hidratação em áreas de calor");
                tip2.setDescription("Carregue sempre uma garrafa térmica na sala de controle das caldeiras.");
                Tip tip3 = new Tip();
                tip3.setTitle("Micro pausa energética");
                tip3.setDescription("A cada 90 minutos, olhe para fora do monitor e alongue os ombros por 60 segundos.");
                tipRepository.saveAll(List.of(tip1, tip2, tip3));
            }
        };
    }
}
