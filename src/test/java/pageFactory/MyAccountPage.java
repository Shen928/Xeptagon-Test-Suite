package pageFactory;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MyAccountPage {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
    }

    // Constructor
    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initialize Page Factory elements
    }

    // Locate the web elements using @FindBy
    @FindBy(css = "[data-test='gross_balance']")
    WebElement grossBalanceElement;

    @FindBy(css = "[data-test='available_balance']")
    WebElement availableBalanceElement;

    @FindBy(css = "[data-test='blocked_amount']")
    WebElement blockedAmountElement;

    @FindBy(css = "[data-test='pop-icon']")
    WebElement popIcon;

    // Locate the carbon credits table
    @FindBy(css = "[data-test='carbonCreditsTable']")
    WebElement carbonCreditsTable;

    // Locate all carbon credit rows (symbols)
    @FindBy(css = "[data-test='carbonCredit']")
    List<WebElement> carbonCreditSymbols;

    // Locate the available Quantity, last price, and balance columns
    @FindBy(css = "[data-test='availableQuantity']")
    List<WebElement> availableQuantityCells;

    @FindBy(css = "[data-test='totalQuantity']")
    List<WebElement> totalQuantityCells;

    @FindBy(css = "[data-test='lastPrice']")
    List<WebElement> lastPriceCells;

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

    // Click the pop icon button
    public void clickPopIconButton() {
        popIcon.click();
    }

    // Click the log out button
    @FindBy(css = "[data-test='logout-btn']")
    WebElement logoutBtn;

    public void clickLogOutButton(){
        logoutBtn.click();
    }

    // Method to get the buyer's credit balance for a specific symbol
    public String getTotalCredit(String symbol) {
        for (int i = 0; i < carbonCreditSymbols.size(); i++) {
            if (carbonCreditSymbols.get(i).getText().equals(symbol)) {
                // Assuming balance information is in the same row as the symbol
                return totalQuantityCells.get(i).getText();
            }
        }
        return null; // Return null if the symbol is not found
    }

    // Method to get available quantity for a specific symbol
    public String getAvailableQuantity(String symbol) {
        for (int i = 0; i < carbonCreditSymbols.size(); i++) {
            if (carbonCreditSymbols.get(i).getText().equals(symbol)) {
                return availableQuantityCells.get(i).getText();
            }
        }
        return null; // Return null if the symbol is not found
    }


    // Method to get last price for a specific symbol
//    public String getLastPriceForSymbol(String symbol) {
//        for (int i = 0; i < carbonCreditSymbols.size(); i++) {
//            if (carbonCreditSymbols.get(i).getText().equals(symbol)) {
//                return lastPriceCells.get(i).getText();
//            }
//        }
//        return null; // Return null if the symbol is not found
//    }


}
