package org.superparty.patterns;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(features = "src/test/resources/features", tags = "@withdrawal",
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class CucumberTestConf { }
