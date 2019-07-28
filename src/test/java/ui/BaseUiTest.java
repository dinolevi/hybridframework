package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Class is used as base test class for UI tests. Here we will set our drivers
 * and create all PageObject instances
 *
 * @author: dino
 */
public class BaseUiTest {

    public static final String CHROME_DRIVER = "chrome";
    public static final String FIREFOX_DRIVER = "firefox";
    public WebDriver driver;
    public String url = "https://qa-sandbox.apps.htec.rs/";
    public LoginPage loginPage;
    public String operatorUsername = "dinorac87@gmail.com";
    public String operatorPassword = "Test1234!";
    public UiHelpers helper;

    /**
     * Before method will setup drivers and will perform login of operator
     *
     * @author: dino
     */
    @BeforeMethod public void testPreparation() {
        //Choose driver and create page instances
        setDriver(FIREFOX_DRIVER);
        helper = new UiHelpers(driver);
        loginPage = new LoginPage(driver);

        //Success login
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(2, SECONDS);
        assertThat("We reached homePage", driver.getCurrentUrl(), is(url));
        assertThat("Title is", driver.getTitle(), is("Sandbox"));
        assertThat("Header is ", loginPage.getHomePageHeader(),
                is("QA Sandbox"));
        loginPage.getLoginButton().isDisplayed();
        loginPage.loginFunction(operatorUsername, operatorPassword);
    }

    /**
     * Method will set desired drivers when driver name is inserted as parameter.
     * Note: available driver names are already stored as constants
     *
     * @param selectYourDriver driver name use constants for preseted values
     * @author: dino
     */
    public void setDriver(String selectYourDriver) {

        switch (selectYourDriver) {
        case "chrome":
            System.setProperty("webdriver.chrome.driver",
                    "src/test/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver();
            break;
        case "firefox":
            System.setProperty("webdriver.gecko.driver",
                    "src/test/resources/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
            break;
        default:
            throw new IllegalStateException(
                    "Unexpected value: " + selectYourDriver);
        }

    }

    /**
     * Method will be close driver after test is finished
     */
    @AfterMethod public void finishProcess() {
        driver.quit();
    }
}
