package pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    public AppiumDriver driver;

    public MainPage(AppiumDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @FindBy(id = "com.snapchat.android:id/turn_on_button")
    private MobileElement turnOnCameraButton;

    @FindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
    private MobileElement permissionAllowForegroundOnlyButton;

    @FindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
    private MobileElement permissionAllowButton;

    @FindBy(xpath = "//android.widget.TextView[@text = 'Allow Snapchat to record audio?']")
    private MobileElement accessToAudioMessage;

    @FindBy(xpath = "//android.widget.TextView[@text = 'Snapchat Would Like to Access Your Contacts']")
    private MobileElement accessToContactsMessage;

    @FindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
    private MobileElement doNotAllowButton;

    @FindBy(id = "com.snapchat.android:id/camera_capture_button")
    private MobileElement cameraCaptureButton;

    public void enableAccess(){
        waitForVisibility(turnOnCameraButton);
        turnOnCameraButton.click();
        waitForVisibility(permissionAllowForegroundOnlyButton);
        permissionAllowForegroundOnlyButton.click();
        waitForVisibility(permissionAllowButton);
        permissionAllowButton.click();
        waitForVisibility(accessToAudioMessage);
        permissionAllowForegroundOnlyButton.click();
        waitForVisibility(accessToContactsMessage);
        doNotAllowButton.click();
    }

    public boolean checkCameraCaptureButtonIsEnabled(){
        return cameraCaptureButton.isEnabled();
    }

    public void waitForVisibility(MobileElement element){
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.elementToBeClickable(element));
    }
}
