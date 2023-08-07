package org.example;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.util.RandomSkierLiftRide;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class SeleniumCucumberTest {

    // interval time for waiting the response
    private final Integer sl = 100;
    private static WebDriver driver;
    private static List<RandomSkierLiftRide> randomSkierLiftRides;
    private static List<String> responseTexts;
    private static Boolean parameterValidity = null;

    @BeforeAll
    public static void beforeAll() {
        // Runs before all scenarios
        System.out.println("Start Testing");

        // config the chrome driver if you are using chrome older that version 115
        // should download and locate to the correct browser driver
        // and uncomment this

        // System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_mac_arm64/Google Chrome for Testing.app");

        // update: 28 Mar: https://stackoverflow.com/questions/75680149/unable-to-establish-websocket-connection
        ChromeOptions chromeOptions = new ChromeOptions().addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterAll
    public static void afterAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void clearInput(WebElement... elements) {
        for (WebElement element : elements) {
            element.clear();
        }
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearOutput(WebDriver driver, WebElement output) {
        ((JavascriptExecutor) driver).executeScript("var ele=arguments[0]; ele.innerHTML = '';", output);
    }

    @When("I open the chrome browser with {string}")
    public void openUrl(String url) {
        driver.get(url);
    }

    @Then("I should see the webpage render {string}")
    public void iShouldSeeTheWebpageRender(String arg0) {
        try {
            // establish, open connection with URL
            HttpURLConnection cn = (HttpURLConnection) new URL(driver.getCurrentUrl()).openConnection();
            // set HEADER request
            cn.setRequestMethod("HEAD");
            // connection initiate
            cn.connect();
            //get response code
            int res = cn.getResponseCode();
            //Display
            // System.out.println("Http response code: " + res);
            Assertions.assertEquals(res, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I generate {int} random parameters for {int} requests")
    public void iGenerateRandomParametersForRequests(int parameterValidity, int numberOfRequest) {
        randomSkierLiftRides = new ArrayList<>();
        for (int i = 0; i < numberOfRequest; i++) {
            randomSkierLiftRides.add(new RandomSkierLiftRide());
        }
        SeleniumCucumberTest.parameterValidity = parameterValidity == 1;
    }

    @Then("I construct requests with those parameters and send them to api {string}")
    public void iConstructRequestsWithThoseParametersAndSendThemToApi(String apiEndpoint) {
        responseTexts = new ArrayList<>();
        final WebElement url = driver.findElement(By.id("urlGet"));
        final WebElement resortID = driver.findElement(By.id("resortIDGet"));
        final WebElement seasonID = driver.findElement(By.id("seasonIDGet"));
        final WebElement dayID = driver.findElement(By.id("dayIDGet"));
        final WebElement skierID = driver.findElement(By.id("skierIDGet"));

        final WebElement sendGet = driver.findElement(By.id("sendGet"));
        final WebElement output = driver.findElement(By.id("outputGet"));

        for (int i = 0; i < randomSkierLiftRides.size(); i++) {
            final RandomSkierLiftRide r = randomSkierLiftRides.get(i);
            clearInput(url, resortID, seasonID, dayID, skierID);
            clearOutput(driver, output);
            url.sendKeys(apiEndpoint);
            seasonID.sendKeys(r.getSeasonID());
            dayID.sendKeys(r.getDayID());

            if (!parameterValidity) {
                resortID.sendKeys(r.getResortID().toString() + (i % 2 != 0 ? "char" : ""));
                skierID.sendKeys(r.getSkierID().toString() + (i % 2 == 0 ? "char" : ""));
            } else {
                resortID.sendKeys(r.getResortID().toString());
                skierID.sendKeys(r.getSkierID().toString());
            }

            sendGet.click();
            while (output.getText().equals("")) {
                sleep(sl);
            }
            responseTexts.add(output.getText());
        }
    }

    @Then("I should receive {int} responses contain message {string}")
    public void iShouldReceiveResponsesWithStatusCode(int numberOfResponse, String responseText) {
        int count = 0;
        for (String actualText : responseTexts) {
            if (actualText.contains(responseText)) {
                count += 1;
            }
        }
        Assertions.assertEquals(numberOfResponse, count);
    }


}