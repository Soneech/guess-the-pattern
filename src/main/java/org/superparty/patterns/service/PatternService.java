package org.superparty.patterns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.superparty.patterns.model.Pattern;
import org.superparty.patterns.repository.PatternRepository;

import java.util.List;

@Service
public class PatternService {
    private final PatternRepository patternRepository;

    @Autowired
    public PatternService(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    public Pattern findById(Long id) {
        return patternRepository.findById(id).orElse(null);
    }

    public int getCountOfGuesses(List<Pattern> patterns) {
        return patterns.stream().mapToInt(Pattern::getGuesses).sum();
    }

    public List<Pattern> findAll() {
        return patternRepository.findAll();
    }

}
