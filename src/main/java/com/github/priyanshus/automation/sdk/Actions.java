package com.github.priyanshus.automation.sdk;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;

import com.github.priyanshus.automation.imageprocess.ImageMagic;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

/**
 * Actions class
 * Contains methods to interact with web elements
 */
public class Actions {
    AppiumDriver driver;

    private ImageMagic search;

    protected Dimension size;

    protected String destDir;

    protected DateFormat dateFormat;

    private int SHORT_SLEEP = 2;

    private int LONG_SLEEP = 10;

    private String screenSize;

    private TouchAction action;

    public Actions(AppiumDriver driver, String screenSize) {
        this.driver = driver;
        this.screenSize = screenSize;
        search = new ImageMagic(screenSize);
        action = new TouchAction(driver);
    }

    /**
     * Captures screenshot
     * @param timeOut
     * @return
     * @throws Exception
     */
    public String captureScreenshot() throws Exception {
        System.out.println(screenSize);
        sleep(SHORT_SLEEP);

        destDir = "screenshots" + "/" + screenSize;
        // Capture screenshot.
        File srcFile = driver.getScreenshotAs(OutputType.FILE);
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        new File(destDir).mkdirs();

        String destFile = dateFormat.format(new Date()) + "-" + screenSize + ".png";

        try {
            FileUtils.copyFile(srcFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        log("Captured screenshot");
        return destFile;
    }

    /**
     * Captures screenshot
     * @param timeOut
     * @return
     * @throws Exception
     */
    public String captureAndRotateScreenshot() throws Exception {
        String screenshot = captureScreenshot();
        search.rotateImage(screenshot);

        return screenshot;
    }

    /**
     * Finds coordinates for provided image
     * @param subImageName subImage Name
     * @return Point Coordinates of matching pixels
     * @throws Exception
     */
    private Point findCoordinatesFor(String subImageName) throws Exception {
        String screenShotName = captureScreenshot();
        search.rotateImage(screenShotName);
        Point p = search.findRegion(screenShotName, subImageName);
        log("Found coordinates for :" + subImageName + " as " + p.x + "," + p.y);
        return p;
    }

    /**
     * Taps on specified image on the current screen
     * @param imageName Image Name
     * @throws Exception
     */
    public void tapOnImage(String imageName) throws Exception {
        Point p = findCoordinatesFor(imageName);
        log("Tapping on : " + imageName);
        driver.tap(1, p.x, p.y, 1);
        sleep(SHORT_SLEEP);
    }

    /**
     * Taps on specified image and returns its coordinates
     * @param imageName Image Name
     * @return Point coordinates of matching pixels
     * @throws Exception
     */
    public Point tapOnImageAndGetCoordinates(String imageName) throws Exception {
        Point p = findCoordinatesFor(imageName);
        log("Tapping on :" + imageName);

        driver.tap(1, p.x, p.y, 1);
        sleep(SHORT_SLEEP);
        return p;
    }

    /**
     * Get coordinates of matching image
     * @param imageName Image Name
     * @return Point coordinates of matching pixels
     * @throws Exception
     */
    public Point getCoordinatesFor(String imageName) throws Exception {
        Point p = findCoordinatesFor(imageName);
        sleep(SHORT_SLEEP);
        return p;
    }

    /**
     * Get coordinates for provides image from base image
     * @param baseImage Base Image
     * @param imageName Sub Image
     * @return Point
     * @throws Exception
     */
    public Point getCoordinatesFor(String baseImage, String imageName) throws Exception {
        Point p = search.findRegion(baseImage, imageName);
        sleep(SHORT_SLEEP);
        return p;
    }

    /**
     * Taps on coordinate
     * @param p Point instance
     * @throws Exception
     */
    public void tapOnCoordinates(Point p) throws Exception {
        driver.tap(1, p.x, p.y, 1);
        sleep(SHORT_SLEEP);
    }

    /**
     * Taps on middle of screen
     * @throws Exception
     */
    public void tapMiddle() throws Exception {
        Dimension size = driver.manage().window().getSize();
        Point middle = new Point(size.getWidth() / 2, size.getHeight() / 2);

        driver.tap(1, (int) middle.x, (int) middle.y, 1);
        sleep(SHORT_SLEEP);
    }

    /**
     * Taps on given x y coordinates
     * @param x
     * @param y
     * @throws Exception
     */
    public void tapAtCoordinates(int x, int y) throws Exception {
        driver.tap(1, x, y, 1);
        sleep(SHORT_SLEEP);
    }

    /**
     * Long tap on given coordinates
     * @param p coordinates
     * @param duration duration to tap on
     */
    public void keepTappedOnScreen(Point p, int duration) {
        log("Long tapped");
        action.longPress(p.x, p.y, duration).release().perform();
    }

    /**
     * Wait for specified time
     * @param seconds seconds
     * @throws Exception
     */
    public void sleep(int seconds) throws Exception {
        Thread.sleep(seconds * 1000);
    }

    /**
     * Wait for specified time
     * @throws Exception
     */
    public void lognSleep() throws Exception {
        Thread.sleep(LONG_SLEEP * 1000);
    }

    /**
     * Logs the message
     * @param message
     */
    public void log(String message) {
        System.out.println(message);
    }
}
