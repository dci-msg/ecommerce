package org.dci.bookhaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @GetMapping("")
    public String viewAnalytics() {
        return "analytic/dashboard";
    }
}
