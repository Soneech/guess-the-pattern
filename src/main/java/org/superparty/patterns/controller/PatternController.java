package org.superparty.patterns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.superparty.patterns.model.Pattern;
import org.superparty.patterns.service.PatternService;
import org.superparty.patterns.util.Answer;

import java.util.List;

@Controller
@RequestMapping("/patterns")
public class PatternController {
    private final PatternService patternService;

    @Autowired
    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }

    // test with cucumber
    @GetMapping
    public String allPatternsPage(Model model) {
        List<Pattern> patterns = patternService.findAll();
        model.addAttribute("patterns", patterns);
        model.addAttribute("guesses", patternService.getCountOfGuesses(patterns));
        return "patterns";
    }

    // test with cucumber
    @GetMapping("/{id}")
    public String patternPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pattern", patternService.findById(id));
        model.addAttribute("answer", new Answer());
        return "pattern_page";
    }
}
