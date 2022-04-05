package pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StartPage {

    public StartPage(AppiumDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @FindBy(id = "com.snapchat.android:id/login_button_horizontal")
    private MobileElement loginButton;

    @FindBy(id = "com.snapchat.android:id/signup_button_horizontal")
    private MobileElement signUpButton;

    public void loginButtonClick(){
        loginButton.click();
    }
    public void signUpButtonClick(){
        signUpButton.click();
    }
}
