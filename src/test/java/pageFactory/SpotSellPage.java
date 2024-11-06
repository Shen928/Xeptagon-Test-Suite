package pageFactory;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SpotSellPage {
    private WebDriver driver;

//    @Before
//    public void setUp() {
//        driver = DriverManager.getDriver();
//    }

    public SpotSellPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "limit-order-button")
    WebElement orderType;

    @FindBy(id = "sell-price")
    WebElement limitPriceInput;

    @FindBy(id = "sell-quantity")
    WebElement quantityInput;

    @FindBy(id = "sell-btn")
    WebElement submitOrderButton;

    @FindBy(id = "success-message")
    WebElement successMessage;

    @FindBy(id="info-notification")
    WebElement infoNotification;

    public void selectLimitOrderType() {
        orderType.click();

    }

    public void enterLimitPrice(String price) {
        limitPriceInput.clear();
        limitPriceInput.sendKeys(price);
    }

    public void enterQuantity(String quantity) {
        quantityInput.clear();
        quantityInput.sendKeys(quantity);
    }

    public void submitOrder() {
        submitOrderButton.click();
    }

    // Updated method to check if the success message is displayed with WebDriverWait
    public boolean isSuccessMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(successMessage));
            return successMessage.isDisplayed();
        } catch (Exception e) {
            return false; // Return false if the message does not appear within 3 seconds
        }
    }

    public String getSuccessMessageText() {
        return successMessage.getText();
    }
//
//    public boolean isInfoNotificationDisplayed() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        try {
//            wait.until(ExpectedConditions.visibilityOf(infoNotification));
//            return infoNotification.isDisplayed();
//        } catch (Exception e) {
//            return false; // Return false if the message does not appear within 3 seconds
//        }
//    }
//
//    public String getInfoNotificationText() {
//        return infoNotification.getText();
//    }

}
