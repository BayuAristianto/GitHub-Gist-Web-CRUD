package gist;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class GistListPage extends BasePage {

    @FindBy(css = "#user-links button[aria-label='View profile and more']")
    private WebElement profileAvatarDropdown;

    @FindBy(css = "a[role='menuitem'][data-analytics-event*='your gists']")
    private WebElement yourGistsMenu;

    @FindBy(css = ".gist-snippet strong.css-truncate-target")
    private List<WebElement> gistFileTitles;

    public GistListPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToYourGists() {
        waitForElementClickable(profileAvatarDropdown);
        profileAvatarDropdown.click();

        waitForElementClickable(yourGistsMenu);
        yourGistsMenu.click();
    }

    public boolean isGistPresentInList(String fileName) {
        for (WebElement title : gistFileTitles) {
            if (title.getText().trim().equals(fileName)) {
                return true;
            }
        }
        return false;
    }
}
