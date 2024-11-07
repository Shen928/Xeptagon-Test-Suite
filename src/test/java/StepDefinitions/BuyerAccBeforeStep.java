package StepDefinitions;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageFactory.AccountPage;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class BuyerAccBeforeStep {
    private WebDriver driver;
    private AccountPage accountPage;
    public static Map<String, Double> accountBalances = new HashMap<>(); // Static map to store balances

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        accountPage = new AccountPage(driver);
    }

    @Given("buyer navigates to the account page")
    public void buyer_navigates_to_the_account_page() {
        driver.get("http://localhost:5173/myAssets/myAccount");


    }

    @When("buyer retrieves the buyer's account balance")
    public void buyer_retrieves_the_buyer_s_account_balance() {
        double grossBalance = accountPage.getGrossBalance();
        double availableBalance = accountPage.getAvailableBalance();
        double blockedAmount = accountPage.getBlockedAmount();

        // Store balances in the static map
        accountBalances.put("grossBalance", grossBalance);
        accountBalances.put("availableBalance", availableBalance);
        accountBalances.put("blockedAmount", blockedAmount);
    }

    @Then("store the buyer account balance values")
    public void store_the_buyer_account_balance_values() {
        System.out.println("Gross Balance: " + accountBalances.get("grossBalance"));
        System.out.println("Available Balance: " + accountBalances.get("availableBalance"));
        System.out.println("Blocked Amount: " + accountBalances.get("blockedAmount"));


    }



//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
