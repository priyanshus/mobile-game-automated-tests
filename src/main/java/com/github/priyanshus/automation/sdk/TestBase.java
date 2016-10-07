package com.github.priyanshus.automation.sdk;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.github.priyanshus.automation.imageprocess.ImageMagic;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/**
 * The common life cycle of test class
 * Important: Make sure you run the tests from testng.xml file
 * Also you need to provide specified params from xml file.
 */
public class TestBase {
    protected AppiumDriver driver;

    protected ImageMagic imageSearch;

    protected Actions actions;

    protected Verify verify;

    @Parameters({"deviceIdentity", "url", "deviceSize"})
    @BeforeClass
    public void setUp(String deviceIdentity, String url, String deviceSize) throws IOException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("newCommandTimeout", 60 * 5);
        capabilities.setCapability("BROWSER_NAME", "Android");
        capabilities.setCapability("VERSION", "5.1");
        capabilities.setCapability("deviceName", deviceIdentity);
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.fingersoft.hillclimb");
        capabilities.setCapability("appActivity",
            "com.fingersoft.game.MainActivity");

        driver = new AndroidDriver(new URL(url), capabilities);

        imageSearch = new ImageMagic(deviceSize);
        actions = new Actions(driver, deviceSize);
        verify = new Verify(deviceSize);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
