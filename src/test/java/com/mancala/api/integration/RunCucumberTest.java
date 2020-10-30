package com.mancala.api.integration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.mancala.api.integration"},
        plugin = {"pretty", "html:build/cucumber", "junit:build/report/junit/cucumber-report.xml"})
public class RunCucumberTest {

}