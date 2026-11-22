package core;

import gist.LoginPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected static Properties config;

    @BeforeClass
    public void setUp() {
        String browser = System.getProperty("browser", ConfigManager.getProperty("browser"));
        String baseUrl = ConfigManager.getProperty("baseUrl");
        String sessionCookie = System.getProperty("sessionCookie", ConfigManager.getProperty("sessionCookie"));

        DriverManager.initDriver(browser);

        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(baseUrl);

        if (sessionCookie != null && !sessionCookie.trim().isEmpty()) {
            Cookie authCookie = new Cookie.Builder("user_session", sessionCookie.trim())
                    .domain("://github.com")
                    .path("/")
                    .isSecure(true)
                    .isHttpOnly(true)
                    .build();
            driver.manage().addCookie(authCookie);
            driver.navigate().refresh();
        } else {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(
                    ConfigManager.getProperty("username"),
                    ConfigManager.getProperty("password")
            );
        }
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
