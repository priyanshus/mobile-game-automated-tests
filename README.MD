# Mobile Game Automation

## How does it work?

Basically the core of this project is pixel based comparison of images. The high-level flow of project is like below:

1. Launches the mobile games using the Appium
2. Captures screenshot of game screens using the Appium
3. Performs a sub image search from captured screenshot using the ImageMagic tool
4. The search will return the coordinates of matching pixels
5. Uses the coordinates to interact with elements using the Appium

The sub image will be treated as a locater and found coordinates will be used to interact with the elements.

## What about verifications?

Verifications are also done on the basis of image comparison. To verify the presence of any element on game screen, you have to use that element as a sub image. Perform the image search and verify if sub images exist in captured screenshot.

## How to setup the project?

To setup this project you have to follow the below steps:

1. Make sure you have java installed on your machine.

2. Install the ImageMagic on your machine. Make sure you can run ImageMagic commands from command line. One can follow the installation steps from here: http://www.imagemagick.org/script/install-source.php

3. You also need to install Appium. Make sure you are able to start the appium server on your system. You can find the installation steps and other information regarding Appium here: http://appium.io/getting-started.html

3. Clone this project using command `git clone https://github.com/priyanshus/mobile-game-automated-tests.git`.

4. Open the cloned project in any IDE like Eclipse.

## How to run the tests?

1. If you are done with the setup steps then you can plug in real android devices to your pc (you can use emulators too).Make sure the devices/emulators are listed by firing the command `adb devices`.

2. When you fire command `adb devices`, you will get an UUID corresponding to each device, note down that id. The IDs will be used while starting the appium server.

3. If you are thinking to run the tests across different device in parallel. Then you have to start same number of appium servers as your devices count. Make sure you start the appium servers on different ports as below.

   `appium -p [port] -cp [port] -bp [port] -U "[UUID]"`

  For example, if you have two devices to run the tests. Start two appium servers as:
   
  `appium -p 5234 -cp 5234 -bp 5345 -U "4239882fls20"`

  `appium -p 6234 -cp 6234 -bp 6345 -U "123434fsfsa"`

4. Configure the testng.xml file as per the device uuid and screen size. You also need to make sure that the server port used in testng.xml is same as you started above. 
   
   I distributed the tests on the basis of screen size, you can do the same.

5. Make sure you use correct sub images which are specified under `/searchimages/[screen-size]`. The folder insider the `/searchimages` should match with the device name specified in testng.xml.
  
  **Note**: If you are running the tests for first time, you may need to change the subimages specified under `/searchimages`     folder. It is like writing locators in web driver tests.

6. Also if you don't see any folder in root directory of project with name `screenshots`, then create a new one. All these things should be automated. :)

7. Now you can run the tests using testng by right clicking on testng.xml.


### Demo

<a href="http://www.youtube.com/watch?feature=player_embedded&v=SGpjOOUNTZU
" target="_blank"><img src="http://img.youtube.com/vi/SGpjOOUNTZU/0.jpg" 
alt="Demo video" width="640" height="380" border="10" /></a>

