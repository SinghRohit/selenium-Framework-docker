package VendorPortal;

import TestListener.TestListener;
import Utils.Constants;
import Utils.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

@Listeners({
        TestListener.class
})

public abstract class AbstractTest {

    protected WebDriver driver;

    @BeforeSuite
    public void initialize() {
        Config.initialize();
    }

    @BeforeTest
    public void setDriver(ITestContext iTestContext) throws MalformedURLException {
        this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ? getRemoteDriver() : getLocalDriver();
        iTestContext.setAttribute(Constants.DRIVER, this.driver);
    }

    private WebDriver getLocalDriver() {
        System.setProperty("wdm.proxy", "http://genproxy.amdocs.com:8080");
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities = new ChromeOptions();
        if (Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))) {
            capabilities = new FirefoxOptions();
        }

        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat, hubHost);

        return new RemoteWebDriver(new URL(url), capabilities);//"http://localhost:4444/wd/hub"
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }


}