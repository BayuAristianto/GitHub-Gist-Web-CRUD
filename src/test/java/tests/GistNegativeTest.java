package tests;

import core.BaseTest;
import core.ConfigManager;
import gist.CreateGistPage;
import gist.GistDetailPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GistNegativeTest extends BaseTest {

    private final String tempFileName = "negative_test_" + System.currentTimeMillis() + ".txt";
    private String validGistUrl;

    @Test(priority = 1, description = "TC05 - Create Gist with empty content")
    public void testCreateGistWithEmptyContent() {
        driver.get(ConfigManager.getProperty("baseUrl"));

        CreateGistPage createGistPage = new CreateGistPage(driver);
        createGistPage.createPublicGist("Negative Test Empty Content", tempFileName, "");

        String actualError = createGistPage.getFlashErrorMessage();
        Assert.assertTrue(actualError.contains("Contents can't be empty"),
                "Pesan error tidak sesuai! Aktual: " + actualError);
    }

    @Test(priority = 2, description = "TC06 - Access non-existent Gist URL returns 404")
    public void testAccessNonExistentGist() {
        driver.get("https://gist.github.com/sesipotret/0000000000000000");

        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Page not found") || driver.getPageSource().contains("404"),
                "Halaman 404 tidak muncul! Title saat ini: " + pageTitle);
    }

    @Test(priority = 3, description = "TC07 - Unauthorized access to edit another user's Gist")
    public void testEditOtherUsersGistUnauthorized() {
        driver.get("https://gist.github.com/HugsLibRecordKeeper/1af68beefaeb865eb155458bfbcfe38b/edit");
        String pageTitle = driver.getTitle();
        Assert.assertTrue(pageTitle.contains("Page not found") || driver.getPageSource().contains("404"),
                "Seharusnya ditolak (404) saat mengakses halaman edit milik user lain! Title: " + pageTitle);
    }

    @Test(priority = 4, description = "TC08 - Cancel delete Gist confirmation")
    public void testCancelDeleteGist() {
        driver.get(ConfigManager.getProperty("baseUrl"));
        CreateGistPage createGistPage = new CreateGistPage(driver);
        createGistPage.createPublicGist("Gist for Negative Delete", tempFileName, "Temporary content for delete test");

        GistDetailPage detailPage = new GistDetailPage(driver);
        Assert.assertEquals(detailPage.getDisplayedFileName(), tempFileName);
        validGistUrl = detailPage.getCurrentUrl();

        detailPage.cancelDeleteGist();
        Assert.assertEquals(detailPage.getCurrentUrl(), validGistUrl);
        detailPage.deleteGist();
    }


}
