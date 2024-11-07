package StepDefinitions;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pageFactory.AccountPage;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BuyerAccAfterStep {
    private WebDriver driver;
    private AccountPage accountPage;
    private Map<String, Double> initialAccountBalances;
    private Map<String, Double> newAccountBalances;

    // Class-level variable to store local storage value
    private String spotLimitBuyCommission;
    private String spotLimitBuyCommissionType;
    private String spotLimitSellCommission;
    private String spotLimitSellCommissionType;
    private Double totalAmount;



    // Constructor or dependency injection for initial account values
    public BuyerAccAfterStep(BuyerAccBeforeStep buyerAccBeforeStep) {
        // Retrieve initial balances from BuyerAccBeforeStep
        this.initialAccountBalances = new HashMap<>(buyerAccBeforeStep.accountBalances);
        this.newAccountBalances = new HashMap<>();
    }

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        accountPage = new AccountPage(driver);
    }

//    @Given("buyer have stored the initial buyer account balance")
//    public void buyer_have_stored_the_initial_buyer_account_balance() {
//        // The initial balance is already stored in the initialAccountBalances map from BuyerAccBeforeStep
////        System.out.println("Initial Gross Balance: " + BuyerAccBeforeStep.accountBalances.get("grossBalance"));
////        System.out.println("Initial Gross Balance: " + initialAccountBalances.get("grossBalance"));
////        System.out.println("Initial Available Balance: " + initialAccountBalances.get("availableBalance"));
////        System.out.println("Initial Blocked Amount: " + initialAccountBalances.get("blockedAmount"));
//    }

    @Given("buyer retrieve the new buyer account balance")
    public void buyer_retrieve_the_new_buyer_account_balance() {
        driver.get("http://localhost:5173/myAssets/myAccount");

        // Cast WebDriver to JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get value from local storage and store it in the class variable
        spotLimitBuyCommission = (String) js.executeScript("return localStorage.getItem('spotLimitBuyCommission');");
        spotLimitBuyCommissionType = (String) js.executeScript("return localStorage.getItem('spotLimitBuyCommissionType');");
        spotLimitSellCommission = (String) js.executeScript("return localStorage.getItem('spotLimitSellCommission');");
        spotLimitSellCommissionType = (String) js.executeScript("return localStorage.getItem('spotLimitSellCommissionType');");


        // Retrieve balance values after the limit buy order is placed
        newAccountBalances.put("grossBalance", accountPage.getGrossBalance());
        newAccountBalances.put("availableBalance", accountPage.getAvailableBalance());
        newAccountBalances.put("blockedAmount", accountPage.getBlockedAmount());

        System.out.println("New Gross Balance: " + newAccountBalances.get("grossBalance"));
        System.out.println("New Available Balance: " + newAccountBalances.get("availableBalance"));
        System.out.println("New Blocked Amount: " + newAccountBalances.get("blockedAmount"));


    }

    @When("buyer validate that the gross balance has decreased")
    public void buyer_validate_that_the_gross_balance_has_decreased() {
        // Retrieve the stored price and quantity
        String price = (String) ScenarioContext.get("limitBuyPrice");
        String quantity = (String) ScenarioContext.get("limitBuyQuantity");

        Double initialGrossBalance = initialAccountBalances.get("grossBalance");
        Double newGrossBalance = newAccountBalances.get("grossBalance");

        Double priceValue = Double.parseDouble(price);
        Double quantityValue = Double.parseDouble(quantity);
        Double buyCommission = Double.parseDouble(spotLimitBuyCommission);
        totalAmount = (priceValue * quantityValue)  + (priceValue * quantityValue) * buyCommission / 100;

        Double expectedGrossBalance = initialGrossBalance - (totalAmount);
        Assert.assertEquals(newGrossBalance, expectedGrossBalance, 0.01,"Fiat currency gross balance should match the expected new gross balance after buy order");

//        System.out.println("Limit Buy Price: " + priceValue);
//        System.out.println("Limit Buy Quantity: " + quantityValue);
//        System.out.println("BuyCommission: " + BuyCommission);
//        System.out.println("initate old : " + initialGrossBalance);
//        Double fee = (priceValue * quantityValue) * BuyCommission / 100;
//        System.out.println("This is fee " + fee);
    }

    @Then("buyer validate that the available balance has decreased")
    public void buyer_validate_that_the_available_balance_has_decreased() {
        // Validate that the available balance has decreased accordingly
        Double initialAvailableBalance = initialAccountBalances.get("availableBalance");
        Double newAvailableBalance = newAccountBalances.get("availableBalance");
        Double expectedGrossBalance = initialAvailableBalance - (totalAmount);
        Assert.assertEquals(newAvailableBalance, expectedGrossBalance, 0.01,"Fiat currency available balance should match the expected new available balance after buy order");
//        System.out.println("initialAvailableBalance" + initialAvailableBalance);
//        System.out.println("newAvailableBalance" + newAvailableBalance);
//        System.out.println("expectedGrossBalance" + expectedGrossBalance);

    }

    @Then("buyer logout from application")
    // Cleanup after tests
    public void buyer_logout_from_application() {
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        accountPage.clickPopIconButton();
        accountPage.clickLogOutButton();


    }
}
