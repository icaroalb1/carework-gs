package com.carework.controller.web;

import com.carework.dto.TipDTO;
import com.carework.service.InternalApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebTipsController {

    private final InternalApiClient apiClient;

    @GetMapping("/tips")
    public String tips(Model model) {
        List<TipDTO> tips = apiClient.get("/tips", new ParameterizedTypeReference<>() {});
        model.addAttribute("tips", tips);
        return "tips";
    }

    @GetMapping("/tips/new")
    public String newTip(Model model) {
        return tips(model);
    }
}
