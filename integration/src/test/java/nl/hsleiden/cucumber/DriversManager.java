package nl.hsleiden.cucumber;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriversManager {

    private WebDriver[] drivers;

    public DriversManager() throws MalformedURLException {
        String seleniumAddress = Configuration.getString("selenium.address");
        drivers = new WebDriver[2];
        if (StringUtils.isNotBlank(seleniumAddress)) {
            DesiredCapabilities chromeCapability = DesiredCapabilities.chrome();
            DesiredCapabilities firefoxCapability = DesiredCapabilities.firefox();
            drivers[0] = new RemoteWebDriver(new URL(seleniumAddress), firefoxCapability);
            drivers[1] = new RemoteWebDriver(new URL(seleniumAddress), chromeCapability);
        } else {
            drivers[0] = new FirefoxDriver();
            drivers[1] = new ChromeDriver();
        }
    }

    public WebDriver[] getDrivers() {
        return drivers.clone();
    }

    public void tearDown() {
        for (WebDriver driver : drivers) {
            if (driver != null) {
                driver.quit();
            }
        }
    }

}
