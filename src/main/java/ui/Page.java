package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Page {
    WebDriver driver;
    UiHelpers helper;
    public Page(WebDriver driver) {
        this.driver = driver;
        helper = new UiHelpers(driver);
    }



    public WebElement search(){
        return  helper.waitElement(driver.findElement(By.name("q")));
    }
}
