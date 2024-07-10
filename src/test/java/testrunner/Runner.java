package testrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/Features" }, glue = {
		"stepdefinitions" }, monochrome = true, plugin = { "pretty", "html:target/cucumber-reports/report.html",
				"json:target/cucumber-reports/cucumber.json" })
public class Runner {

}
