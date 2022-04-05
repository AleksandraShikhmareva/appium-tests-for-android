package pageobjects;

import appiumsupport.AppiumStarter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private static Logger logger = LoggerFactory.getLogger(LoginPage.class);
    public AppiumDriver driver;

    public LoginPage(AppiumDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    @FindBy(id = "com.snapchat.android:id/use_phone_instead")
    private MobileElement usePhoneNumberLink;

    @FindBy(id = "com.snapchat.android:id/phone_country_code_field")
    private MobileElement countryCodeField;

    @FindBy(id = "com.snapchat.android:id/phone_form_field")
    private MobileElement phoneNumberInput;

    @FindBy(id = "com.snapchat.android:id/use_email_or_username_instead")
    private MobileElement useEmailOrUserNameLink;

    @FindBy(id = "com.snapchat.android:id/password_field")
    private MobileElement passwordField;

    @FindBy(id = "com.snapchat.android:id/username_or_email_field")
    private MobileElement userNameOrEmailField;

    @FindBy(id = "com.snapchat.android:id/forgot_password_button")
    private MobileElement forgotPasswordButton;

    @FindBy(id = "com.snapchat.android:id/button_text")
    private MobileElement logInButton;

    @FindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
    private MobileElement allowPermissionButton;

    @FindBy(id = "com.snapchat.android:id/login_error_message")
    private MobileElement loginErrorMessage;

    public void typeNumber(String number){
        phoneNumberInput.setValue(number);
    }

    public void typePassword(String password){
        passwordField.setValue(password);
    }

    public void typeCountryCode(String countryCode){
        countryCodeField.setValue(countryCode);
    }

    public void typeUserNameOrEmail(String userName){
        userNameOrEmailField.setValue(userName);
    }

    public void allowPermission(){
        if (isElementDisplayed(allowPermissionButton)){
            allowPermissionButton.click();
        }
    }

    public void waitForVisibility(MobileElement element){
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public String getErrorMessage(){
        return loginErrorMessage.getText();
    }

    public void loginWithPhoneNumber(String phone, String password){
        allowPermission();
        if (isElementDisplayed(usePhoneNumberLink)){
            usePhoneNumberLink.click();
            allowPermission();
        }
        waitForVisibility(phoneNumberInput);
        typeNumber(phone);
        if (!password.isEmpty()) {
            typePassword(password);
            logInButton.click();
            waitForVisibility(loginErrorMessage);
        }
        else {
            logInButton.click();
        }
    }

    public void loginWithUserNameOrEmail(String username, String password){
        allowPermission();
        if (isElementDisplayed(useEmailOrUserNameLink)){
            useEmailOrUserNameLink.click();
            allowPermission();
        }
        waitForVisibility(userNameOrEmailField);
        typeUserNameOrEmail(username);
        typePassword(password);
        logInButton.click();
        waitForVisibility(loginErrorMessage);
    }

    public boolean isElementDisplayed(MobileElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
