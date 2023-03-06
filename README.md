## Important !!!

**The project and the tested web server are for course demonstration purposes.**

**You could try light testing with the selenium code.** 

**But please don't perform heavy batch size.** 

**It might crash the server :smile:.**

This is a selenium showcase for course coen448/6761 (Software Testing).
The demo shows how to use selenium to test a remote webpage.

## How To

You can first visit http://155.248.237.143:8080/coen6731/public/index.html.

And if it crashed, please let me know: [jun.huang@concordia.ca](jun.huang@concordia.ca).

You should focus on how to set up the first selenium test maven project.
Material and Tutorial are listed below:

1. [Selenium WebDriver with Java and TestNG. Tutorial designed for complete beginners in Selenium testing and automation](https://concordia.udemy.com/course/selenium-for-beginners/learn/lecture/14351810#overview)
2. [Maven: Using TestNG](https://maven.apache.org/surefire/maven-surefire-plugin/examples/testng.html)
3. [Selenium](https://www.selenium.dev/)

### ENV Requirement

1. JDK >= 1.8
2. Maven >= 3.6

For Eclipse User:

**You will need to download the TestNG Plugin.**
https://www.browserstack.com/guide/how-to-install-testng-in-eclipse

### Configure and Download the WebDriver

The code for selenium is located at `org.example.SeleniumTest`.
Please edit the following settings from line 19~34 to play the test:

``` java 
// how many request
private final Integer batch = 10;

// interval time for waiting the response
private final Integer sl = 100;

@BeforeClass
public void beforeClass() {
    System.out.println("Start Testing");

    // should download and locate to the correct browser driver
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_mac_arm64/chromedriver");

    // should change to the destination domain
    testUrl = "http://localhost:8080/coen6731/public/index.html";
    endpointUrl = "http://localhost:8080/coen6731/skiers";
}
```

For using the demo, you could change the domain to: 

``` java 
testUrl = "http://155.248.237.143:8080/coen6731/public/index.html";
endpointUrl = "http://155.248.237.143:8080/coen6731/skiers";
```

The web driver can be downloaded at: https://www.selenium.dev/documentation/webdriver/getting_started/install_drivers/#quick-reference

### Run Test with Maven

``` bash 
mvn clean test
```
