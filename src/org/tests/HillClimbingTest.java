package org.tests;

import org.automation.sdk.TestBase;
import org.openqa.selenium.Point;
import org.testng.annotations.Test;

/**
 * Test class
 */
public class HillClimbingTest extends TestBase {
	String pauseScreen, exitScreen;
	Point exitButton, accelerateButton, pauseButton, okButton;

	/**
	 * Test to verify buttons on exit screen
	 */
	@Test
	public void testPauseFeature() throws Exception {
		actions.lognSleep();

		Point nextButton = actions.tapOnImageAndGetCoordinates("next.png");

		actions.tapOnCoordinates(nextButton);
		actions.tapOnCoordinates(nextButton);
		actions.tapOnCoordinates(nextButton);

		accelerateButton = actions.getCoordinatesFor("accelerate.png");

		actions.keepTappedOnScreen(accelerateButton, 5000);
		pauseButton = actions.tapOnImageAndGetCoordinates("pause.png");
		
		pauseScreen = actions.captureAndRotateScreenshot();
		
		exitButton = verify.hasElement(pauseScreen , "exit.png");
		
		actions.tapOnCoordinates(exitButton);
		exitScreen = actions.captureScreenshot();
		
		okButton = verify.hasElement(exitScreen , "ok.png");
		
		actions.tapOnCoordinates(okButton);
	}
}
