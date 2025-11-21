package com.carework.controller.web;

import com.carework.config.CareworkProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebCheckinController {

    private final CareworkProperties properties;

    public WebCheckinController(CareworkProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/checkin")
    public String checkinPage(Model model) {
        model.addAttribute("userId", properties.getDemo().getUserId());
        return "checkin";
    }
}
