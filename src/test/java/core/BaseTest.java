package core;

import gist.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        DriverManager.initDriver(ConfigManager.getProperty("browser"));

        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfigManager.getProperty("baseUrl"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                ConfigManager.getProperty("username"),
                ConfigManager.getProperty("password")
        );
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected static Properties config;

}
