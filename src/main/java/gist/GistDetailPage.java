package gist;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GistDetailPage extends BasePage {

    @FindBy(css = "strong.css-truncate-target a")
    private WebElement gistFileNameHeader;

    @FindBy(xpath = "//span[@class='Button-label'][normalize-space()='Edit']")
    private WebElement editButton;

    @FindBy(name = "gist[description]")
    private WebElement editDescriptionInput;

    @FindBy(xpath = "//button[contains(text(),'Update public gist')]")
    private WebElement updateGistButton;

    @FindBy(xpath = "//span[@class='Button-label'][normalize-space()='Delete']")
    private WebElement deleteButton;

    @FindBy(css = "div[itemprop='about']")
    private WebElement gistDescriptionText;

    @FindBy(css = ".CodeMirror-code")
    private WebElement codeEditorArea;

    @FindBy(css = ".flash-error, .js-flash-alert")
    private WebElement flashErrorMessage;

    public GistDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getDisplayedFileName() {
        waitForElementToBeVisible(gistFileNameHeader);
        return gistFileNameHeader.getText().trim();
    }

    public String getDisplayedDescription() {
        waitForElementToBeVisible(gistDescriptionText);
        return gistDescriptionText.getText().trim();
    }

    public void editGistDescription(String newDescription) {
        waitForElementClickable(editButton);
        editButton.click();

        waitForElementToBeVisible(editDescriptionInput);
        editDescriptionInput.clear();
        editDescriptionInput.sendKeys(newDescription);

        waitForElementClickable(updateGistButton);
        updateGistButton.click();
    }

    public void deleteGist() {
        waitForElementClickable(deleteButton);
        deleteButton.click();
        driver.switchTo().alert().accept();
    }

    public void editGistWithEmptyContent() {
        waitForElementClickable(editButton);
        editButton.click();

        waitForElementClickable(codeEditorArea);
        codeEditorArea.click();

        new org.openqa.selenium.interactions.Actions(driver)
                .keyDown(org.openqa.selenium.Keys.CONTROL)
                .sendKeys("a")
                .keyUp(org.openqa.selenium.Keys.CONTROL)
                .sendKeys(org.openqa.selenium.Keys.BACK_SPACE)
                .perform();

        waitForElementClickable(updateGistButton);
        updateGistButton.click();
    }

    public String getFlashErrorMessage() {
        waitForElementToBeVisible(flashErrorMessage);
        return flashErrorMessage.getText().trim();
    }

    public void cancelDeleteGist() {
        waitForElementClickable(deleteButton);
        deleteButton.click();
        driver.switchTo().alert().dismiss();
    }
}
