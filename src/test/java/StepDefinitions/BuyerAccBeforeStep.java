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

public class BuyerAccBeforeStep {

    private String totalCredits;
    private String lastPriceForSymbol;
    private String availableCredits;
    private WebDriver driver;
    private MyAccountPage myAccountPage;
    public static Map<String, Double> accountBalances = new HashMap<>(); // Static map to store balances
    public static Map<String, Double> creditsBalances = new HashMap<>();

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        myAccountPage = new MyAccountPage(driver);
    }

    @Given("buyer navigates to the account page")
    public void buyer_navigates_to_the_account_page() {
        driver.get("http://localhost:5173/myAssets/myAccount");


    }

    @When("buyer retrieves the buyer's account balance")
    public void buyer_retrieves_the_buyer_s_account_balance() {
        double grossBalance = myAccountPage.getGrossBalance();
        double availableBalance = myAccountPage.getAvailableBalance();
        double blockedAmount = myAccountPage.getBlockedAmount();

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

    @Given("buyer retrieves the buyer's total credit balance and available credit balance")
    public void retrieve_Buyer_Total_And_Available_Credit_Balance() {
       // myAccountPage.printCarbonCreditValues("CAR.088");
        // Retrieve credit balance for the specified carbon credit symbol, e.g., "CAR.002"
        totalCredits = myAccountPage.getTotalCredit("CAR.088");
        availableCredits = myAccountPage.getAvailableQuantity("CAR.088");

        // Store credit balances in the static map
        creditsBalances.put("totalCredits", Double.valueOf(totalCredits));
        creditsBalances.put("availableCredits", Double.valueOf(availableCredits));

        // Check that the balance and price are not null, meaning the symbol was found
//        Assert.assertNotNull("Buyer credit balance should not be null", totalCredits);
//        Assert.assertNotNull("Buyer credit available balance should not be null", availableCredits);
    }

    @Then("store the buyer total credit balance and available credit balance")
    public void store_Buyer_Total_And_Available_Credit_Balance_Values() {
        // Optionally print or log the balance values
        System.out.println("Total Credit Balance: " + creditsBalances.get("totalCredits"));
        System.out.println("Available Credit Balance: " + creditsBalances.get("availableCredits"));


        // You can perform further assertions or operations here if necessary
    }



//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
