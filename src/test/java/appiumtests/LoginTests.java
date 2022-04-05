package appiumtests;

import appiumsupport.AppiumStarter;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.StartPage;

import java.io.IOException;

public class LoginTests {

    private static String PASSWORD = "LetsGetTheStarShipStarted2023!";
    private static final String USERNAME = "elonmusk";
    private static final String EMAIL = "elonmusk@gmail.com";
    private static final String PHONE = "9998887766";
    private static final String INVALID_PHONE = "0000000000";
    private static final String WRONG_PASSWORD = "qwerty";
    private static final String WRONG_USERNAME = "elonamusk";
    private static final String WRONG_EMAIL = "elonamusk@gmail.com";
    private static AppiumDriver driver;
    private StartPage startPage;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeSuite
    public void prepareEnvironment() throws IOException, InterruptedException {
        AppiumStarter.instance.startServer();
        AppiumStarter.instance.initDriver();
        driver = AppiumStarter.instance.driver;
        startPage = new StartPage(driver);
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        System.out.println(driver.getSessionId());
    }

    @AfterMethod
    public void cleanApp(){
        driver.resetApp();
    }

    @Test(priority=1, enabled = true)
    public void loginWithInvalidPhone() throws InterruptedException {
        startPage.loginButtonClick();
        loginPage.loginWithPhoneNumber(INVALID_PHONE, PASSWORD);
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("please enter a valid phone number"));
    }

    @Test(priority=2, enabled = true)
    public void loginWithWrongName() throws InterruptedException {
        startPage.loginButtonClick();
        loginPage.loginWithUserNameOrEmail(WRONG_USERNAME, PASSWORD);
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("we cannot find a matching username"));
    }

    @Test(priority=3, enabled = true)
    public void loginWithWrongPassword() throws InterruptedException {
        startPage.loginButtonClick();
        loginPage.loginWithUserNameOrEmail(USERNAME, WRONG_PASSWORD);
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("find matching credentials"));
    }

    @Test(priority=4, enabled = true)
    public void successLoginViaUserName() throws InterruptedException {
        startPage.loginButtonClick();
        loginPage.loginWithUserNameOrEmail(USERNAME, PASSWORD);
        Assert.assertTrue(mainPage.checkCameraCaptureButtonIsEnabled());
    }

    @AfterClass
    public void stopDriver() throws IOException, InterruptedException {
        AppiumStarter.instance.stop();
    }
}
