package org.superparty.patterns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.superparty.patterns.service.PatternService;

@Controller
public class PatternController {
    private final PatternService patternService;

    @Autowired
    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }
}
