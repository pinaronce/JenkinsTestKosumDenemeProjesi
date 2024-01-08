package step;

import base.BaseTest;
import com.thoughtworks.gauge.Step;
import model.*;
import org.openqa.selenium.*;
import io.restassured.response.Response;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class BaseSteps extends BaseTest {



    public static Map<String, Map<String, By>> pageElementLocators = new HashMap<>();


    public BaseSteps() {
        pageElementLocators.put("home", HomePageElements.LOCATORS);
    }

    @Step("Navigate to the URL <url>")
    public void navigateToURL(String url) {
        try {
            logger.info("Navigating to URL: " + url);
            driver.get(url);
            logger.info("Navigation to URL successful: " + url);
        } catch (Exception e) {
            logger.error("Navigation to URL failed: " + url, e);
            throw e;
        }
    }
}