package nl.hsleiden.cucumber;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Glue {

	private WebDriver[] drivers;
	private String host;
	private String contextPath;
	private String queryString;

	@Before
	public void setup() {
		drivers = new WebDriver[2];
		drivers[0] = new FirefoxDriver();
		drivers[1] = new ChromeDriver();
	}

	@Given("^the host name URL \"([^\"]*)\"$")
	public void the_host_name_URL(String url) throws Throwable {
		this.host = url;
	}

	@When("^open the context path \"([^\"]*)\"$")
	public void open_the_context_path(String contextPath) throws Throwable {
		this.contextPath = contextPath;
	}

	@When("^query string \"([^\"]*)\"$")
	public void queryString(String queryString) throws Throwable {
		this.queryString = queryString;
	}

	@Then("^the header should read \"([^\"]*)\"$")
	public void the_header_should_read(String text) throws Throwable {
		String url = host + contextPath + "?" + queryString;
		for (WebDriver driver : drivers) {
			driver.get(url);
			Assert.assertEquals(text, driver.findElement(By.cssSelector("h2"))
					.getText());
		}

	}

	@After
	public void tearDown() throws Exception {
		for (WebDriver driver : drivers) {
			driver.quit();
		}
	}

}
