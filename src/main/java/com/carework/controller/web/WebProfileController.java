package com.carework.controller.web;

import com.carework.config.CareworkProperties;
import com.carework.dto.UserDTO;
import com.carework.service.InternalApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebProfileController {

    private final InternalApiClient apiClient;
    private final CareworkProperties properties;

    @GetMapping("/profile")
    public String profile(Model model) {
        UserDTO user = apiClient.get("/users/" + properties.getDemo().getUserId(), UserDTO.class);
        model.addAttribute("user", user);
        return "profile";
    }
}
