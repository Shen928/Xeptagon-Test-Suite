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
import java.util.List;

public class SpotBuyPage {
    private WebDriver driver;

//    @Before
//    public void setUp() {
//        driver = DriverManager.getDriver();
//    }

    public SpotBuyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "limit-order-button")
    WebElement orderType;

    @FindBy(id = "buy-price")
    WebElement limitPriceInput;

    @FindBy(id = "buy-quantity")
    WebElement quantityInput;

    @FindBy(id = "buy-btn")
    WebElement submitOrderButton;

    @FindBy(id = "success-message")
    WebElement successMessage;

    @FindBy(id="info-notification")
    WebElement infoNotification;

    @FindBy(css = "[data-test='cancel-all-btn']")
    WebElement cancelAllButton;

    @FindBy(css = "[data-test='cancel-now']")
    WebElement cancelNowButton;

    // Locate all order id rows
    @FindBy(css = "[data-test='openOrderId']")
    List<WebElement> openOrderIds;

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

    public boolean isSuccessMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
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

    public boolean isInfoNotificationDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        try {
            wait.until(ExpectedConditions.visibilityOf(infoNotification));
            return infoNotification.isDisplayed();
        } catch (Exception e) {
            return false; // Return false if the message does not appear within 3 seconds
        }
    }

    public String getInfoNotificationText() {
        return infoNotification.getText();
    }

    public void clickCancelAllButton(){
        cancelAllButton.click();

        // Wait for a specific time interval (e.g., 2 seconds)
        try {
            Thread.sleep(2000); // 2000 milliseconds = 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cancelNowButton.click();
        infoNotification.click();

    }

    public boolean areAllOrdersCanceled() {
        return openOrderIds.isEmpty(); // Returns true if no open order IDs are found
    }
}
