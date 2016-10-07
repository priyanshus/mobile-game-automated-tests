package com.github.priyanshus.automation.imageprocess;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.openqa.selenium.Point;

import com.github.priyanshus.automation.sdk.Constants;

/**
 * Utility class contains convenient methods to process image file
 */
public class ImageMagic {
    boolean rotate = true;

    public String screenSize;

    public ImageMagic(String screenSize) {
        this.screenSize = screenSize;
    }

    /**
     * Performs search for sub image
     * @param baseImage
     *            Main image
     * @param subImage
     *            sub image
     * @return Coordinates in encrypted form
     * @throws Exception
     */
    private String subImagesearch(String baseImage, String subImage)
        throws Exception {
        int exitVal = -1;

        System.out.println(screenSize);

        String screenImageFullPath = Constants.SCREENSHOT_BASEPATH + "/"
            + screenSize + "/" + baseImage;
        String searchImageFullPath = Constants.SEARCH_BASEPATH + "/"
            + screenSize + "/" + subImage;
        String diffPath = Constants.DIFF_BASEPATH + "/" + screenSize + "/"
            + "diff-%d.png";

        final String[] command = {"compare", "-subimage-search", "-metric",
            "rmse", screenImageFullPath, searchImageFullPath, diffPath};

        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            exitVal = p.waitFor();
            BufferedReader stdError = new BufferedReader(new InputStreamReader(
                p.getErrorStream()));
            String line = "";
            while ((line = stdError.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Subimage search finished with exit value : "
            + exitVal);

        if (exitVal != 0) {
            System.exit(1);
        }

        return output.toString();
    }

    /**
     * Rotate image to counter clockwise by 90 degree
     * @param image
     *            Image Name which needs to be rotated
     * @throws Exception
     */
    public void rotateImage(String image) throws Exception {
        int exitVal = -1;

        if (!(screenSize == "big" || screenSize.equalsIgnoreCase("big"))) {
            String screenImageFullPath = Constants.SCREENSHOT_BASEPATH + "/"
                + screenSize + "/" + image;

            final String[] command = {"convert", "-rotate", "-90",
                screenImageFullPath, screenImageFullPath};

            StringBuffer output = new StringBuffer();

            Process p;
            try {
                p = Runtime.getRuntime().exec(command);
                exitVal = p.waitFor();
                BufferedReader stdError = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
                String line = "";
                while ((line = stdError.readLine()) != null) {
                    output.append(line + "\n");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Image rotate exit value : " + exitVal);

            if (exitVal != 0) {
                System.exit(1);
            }
        }
    }

    /**
     * Find coordinates from encrypted value
     * @param baseImage
     * @param subImage
     * @return
     * @throws Exception
     */
    public Point findRegion(String baseImage, String subImage) throws Exception {
        Point p = null;
        String output = this.subImagesearch(baseImage, subImage);

        if (output != null) {
            String[] temp = output.replaceAll("\\s+", "").split("@")[1]
                .split(",");
            p = new Point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
        }

        return p;
    }
}
