package nl.hsleiden.cucumber;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Glue {

    private DriversManager driversManager;
    private String page;
    private String baseUrl;
    private String queryString = "";

    @Before
    public void setup() throws MalformedURLException {
        driversManager = new DriversManager();
    }

    @When("^open the page \"([^\"]*)\" on site$")
    public void open_the_context_path(String page) throws Throwable {
        this.page = page;
        this.baseUrl = Configuration.getSiteBaseUrl();
    }

    @When("^query string \"([^\"]*)\"$")
    public void queryString(String queryString) throws Throwable {
        this.queryString = queryString;
    }

    @Then("^the header should read \"([^\"]*)\"$")
    public void the_header_should_read(String text) throws Throwable {
        String url = baseUrl + page + "?" + queryString;
        System.out.println(url);
        for (WebDriver driver : driversManager.getDrivers()) {
            driver.get(url);
            Assert.assertEquals(text, driver.findElement(By.cssSelector("h2")).getText());
        }

    }

    @After
    public void tearDown() {
        driversManager.tearDown();
    }

}
