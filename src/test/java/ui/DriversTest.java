package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class DriversTest extends BaseUiTest{

    @Test
    public void chrome() throws InterruptedException {



        driver.get("http://www.google.com/xhtml");
       // driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
       // Thread.sleep(5000);  // Let the user actually see something!

       driver.getTitle().contentEquals("Google");
        //page.search().sendKeys("YYYYYYYY");
        Thread.sleep(5000);  // Let the user actually see something!
        driver.quit();

    }

    @Test
    public void firefox() throws InterruptedException {

//        System.setProperty("webdriver.gecko.driver",
//                "src/test/resources/drivers/geckodriver.exe");
//       WebDriver driver = new FirefoxDriver();
        Thread.sleep(3000);

        driver.get("https://www.nba.com");

        System.out.println("Selenium Webdriver Script in Firefox browser using Gecko Driver | Software Testing Material");

        driver.close();

    }
}
