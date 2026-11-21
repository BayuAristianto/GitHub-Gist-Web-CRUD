package tests;

import core.BaseTest;
import core.ConfigManager;
import gist.CreateGistPage;
import gist.GistDetailPage;
import gist.GistListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GistCrudTest extends BaseTest {

    private final String fileName = "automation_test_" + System.currentTimeMillis() + ".txt";
    private final String initialDesc = "Initial Gist Description via Selenium";
    private final String updatedDesc = "Updated Gist Description via Selenium";
    private String createdGistUrl;

    @Test(priority = 1, description = "TC01 - Create Public Gist")
    public void testCreatePublicGist() {
        driver.get(ConfigManager.getProperty("baseUrl"));

        CreateGistPage createGistPage = new CreateGistPage(driver);
        createGistPage.createPublicGist(initialDesc, fileName, "Hello World from Automated Test!");

        GistDetailPage detailPage = new GistDetailPage(driver);
        Assert.assertEquals(detailPage.getDisplayedFileName(), fileName);
        Assert.assertEquals(detailPage.getDisplayedDescription(), initialDesc);

        createdGistUrl = detailPage.getCurrentUrl();
    }

    @Test(priority = 2, dependsOnMethods = "testCreatePublicGist", description = "TC02 - See List of Gists")
    public void testSeeListOfGists() {
        GistListPage listPage = new GistListPage(driver);
        listPage.navigateToYourGists();

        Assert.assertTrue(listPage.isGistPresentInList(fileName), "Gist yang baru dibuat tidak muncul di daftar!");
    }

    @Test(priority = 3, dependsOnMethods = "testCreatePublicGist", description = "TC03 - Edit Existing Gist")
    public void testEditExistingGist() {
        driver.get(createdGistUrl);

        GistDetailPage detailPage = new GistDetailPage(driver);
        detailPage.editGistDescription(updatedDesc);

        Assert.assertEquals(detailPage.getDisplayedDescription(), updatedDesc);
    }

    @Test(priority = 4, dependsOnMethods = "testEditExistingGist", description = "TC04 - Delete Existing Gist")
    public void testDeleteExistingGist() {
        driver.get(createdGistUrl);

        GistDetailPage detailPage = new GistDetailPage(driver);
        detailPage.deleteGist();

        GistListPage listPage = new GistListPage(driver);
        Assert.assertFalse(listPage.isGistPresentInList(fileName), "Gist masih ada setelah dihapus!");
    }
}
