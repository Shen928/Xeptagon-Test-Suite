package StepDefinitions;
import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageFactory.MyAccountPage;

import java.util.HashMap;
import java.util.Map;

public class SellerAccBeforeStep {
    private String totalCredits;
    private String availableCredits;
    private String blockedCredits;
    private WebDriver driver;
    private MyAccountPage myAccountPage;
    public static Map<String, Double> accountBalances = new HashMap<>(); // Map to store balances
    public static Map<String, Double> creditsBalances = new HashMap<>();

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        myAccountPage = new MyAccountPage(driver);
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
        double grossBalance = myAccountPage.getGrossBalance();
        double availableBalance = myAccountPage.getAvailableBalance();
        double blockedAmount = myAccountPage.getBlockedAmount();

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

    @Given("seller retrieves the seller's total credit balance and available credit balance")
    public void retrieve_Seller_Total_And_Available_Credit_Balance() {
        // myAccountPage.printCarbonCreditValues("CAR.088");
        // Retrieve credit balance for the specified carbon credit symbol, e.g., "CAR.002"
        totalCredits = myAccountPage.getTotalCredit("CAR.088");
        availableCredits = myAccountPage.getAvailableQuantity("CAR.088");
        blockedCredits = myAccountPage.getBlockedCredits("CAR.088");

        // Store credit balances in the static map
        creditsBalances.put("totalCredits", Double.valueOf(totalCredits));
        creditsBalances.put("availableCredits", Double.valueOf(availableCredits));
        creditsBalances.put("blockedCredits", Double.valueOf(blockedCredits));

        // Check that the balance and price are not null, meaning the symbol was found
//        Assert.assertNotNull("seller credit balance should not be null", totalCredits);
//        Assert.assertNotNull("seller credit available balance should not be null", availableCredits);
//        Assert.assertNotNull("seller credit available balance should not be null", availableCredits);
    }

    @Then("store the seller total credit balance and available credit balance")
    public void store_Seller_Total_And_Available_Credit_Balance_Values() {
        // Optionally print or log the balance values
        System.out.println("seller Total credit Balance: " + creditsBalances.get("totalCredits"));
        System.out.println("seller Available credit Balance: " + creditsBalances.get("availableCredits"));


        // You can perform further assertions or operations here if necessary
    }

    // Cleanup after tests
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

}
