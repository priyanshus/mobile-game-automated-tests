package com.github.priyanshus.automation.sdk;

import org.openqa.selenium.Point;
import org.testng.Assert;

import com.github.priyanshus.automation.imageprocess.ImageMagic;

/**
 * Assertion methods
 */
public class Verify {
    ImageMagic subImageSearch;

    public Verify(String screenSize) {
        subImageSearch = new ImageMagic(screenSize);
    }

    /**
     * Verifies provided sub image is visible in base image.
     * Also returns coordinates of matching pixels
     * @param baseImage Base image
     * @param subImage Sub Image
     * @return coordinates
     * @throws Exception
     */
    public Point hasElement(String baseImage, String subImage) throws Exception {
        Point p = subImageSearch.findRegion(baseImage, subImage);

        if (p.x != 0 && p.y != 0) {
            Assert.assertTrue(true, "Found " + subImage);
            return p;
        } else {
            Assert.assertTrue(false, "Not Found " + subImage);
            return null;
        }
    }
}
