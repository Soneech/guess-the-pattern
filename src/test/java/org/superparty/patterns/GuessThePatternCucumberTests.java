package org.superparty.patterns;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jdk.jshell.spi.ExecutionControl;
import lombok.val;
import org.glassfish.jaxb.core.v2.TODO;
import org.junit.Assert;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.superparty.patterns.controller.PatternController;
import org.superparty.patterns.model.Pattern;
import org.superparty.patterns.repository.PatternRepository;
import org.superparty.patterns.service.PatternService;
import org.superparty.patterns.util.Answer;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuessThePatternCucumberTests {
	private Model model = new ConcurrentModel();

	private PatternController patternController;


	private PatternService patternService;

	private PatternRepository patternRepository;


	private HashMap<Long, Pattern> patterns;

	@Before
	public void beforeScenario() {
		model = new ConcurrentModel();
		Pattern pattern1 = new Pattern(1L, "адаптер", "pattern1.png", 0);
		Pattern pattern2 = new Pattern(3L, "цепочка обязанностей", "pattern3.png", 2);
		Pattern pattern3 = new Pattern(8L, "прототип", "pattern8.png", 3);
		patterns = new HashMap<>();

		patternRepository = new PatternRepository() {
			@Override
			public void flush() {
			}

			@Override
			public <S extends Pattern> S saveAndFlush(S entity) {
				return null;
			}

			@Override
			public <S extends Pattern> List<S> saveAllAndFlush(Iterable<S> entities) {
				return null;
			}

			@Override
			public void deleteAllInBatch(Iterable<Pattern> entities) {

			}

			@Override
			public void deleteAllByIdInBatch(Iterable<Long> longs) {

			}

			@Override
			public void deleteAllInBatch() {

			}

			@Override
			public Pattern getOne(Long aLong) {
				return null;
			}

			@Override
			public Pattern getById(Long aLong) {
				return null;
			}

			@Override
			public Pattern getReferenceById(Long aLong) {
				return null;
			}

			@Override
			public <S extends Pattern> List<S> findAll(Example<S> example) {
				return null;
			}

			@Override
			public <S extends Pattern> List<S> findAll(Example<S> example, Sort sort) {
				return null;
			}

			@Override
			public <S extends Pattern> List<S> saveAll(Iterable<S> entities) {
				return null;
			}

			@Override
			public List<Pattern> findAll() {
				return patterns.values().stream().toList();
			}

			@Override
			public List<Pattern> findAllById(Iterable<Long> longs) {
				return null;
			}

			@Override
			public <S extends Pattern> S save(S entity) {
				return null;
			}

			@Override
			public Optional<Pattern> findById(Long aLong) {
				return Optional.ofNullable(patterns.get(aLong));
			}

			@Override
			public boolean existsById(Long aLong) {
				return false;
			}

			@Override
			public long count() {
				return 0;
			}

			@Override
			public void deleteById(Long aLong) {

			}

			@Override
			public void delete(Pattern entity) {

			}

			@Override
			public void deleteAllById(Iterable<? extends Long> longs) {

			}

			@Override
			public void deleteAll(Iterable<? extends Pattern> entities) {

			}

			@Override
			public void deleteAll() {

			}

			@Override
			public List<Pattern> findAll(Sort sort) {
				return null;
			}

			@Override
			public Page<Pattern> findAll(Pageable pageable) {
				return null;
			}

			@Override
			public <S extends Pattern> Optional<S> findOne(Example<S> example) {
				return Optional.empty();
			}

			@Override
			public <S extends Pattern> Page<S> findAll(Example<S> example, Pageable pageable) {
				return null;
			}

			@Override
			public <S extends Pattern> long count(Example<S> example) {
				return 0;
			}

			@Override
			public <S extends Pattern> boolean exists(Example<S> example) {
				return false;
			}

			@Override
			public <S extends Pattern, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
				return null;
			}
		};
		List.of(pattern1,pattern2,pattern3).forEach(pattern -> {
			patterns.put(pattern.getId(), pattern);
		});

		patternService = new PatternService(patternRepository);
		patternController = new PatternController(patternService);
	}


	// pattern-service-find-by-id.feature + pattern-service-answer-checking.feature
	@Given("был передан id паттерна {string}")
	public void patternsIdWasGiven(String string) {
		model.addAttribute("patternId", Integer.valueOf(string));
	}

	@When("из базы данных был получен паттерн с id {string}")
	public void getPatternFromDb(String id) {
		val pattern = patternService.findById(Long.valueOf((Integer) model.getAttribute("patternId")));
		model.addAttribute("patternFromDb", pattern);
	}

	@Then("полученный паттерн является {string}")
	public void receivedPatternIsNullOrNotNull(String isNullOrNotNull) {
		val isNull = isNullOrNotNull.equals("null");
		val isReceivedObjectNull = model.getAttribute("patternFromDb") == null;
		Assert.assertEquals(isNull, isReceivedObjectNull);
	}


	// pattern-view.feature
	@When("клиент переходит на {string}")
	public void clientGoesToPatternsPage(String page) {
		model.addAttribute("patternsPage", patternController.allPatternsPage(model));
	}

	@Then("вернуть страницу {string} с ссылками на задания")
	public void returnPageWithPatternsList(String page) {
		val patterns = patternService.findAll();
		Assert.assertEquals(patterns, model.getAttribute("patterns"));
		Assert.assertEquals(patternService.getCountOfGuesses(patterns), model.getAttribute("guesses"));
		Assert.assertEquals("patterns", model.getAttribute("patternsPage"));
	}

	@When("клиент переходит на patterns {string}")
	public void clientGoesToConcretePattern(String string) {
		model.addAttribute("concretePattern", patternController.patternPage(Long.valueOf(string), model));
	}

	@Then("вернуть страницу {string} с данными паттерна, найденного по id {string}")
	public void returnPageWithDataAboutPattern(String page, String id) {
		System.out.println("HIHI2" + model);
		Assert.assertEquals(page, model.getAttribute("concretePattern"));
		Assert.assertEquals(patternService.findById(Long.valueOf(id)), model.getAttribute("pattern"));
		Assert.assertNotNull(model.getAttribute("answer"));
	}


	// pattern-service-answer-checking.feature, Given в начале
	@When("дан ответ {string}")
	public void answerWasGiven(String answerValue) {
		val answer = new Answer();
		answer.setValue(answerValue);
		model.addAttribute("answerFlag", patternService.checkTheAnswer(answer,
				Long.valueOf((Integer) model.getAttribute("patternId"))));
	}

	@Then("вернуть флаг {string}")
	public void returnFlag(String stringFlag) {
		val flag = Boolean.valueOf(stringFlag);
		Assert.assertEquals(flag, model.getAttribute("answerFlag"));
	}
}
