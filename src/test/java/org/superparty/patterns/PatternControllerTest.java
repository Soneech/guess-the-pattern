package org.superparty.patterns;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.superparty.patterns.controller.PatternController;
import org.superparty.patterns.model.Pattern;
import org.superparty.patterns.service.PatternService;
import org.superparty.patterns.util.Answer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatternController.class)
public class PatternControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PatternService patternService;

    @Test
    public void testPatchRequestGuessed() throws Exception {
        Pattern pattern = new Pattern(3L, "цепочка обязанностей", "pattern3.png", 0);
        Answer answer = new Answer("цепочка обязанностей");

        when(patternService.findById(3L)).thenReturn(pattern);
        when(patternService.checkTheAnswer(answer, 3L)).thenReturn(true);

        mockMvc.perform(patch("/patterns/{id}", 3L)
                        .param("value", answer.getValue()).flashAttr("answer", answer))
                .andExpect(status().isOk())
                .andExpect(view().name("pattern_page"))
                .andExpect(model().attribute("pattern", pattern))
                .andExpect(model().attributeExists("answer"))
                .andExpect(model().attribute("message", "Правильно!"));
    }

    @Test
    public void testPatchRequestNotGuessed() throws Exception {
        Pattern pattern = new Pattern(3L, "цепочка обязанностей", "pattern3.png", 0);
        Answer answer = new Answer("адаптер");

        when(patternService.findById(3L)).thenReturn(pattern);
        when(patternService.checkTheAnswer(answer, 3L)).thenReturn(false);

        mockMvc.perform(patch("/patterns/{id}", 3L)
                        .param("value", answer.getValue()).flashAttr("answer", answer))
                .andExpect(status().isOk())
                .andExpect(view().name("pattern_page"))
                .andExpect(model().attribute("pattern", pattern))
                .andExpect(model().attributeExists("answer"))
                .andExpect(model().attribute("message", "Неверный ответ, попробуйте ещё :("));
    }
}
