package org.example;

import com.google.gson.Gson;
import org.example.util.RandomSkierLiftRide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

    private String testUrl, endpointUrl;

    // how many request
    private final Integer batch = 10;

    // interval time for waiting the response
    private final Integer sl = 100;

    @BeforeEach
    public void beforeClass() {
        System.out.println("Start Testing");

        // should download and locate to the correct browser driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_mac_arm64/chromedriver");

        // should change to the destination domain
        //        testUrl = "http://localhost:8080/coen6731/public/index.html";
        //        endpointUrl = "http://localhost:8080/coen6731/skiers";

        testUrl = "http://155.248.237.143:8080/coen6731/public/index.html";
        endpointUrl = "http://155.248.237.143:8080/coen6731/skiers";
    }

    private void clearInput(WebElement... elements) {
        for (WebElement element : elements) {
            element.clear();
        }
    }


    private void clearOutput(WebDriver driver, WebElement output) {
        ((JavascriptExecutor) driver).executeScript("var ele=arguments[0]; ele.innerHTML = '';", output);
    }

    @Test
    public void postTest() {
        WebDriver driver1 = new ChromeDriver();
        try {
            System.out.println("Start Post Testing");
            driver1.get(testUrl);
            // driver1.manage().window().minimize();

            final WebElement url = driver1.findElement(By.id("url"));
            final WebElement resortID = driver1.findElement(By.id("resortID"));
            final WebElement seasonID = driver1.findElement(By.id("seasonID"));
            final WebElement dayID = driver1.findElement(By.id("dayID"));
            final WebElement skierID = driver1.findElement(By.id("skierID"));
            final WebElement body = driver1.findElement(By.id("body"));

            clearInput(url, resortID, seasonID, dayID, skierID, body);

            final WebElement sendPost = driver1.findElement(By.id("sendPost"));
            final WebElement output = driver1.findElement(By.id("output"));

            // test incorrect endpoint api
            url.sendKeys("nonsense url");
            RandomSkierLiftRide r = new RandomSkierLiftRide();
            resortID.sendKeys(r.getResortID().toString());
            seasonID.sendKeys(r.getSeasonID());
            dayID.sendKeys(r.getDayID());
            skierID.sendKeys(r.getSkierID().toString());
            body.sendKeys(new Gson().toJson(r.getBody()));
            sendPost.click();
            Assertions.assertTrue(output.getText().contains("Request failed with status code 404") && output.getText().contains("Not Found"));
            // test valid params
            for (int i = 0; i < batch; i++) {
                clearInput(url, resortID, seasonID, dayID, skierID, body);
                clearOutput(driver1, output);
                url.sendKeys(endpointUrl);
                r = new RandomSkierLiftRide();
                resortID.sendKeys(r.getResortID().toString());
                seasonID.sendKeys(r.getSeasonID());
                dayID.sendKeys(r.getDayID());
                skierID.sendKeys(r.getSkierID().toString());
                body.sendKeys(new Gson().toJson(r.getBody()));
                sendPost.click();
                while (output.getText().equals("")) {
                    sleep(sl);
                }
                Assertions.assertTrue(output.getText().contains("Response Code: 201"));
                final RandomSkierLiftRide rsObj = new Gson().fromJson(output.getText().replace("Response Code: 201", "").trim().replace("liftRide", "body"), RandomSkierLiftRide.class);

                Assertions.assertEquals(rsObj, r);
            }

            // test invalid params
            for (int i = 0; i < batch; i++) {
                clearInput(url, resortID, seasonID, dayID, skierID, body);
                clearOutput(driver1, output);
                url.sendKeys(endpointUrl);
                r = new RandomSkierLiftRide();
                resortID.sendKeys(r.getResortID().toString() + (i % 2 != 0 ? "char" : ""));
                seasonID.sendKeys(r.getSeasonID());
                dayID.sendKeys(r.getDayID());
                skierID.sendKeys(r.getSkierID().toString() + (i % 2 == 0 ? "char" : ""));
                body.sendKeys(new Gson().toJson(r.getBody()));
                sendPost.click();
                while (output.getText().equals("")) {
                    sleep(sl);
                }
                Assertions.assertTrue(output.getText().contains("Request failed with status code 400") && output.getText().contains("ResortID or SkierID should be an integer."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        } finally {
            driver1.quit();
        }
    }

    @Test
    public void getTest() {
        WebDriver driver2 = new ChromeDriver();
        try {
            System.out.println("Start Get Testing");
            driver2.get(testUrl);
            // driver2.manage().window().minimize();

            final WebElement url = driver2.findElement(By.id("urlGet"));
            final WebElement resortID = driver2.findElement(By.id("resortIDGet"));
            final WebElement seasonID = driver2.findElement(By.id("seasonIDGet"));
            final WebElement dayID = driver2.findElement(By.id("dayIDGet"));
            final WebElement skierID = driver2.findElement(By.id("skierIDGet"));

            clearInput(url, resortID, seasonID, dayID, skierID);

            final WebElement sendGet = driver2.findElement(By.id("sendGet"));
            final WebElement output = driver2.findElement(By.id("outputGet"));

            // test valid params
            for (int i = 0; i < batch; i++) {
                clearInput(url, resortID, seasonID, dayID, skierID);
                clearOutput(driver2, output);
                url.sendKeys(endpointUrl);
                RandomSkierLiftRide r = new RandomSkierLiftRide();
                resortID.sendKeys(r.getResortID().toString());
                seasonID.sendKeys(r.getSeasonID());
                dayID.sendKeys(r.getDayID());
                skierID.sendKeys(r.getSkierID().toString());
                sendGet.click();
                while (output.getText().equals("")) {
                    sleep(sl);
                }
                Assertions.assertTrue(output.getText().contains("Response Code: 200"));
            }

            // test invalid params
            for (int i = 0; i < batch; i++) {
                clearInput(url, resortID, seasonID, dayID, skierID);
                clearOutput(driver2, output);
                url.sendKeys(endpointUrl);
                RandomSkierLiftRide r = new RandomSkierLiftRide();
                resortID.sendKeys(r.getResortID().toString() + (i % 2 != 0 ? "char" : ""));
                seasonID.sendKeys(r.getSeasonID());
                dayID.sendKeys(r.getDayID());
                skierID.sendKeys(r.getSkierID().toString() + (i % 2 == 0 ? "char" : ""));
                sendGet.click();
                while (output.getText().equals("")) {
                    sleep(sl);
                }
                Assertions.assertTrue(output.getText().contains("Request failed with status code 400") && output.getText().contains("ResortID or SkierID should be an integer."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        } finally {
            driver2.quit();
        }

    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}