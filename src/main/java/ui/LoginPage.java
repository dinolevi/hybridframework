package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * PageObject class for Login Page
 *
 * @author dino
 */
public class LoginPage {
    WebDriver driver;
    UiHelpers helper;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        helper = new UiHelpers(driver);
    }

    public WebElement getLoginButton() {
        return helper.waitToBeClickable(
                driver.findElement(By.xpath("//ul//a[@href='/login']")));
    }

    public String getHomePageHeader() {
        return driver
                .findElement(By.xpath("//h1[contains(text(),'QA Sandbox')]"))
                .getText();
    }

    public WebElement getEmailAdressInputField() {
        return helper.waitToBeClickable(driver.findElement(By.name("email")));
    }

    public void insertEmailAdress(String email) {
        getEmailAdressInputField().clear();
        getEmailAdressInputField().sendKeys(email);
    }

    public WebElement getPasswordInputField() {
        return helper
                .waitToBeClickable(driver.findElement(By.name("password")));
    }

    public void insertPassword(String password) {
        getPasswordInputField().clear();
        getPasswordInputField().sendKeys(password);
    }

    public WebElement getSubmitButton() {
        return helper.waitToBeClickable(
                driver.findElement(By.xpath("//button[@type='submit']")));
    }

    public void loginFunction(String email, String password) {
        getLoginButton().click();
        insertEmailAdress(email);
        insertPassword(password);
        getSubmitButton().click();
    }
}
