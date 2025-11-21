package com.carework.controller.api;

import com.carework.config.CareworkProperties;
import com.carework.dto.LoginDTO;
import com.carework.dto.LoginResponseDTO;
import com.carework.dto.UserDTO;
import com.carework.model.User;
import com.carework.service.DtoMapper;
import com.carework.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CareworkProperties properties;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO);
        UserDTO userDTO = DtoMapper.toUserDTO(user);
        String token = properties.getSecurity().getApiKey();
        return ResponseEntity.ok(new LoginResponseDTO(token, "Bem-vindo, " + userDTO.name() + "!"));
    }
}
