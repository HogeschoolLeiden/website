package nl.hsleiden.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "html:target/cucumber-report",
		"json:target/cucumber.json" }, tags = { "~@wip", "~@disabled" }, glue = { "nl.hsleiden.cucumber" })
public class ITCucumber {

}
