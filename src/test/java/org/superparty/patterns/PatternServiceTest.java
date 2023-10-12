package org.superparty.patterns;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.superparty.patterns.model.Pattern;
import org.superparty.patterns.service.PatternService;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatternServiceTest {
    @Mock
    private PatternService patternService;
    private List<Pattern> patterns;

    @BeforeEach
    public void setUp() {
        Pattern pattern1 = new Pattern(1L, "адаптер", "pattern1.png", 0);
        Pattern pattern2 = new Pattern(3L, "цепочка обязанностей", "pattern3.png", 2);
        Pattern pattern3 = new Pattern(8L, "прототип", "pattern8.png", 3);

        patterns = new ArrayList<>(List.of(pattern1, pattern2, pattern3));
    }

    @Test
    public void testFindAll() {
        when(patternService.findAll()).thenReturn(patterns);
        Assertions.assertEquals("адаптер", patternService.findAll().get(0).getName());
        Assertions.assertEquals("цепочка обязанностей", patternService.findAll().get(1).getName());
        Assertions.assertEquals("прототип", patternService.findAll().get(2).getName());
    }

    @Test
    public void testIncreaseCountOfGuessesForPattern() {
        Pattern pattern = patterns.get(0);

        Pattern newPattern = new Pattern(pattern.getId(), pattern.getName(),
                pattern.getFileName(), pattern.getGuesses() + 1);

        when(patternService.increaseCountOfGuessesForPattern(pattern)).thenReturn(newPattern);
        Pattern result = patternService.increaseCountOfGuessesForPattern(pattern);
        Assertions.assertEquals(result.getGuesses(), pattern.getGuesses() + 1);
    }
}
