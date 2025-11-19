package org.example.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Junit4Tests {

    private WebDriver driver;
    private String baseUrl = "http://dev.testinium.com";
    private boolean acceptNextAlert = true;

    final String hubCloudDev = "http://hub.cloud.dev.testinium.com/wd/hub";

    @Before
    public void setup() throws MalformedURLException {
        DesiredCapabilities dc = DesiredCapabilities.firefox();
        final String hubLocal = "http://localhost:4444/wd/hub";
        final String hubCloudProd = "http://hub.testinium.io/wd/hub";

        final String hubDocker = "http://host.docker.internal:4444/wd/hub";
        final String key = System.getProperty("key");
        final String testID = System.getProperty("testID");

        if (key != null && !key.isEmpty()) {
            // TESTINIUM ÜZERİNDE KOŞMA SEÇENEĞİ
            if (testID != null && !testID.isEmpty()) {
                System.out.println("MAKSAHIN: hubCloudProd");
                System.out.println("MAKSAHIN: AppToken: " + System.getProperty("appToken"));
                // cloud plugin
                dc = DesiredCapabilities.chrome();
                dc.setPlatform(Platform.WINDOWS);
                dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                dc.setCapability("testinium:key", key);
                dc.setCapability("testinium:testID", testID);
                dc.setCapability("testinium:takesScreenshot", true);
                dc.setCapability("testinium:recordsVideo", false);
                driver = new RemoteWebDriver(new URL(hubDocker), dc);
            } else {
                // hub local
                System.out.println("MAKSAHIN: hubLocal");

                // ----------------------------------------------------------------------------------------------------
                String lastRetry = System.getProperty("lastRetry","false");
                String retryCount = System.getProperty("retryCount","-");
                System.out.println("MAKSAHIN lastRetry " + lastRetry);
                System.out.println("MAKSAHIN retryCount " + retryCount);
                if(Boolean.parseBoolean(lastRetry)) {
                    System.out.println("MAKSAHIN Test son kez tekrarlı çalışıyor! retryWithScreenshots true verdik.");
                    dc.setCapability("retryWithScreenshots", "true");
                }
                // ----------------------------------------------------------------------------------------------------
                dc.setCapability("key", key);
                driver = new RemoteWebDriver(new URL(hubDocker), dc);
            }
        } else {
            // test local'den testinium'suz başlatıldığında
            System.out.println("MAKSAHIN: testiniumsuz lokalden");
            System.setProperty("webdriver.chrome.driver", "/Users/ebubekir/Dev/data/chromedriver");
            driver = new ChromeDriver();
        }
    }

    @Test
    public void goToSozcuWithJunit4Http() throws Exception {
        driver.get("http://www.sozcu.com.tr");
        Thread.sleep(5000);
        driver.getPageSource();
    }

    @Test
    public void goToEveWithJunit4Http() throws Exception {
        driver.get("https://www.eveshop.com.tr/");
        Thread.sleep(5000);
        driver.getPageSource();
    }
    @Test
    public void goToAmazonWithJunit4Http() throws Exception {
        driver.get("https://www.amazon.com.tr");
        Thread.sleep(5000);
        driver.getPageSource();
    }
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
