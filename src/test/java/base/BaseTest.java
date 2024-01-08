package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.AfterScenario;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    public static WebDriver driver;
    private String selectedBrowser = System.getProperty("browser", "chrome");

    protected Map<String, String> storedValues = new HashMap<>();

    @BeforeScenario
    public void initializeTestEnvironment() {
        setupWebDriver(selectedBrowser);
        maximizeBrowserWindow();
    }

    @AfterScenario
    public void cleanUpTestEnvironment() {
        quitWebDriver();
    }

    private void setupWebDriver(String browser) {
        if (browser == null) {
            throw new IllegalArgumentException("The browser system property must be set.");
        }
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = setupChromeDriver();
                break;
            case "firefox":
                driver = setupFirefoxDriver();
                break;
            case "edge":
                driver = setupEdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browser);
        }
        logger.info("{} WebDriver initialized.", browser);
    }

    private WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(createChromeOptions());
    }

    private WebDriver setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(createFirefoxOptions());
    }

    private WebDriver setupEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver(createEdgeOptions());
    }

    private void maximizeBrowserWindow() {
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }

    private void quitWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        return options;
    }

    private FirefoxOptions createFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--private");
        options.addPreference("dom.webnotifications.enabled", false);
        return options;
    }

    private EdgeOptions createEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--inprivate", "--disable-gpu");
        return options;
    }
}
