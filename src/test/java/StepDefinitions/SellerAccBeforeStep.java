package StepDefinitions;
import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageFactory.AccountPage;
import pageFactory.SpotBuyPage;

import java.util.HashMap;
import java.util.Map;

public class SellerAccBeforeStep {
    private WebDriver driver;
    private AccountPage accountPage;
    public static Map<String, Double> accountBalances = new HashMap<>(); // Map to store balances

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        accountPage = new AccountPage(driver);
    }

    // Constructor to initialize WebDriver and AccountPage
//    public SellerAccBeforeStep() {
//        // Initialize the WebDriver (You may want to configure it according to your setup)
////        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Set the path to your ChromeDriver
////        this.driver = new ChromeDriver();
////        this.accountPage = new AccountPage(driver);
//        this.accountBalances = new HashMap<>();
//    }

    @Given("seller navigate to the account page")
    public void seller_navigate_to_the_account_page() {
        // Use the provided account page URL
        driver.get("http://localhost:5173/myAssets/myAccount");
    }

    @When("seller should retrieve the seller's account balance")
    public void seller_should_retrieve_the_seller_s_account_balance() {
        // Retrieve balance values using AccountPage methods
        double grossBalance = accountPage.getGrossBalance();
        double availableBalance = accountPage.getAvailableBalance();
        double blockedAmount = accountPage.getBlockedAmount();

        // Store balances in the map
        accountBalances.put("grossBalance", grossBalance);
        accountBalances.put("availableBalance", availableBalance);
        accountBalances.put("blockedAmount", blockedAmount);
    }

    @Then("store the seller account balance values")
    public void store_the_seller_account_balance_values() {
        // Here you can handle the stored balances, for example:
        System.out.println("Gross Balance: " + accountBalances.get("grossBalance"));
        System.out.println("Available Balance: " + accountBalances.get("availableBalance"));
        System.out.println("Blocked Amount: " + accountBalances.get("blockedAmount"));

        // You can also implement further validation or assertions based on your needs
    }

    // Cleanup after tests
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

}
