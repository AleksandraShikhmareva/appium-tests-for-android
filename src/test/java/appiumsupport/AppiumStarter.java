package appiumsupport;

import dataProvider.ConfigFileReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class AppiumStarter {
    public static AppiumStarter instance = new AppiumStarter();
    private static Logger logger = LoggerFactory.getLogger(AppiumStarter.class);
    private AppiumDriverLocalService service;
    public AppiumDriver driver;

    private AppiumStarter() {
    }

    public synchronized void startServer() throws InterruptedException {
        if (service != null) {
            return; //already started
        }
        try {
            logger.info("Starting appium server");
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .usingPort(4723)
                    .withLogFile(new File("./appium_server.log"))
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "debug");
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
            logger.info("Appium server has been started successfully");
            registerStopServerHook();

        } catch (AppiumServerHasNotBeenStartedLocallyException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void registerStopServerHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            service.stop();
        }, "appium_stop"));
    }

    public synchronized void initDriver() throws MalformedURLException {
        if (driver != null) {
            return;
        }
        DesiredCapabilities desiredCapabilities = setDesiredCapabilities();
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(url, desiredCapabilities);
    }

    public synchronized void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private DesiredCapabilities setDesiredCapabilities() {
        ConfigFileReader configFileReader = new ConfigFileReader();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //setting device capabilities
        capabilities.setCapability("deviceName", configFileReader.getProperty("deviceName"));
        capabilities.setCapability("udid", configFileReader.getProperty("udid"));
        capabilities.setCapability("platformName", configFileReader.getProperty("platformName"));
        capabilities.setCapability("platformVersion", configFileReader.getProperty("platformVersion"));

        //Application capabilities
        capabilities.setCapability("appPackage", configFileReader.getProperty("appPackage"));
        capabilities.setCapability("appActivity", configFileReader.getProperty("appActivity"));
        capabilities.setCapability("no-reset","false");

        return capabilities;
    }
}
