package pageFactory;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
    }

    // Constructor
    public AccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initialize Page Factory elements
    }

    // Locate the web elements using @FindBy
    @FindBy(css = "[data-test='gross_balance']")
    private WebElement grossBalanceElement;

    @FindBy(css = "[data-test='available_balance']")
    private WebElement availableBalanceElement;

    @FindBy(css = "[data-test='blocked_amount']")
    private WebElement blockedAmountElement;

    // Method to get gross balance
    public double getGrossBalance() {
        return Double.parseDouble(grossBalanceElement.getText().replace(",", ""));
    }

    // Method to get available balance
    public double getAvailableBalance() {
        return Double.parseDouble(availableBalanceElement.getText().replace(",", ""));
    }

    // Method to get blocked amount
    public double getBlockedAmount() {
        return Double.parseDouble(blockedAmountElement.getText().replace(",", ""));
    }
}
