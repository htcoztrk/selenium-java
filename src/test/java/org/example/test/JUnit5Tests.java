package org.example.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class JUnit5Tests {

    private static WebDriver driver;
    private String baseUrl = "http://dev.testinium.com";
    private boolean acceptNextAlert = true;

    final String hubCloudDev = "http://hub.cloud.dev.testinium.com/wd/hub";

    @BeforeAll
    public static void setup() throws MalformedURLException {
        final String hubDocker = "http://172.25.1.25:4444/wd/hub";
        DesiredCapabilities dc = DesiredCapabilities.firefox();
        final String hubLocal = "http://localhost:4444/wd/hub";
        final String hubCloudProd = "http://hub.testinium.io/wd/hub";

        final String key = System.getProperty("key");
        final String testID = System.getProperty("testID");

        if (key != null && !key.isEmpty()) {
            // TESTINIUM ÜZERİNDE KOŞMA SEÇENEĞİ
            if (testID != null && !testID.isEmpty()) {
                System.out.println("MAKSAHIN: hubCloudProd");
                System.out.println("MAKSAHIN: AppToken: " + System.getProperty("appToken"));
                // cloud plugin
                dc = DesiredCapabilities.chrome();
                dc.setPlatform(Platform.MAC);
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
    public void goToSozcuWithJunit5Http() throws Exception {
        driver.get("http://www.sozcu.com.tr");
        Thread.sleep(5000);
        driver.getPageSource();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
