package gist;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class CreateGistPage extends BasePage {

    @FindBy(name = "gist[description]")
    private WebElement descriptionInput;

    @FindBy(name = "gist[contents][][name]")
    private WebElement fileNameInput;

    @FindBy(css = ".CodeMirror-code")
    private WebElement codeEditorArea;

    @FindBy(xpath = "//summary[@aria-label='Select a type of Gist']")
    private WebElement gistTypeDropdown;

    @FindBy(xpath = "//span[normalize-space()='Public gists are visible to everyone.']")
    private WebElement publicGistOption;

    @FindBy(xpath = "//button[normalize-space()='Create public gist']")
    private WebElement createPublicGistButton;

    @FindBy(css = ".flash-error, .js-flash-alert")
    private WebElement flashErrorMessage;

    public CreateGistPage(WebDriver driver) {
        super(driver);
    }

    public void createPublicGist(String description, String fileName, String content) {
        waitForElementToBeVisible(descriptionInput);
        descriptionInput.sendKeys(description);
        fileNameInput.sendKeys(fileName);

        waitForElementClickable(codeEditorArea);
        codeEditorArea.click();
        new Actions(driver).sendKeys(content).perform();

        scrollToElement(gistTypeDropdown);
        waitForElementClickable(gistTypeDropdown);
        gistTypeDropdown.click();

        waitForElementClickable(publicGistOption);
        publicGistOption.click();

        waitForElementClickable(createPublicGistButton);
        createPublicGistButton.click();
    }

    public String getFlashErrorMessage() {
        waitForElementToBeVisible(flashErrorMessage);
        return flashErrorMessage.getText().trim();
    }
}
