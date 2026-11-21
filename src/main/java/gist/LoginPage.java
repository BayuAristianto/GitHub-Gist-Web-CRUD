package gist;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//a[@class='Header-link no-underline tmp-mr-3']")
    private WebElement signInLink;

    @FindBy(id = "login_field")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//input[@name='commit']")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        waitForElementClickable(signInLink);
        signInLink.click();

        waitForElementToBeVisible(usernameField);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        signInButton.click();
    }
}
