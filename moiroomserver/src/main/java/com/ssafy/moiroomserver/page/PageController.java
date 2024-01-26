package com.ssafy.moiroomserver.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/moiroom/privacy")
    public String showPrivacyPage() {
        return "privacy";
    }
}