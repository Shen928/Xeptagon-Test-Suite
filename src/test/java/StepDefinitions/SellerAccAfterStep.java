package StepDefinitions;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.it.Ma;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageFactory.AccountPage;
import org.testng.Assert;
import pageFactory.LoginPage;
import pageFactory.MarketPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SellerAccAfterStep {
    private WebDriver driver;
    private LoginPage loginPage;
    private MarketPage marketPage;
    private AccountPage accountPage;
    private Map<String, Double> initialAccountBalances;
    private Map<String, Double> newAccountBalances;

    // Class-level variable to store local storage values
    private String spotLimitSellCommission;
    private Double totalAmount;

    // Constructor or dependency injection for initial account values
    public SellerAccAfterStep(SellerAccBeforeStep sellerAccBeforeStep) {
        // Retrieve initial balances from SellerAccBeforeStep
        this.initialAccountBalances = new HashMap<>(sellerAccBeforeStep.accountBalances);
        this.newAccountBalances = new HashMap<>();
    }

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        marketPage = new MarketPage(driver);
        accountPage = new AccountPage(driver);
//        loginToSellerAccount();  // Log in before executing the balance check steps
    }

//    private void loginToSellerAccount() {
//        driver.get("http://localhost:5173/login");
////        loginPage.enterEmail("shehans+EX2@xeptagon.com");  // Use valid email
////        loginPage.enterPassword("EX2@xeptagon.coM");         // Use valid password
////        loginPage.clickLoginButton();
//    }

    @Given("the seller logs into their account")
    public void seller_login_into_seller_account() {
        driver.get("http://localhost:5173/signIn");
        loginPage.enterEmail("shehans+EX2@xeptagon.com");  // Use valid email
        loginPage.enterPassword("EX2@xeptagon.coM");         // Use valid password
        loginPage.clickLoginButton();
        marketPage.is_load_market_page();
        // Implement login functionality with LoginPage methods

    }

    @When("the seller navigates to the account page")
    public void seller_navigate_to_the_seller_account_page() {
        driver.get("http://localhost:5173/myAssets/myAccount");
    }

    @When("the seller retrieves the new account balance")
    public void seller_retrieve_the_new_seller_account_balance() {
        // Cast WebDriver to JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get commission value from local storage and store it in the class variable
        spotLimitSellCommission = (String) js.executeScript("return localStorage.getItem('spotLimitSellCommission');");

        // Retrieve balance values after the limit sell order is placed
        newAccountBalances.put("grossBalance", accountPage.getGrossBalance());
        newAccountBalances.put("availableBalance", accountPage.getAvailableBalance());
        newAccountBalances.put("blockedAmount", accountPage.getBlockedAmount());

        System.out.println("New Gross Balance: " + newAccountBalances.get("grossBalance"));
        System.out.println("New Available Balance: " + newAccountBalances.get("availableBalance"));
        System.out.println("New Blocked Amount: " + newAccountBalances.get("blockedAmount"));
    }

    @When("the seller validates that the gross balance has increased")
    public void seller_validate_that_the_gross_balance_has_increased() {
        // Retrieve the stored price and quantity
        String price = (String) ScenarioContext.get("limitSellPrice");
        String quantity = (String) ScenarioContext.get("limitSellQuantity");

        Double initialGrossBalance = initialAccountBalances.get("grossBalance");
        Double newGrossBalance = newAccountBalances.get("grossBalance");

        Double priceValue = Double.parseDouble(price);
        Double quantityValue = Double.parseDouble(quantity);
        Double sellCommission = Double.parseDouble(spotLimitSellCommission);
        totalAmount = (priceValue * quantityValue) - ((priceValue * quantityValue) * sellCommission / 100);
        System.out.println("sellCommission " + sellCommission);
        System.out.println("totalAmount " + totalAmount);
        Double expectedGrossBalance = initialGrossBalance + totalAmount;
        Assert.assertEquals(newGrossBalance, expectedGrossBalance, 0.01, "Fiat currency gross balance should match the expected new gross balance after sell order");
    }

    @Then("the seller validates that the available balance has increased")
    public void seller_validate_that_the_available_balance_has_increased() {
        Double initialAvailableBalance = initialAccountBalances.get("availableBalance");
        Double newAvailableBalance = newAccountBalances.get("availableBalance");

        Double expectedAvailableBalance = initialAvailableBalance + totalAmount;
        Assert.assertEquals(newAvailableBalance, expectedAvailableBalance, 0.01, "Fiat currency available balance should match the expected new available balance after sell order");
    }
}
