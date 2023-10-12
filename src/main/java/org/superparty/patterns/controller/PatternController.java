package org.superparty.patterns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @PatchMapping("/{id}")
    public String guessPattern(@PathVariable("id") Long id, @ModelAttribute("answer") Answer answer, Model model) {
        boolean didGuess = patternService.checkTheAnswer(answer, id);

        Pattern pattern = patternService.findById(id);
        model.addAttribute("pattern", pattern);
        model.addAttribute("answer", new Answer());
        if (didGuess) {
            model.addAttribute("message", "Правильно!");
            patternService.increaseCountOfGuessesForPattern(pattern);
        }
        else
            model.addAttribute("message", "Неверный ответ, попробуйте ещё :(");
        return "pattern_page";
    }
}
