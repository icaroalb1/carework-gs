package com.carework.controller.web;

import com.carework.config.CareworkProperties;
import com.carework.dto.WeeklyReportDTO;
import com.carework.service.InternalApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebReportController {

    private final InternalApiClient apiClient;
    private final CareworkProperties properties;

    @GetMapping("/report")
    public String report(Model model) {
        WeeklyReportDTO report = apiClient.get("/reports/weekly/" + properties.getDemo().getUserId(), WeeklyReportDTO.class);
        model.addAttribute("report", report);
        return "report";
    }
}
