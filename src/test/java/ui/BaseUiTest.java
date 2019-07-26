package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseUiTest {

    public  WebDriver driver;
    public static final String CHROME_DRIVER = "chrome";
    public static final String FIREFOX_DRIVER = "firefox";
    Page page;

    public  void setup(String selectYourDriver) {

        switch (selectYourDriver) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
                 driver = new ChromeDriver();
                  break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
                 driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + selectYourDriver);
        }
         page = new Page(driver);
    }
}
