package ui;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UiHelpers {

    WebDriver driver;
    WebDriverWait wait;


    public UiHelpers(WebDriver driver) {
         this.driver = driver;
         this.wait = new WebDriverWait(this.driver, 10);
    }


    public WebElement waitElement(WebElement element){
        return  wait.until(ExpectedConditions.elementToBeClickable(element));
    }


}

