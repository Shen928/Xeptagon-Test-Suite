package StepDefinitions;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.it.Ma;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageFactory.MyAccountPage;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class BuyerAccAfterStep {
    private Map<String, Double> initialCreditBalances;
    private WebDriver driver;
    private MyAccountPage myAccountPage;
    private Map<String, Double> initialAccountBalances;
    private Map<String, Double> newAccountBalances;
    private Map<String, Double> newCreditBalances;

    // Class-level variable to store local storage value
    private String spotLimitBuyCommission;
    private String spotLimitBuyCommissionType;
    private String spotLimitSellCommission;
    private String spotLimitSellCommissionType;
    private Double totalTradeAmount;
    private Double totalBuyAmount;
    private Double remainBlockedAmount;




    // Constructor or dependency injection for initial account values
    public BuyerAccAfterStep(BuyerAccBeforeStep buyerAccBeforeStep) {
        // Retrieve initial balances from BuyerAccBeforeStep
        this.initialAccountBalances = new HashMap<>(buyerAccBeforeStep.accountBalances);
        this.initialCreditBalances = new HashMap<>(buyerAccBeforeStep.creditsBalances);
        this.newAccountBalances = new HashMap<>();
        this.newCreditBalances = new HashMap<>();
    }

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        myAccountPage = new MyAccountPage(driver);
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
        // Cast WebDriver to JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get value from local storage and store it in the class variable
        spotLimitBuyCommission = (String) js.executeScript("return localStorage.getItem('spotLimitBuyCommission');");
        spotLimitBuyCommissionType = (String) js.executeScript("return localStorage.getItem('spotLimitBuyCommissionType');");
        spotLimitSellCommission = (String) js.executeScript("return localStorage.getItem('spotLimitSellCommission');");
        spotLimitSellCommissionType = (String) js.executeScript("return localStorage.getItem('spotLimitSellCommissionType');");


        // Retrieve balance values after the limit buy order is placed
        newAccountBalances.put("grossBalance", myAccountPage.getGrossBalance());
        newAccountBalances.put("availableBalance", myAccountPage.getAvailableBalance());
        newAccountBalances.put("blockedAmount", myAccountPage.getBlockedAmount());

        System.out.println("New Gross Balance: " + newAccountBalances.get("grossBalance"));
        System.out.println("New Available Balance: " + newAccountBalances.get("availableBalance"));
        System.out.println("New Blocked Amount: " + newAccountBalances.get("blockedAmount"));


    }

    @When("buyer validate that the gross balance has decreased")
    public void buyer_validate_that_the_gross_balance_has_decreased() {
        // Retrieve the stored price and quantity
        String buyPrice = (String) ScenarioContext.get("limitBuyPrice");
        String buyQuantity = (String) ScenarioContext.get("limitBuyQuantity");
        String sellPrice = (String) ScenarioContext.get("limitSellPrice");
        String sellQuantity = (String) ScenarioContext.get("limitSellQuantity");

        Double initialGrossBalance = initialAccountBalances.get("grossBalance");

        Double newGrossBalance = newAccountBalances.get("grossBalance");


        Double priceValue = Double.parseDouble(buyPrice);
        Double sellQuantityValue = Double.parseDouble(sellQuantity);
        Double buyQuantityValue = Double.parseDouble(buyQuantity);
        Double buyCommission = Double.parseDouble(spotLimitBuyCommission);

        totalTradeAmount = (priceValue * sellQuantityValue)  + ((priceValue * sellQuantityValue) * buyCommission / 100);
        totalBuyAmount = (priceValue * sellQuantityValue)  + ((priceValue * buyQuantityValue) * buyCommission / 100);

        Double expectedGrossBalance = initialGrossBalance - (totalTradeAmount);

        System.out.println("Limit Buy Price: " + buyPrice);
        System.out.println("Limit Buy Quantity: " + buyQuantity);
        System.out.println("Limit sell Price: " + sellPrice);
        System.out.println("Limit sell Quantity: " + sellQuantity);
        System.out.println("totalAmount: " + totalTradeAmount);
        System.out.println("expectedGrossBalance: " + expectedGrossBalance);
        System.out.println("initialGrossBalance: " + initialGrossBalance);
        Assert.assertEquals(newGrossBalance, expectedGrossBalance, 0.01,"Fiat currency gross balance should match the expected new gross balance after buy order");


//        System.out.println("initate old : " + initialGrossBalance);
//        Double fee = (priceValue * quantityValue) * BuyCommission / 100;
//        System.out.println("This is fee " + fee);
    }

    @Then("buyer validate that the available balance has decreased")
    public void buyer_validate_that_the_available_balance_has_decreased() {
        // Validate that the available balance has decreased accordingly





        //////
        // Retrieve the stored price and quantity
        String buyPrice = (String) ScenarioContext.get("limitBuyPrice");
        String buyQuantity = (String) ScenarioContext.get("limitBuyQuantity");
        String sellPrice = (String) ScenarioContext.get("limitSellPrice");
        String sellQuantity = (String) ScenarioContext.get("limitSellQuantity");

        Double initialAvailableBalance = initialAccountBalances.get("availableBalance");

        Double newAvailableBalance = newAccountBalances.get("availableBalance");


        Double priceValue = Double.parseDouble(buyPrice);
        Double sellQuantityValue = Double.parseDouble(sellQuantity);
        Double buyQuantityValue = Double.parseDouble(buyQuantity);
        Double buyCommission = Double.parseDouble(spotLimitBuyCommission);

        totalTradeAmount = (priceValue * sellQuantityValue)  + ((priceValue * sellQuantityValue) * buyCommission / 100);
        totalBuyAmount = (priceValue * buyQuantityValue)  + ((priceValue * buyQuantityValue) * buyCommission / 100);

        Double expectedAvailableBalance = initialAvailableBalance - (totalBuyAmount);

        System.out.println("Limit Buy Price: " + buyPrice);
        System.out.println("Limit Buy Quantity: " + buyQuantity);
        System.out.println("Limit sell Price: " + sellPrice);
        System.out.println("Limit sell Quantity: " + sellQuantity);
        System.out.println("totalAmount: " + totalTradeAmount);
        System.out.println("totalBuyAmount: " + totalBuyAmount);
        System.out.println("newAvailableBalance" + newAvailableBalance);
        System.out.println("expectedAvailableBalance" + expectedAvailableBalance);
        System.out.println("initialAvailableBalance" + initialAvailableBalance);


        Assert.assertEquals(newAvailableBalance, expectedAvailableBalance, 0.01,"Fiat currency available balance should match the expected new available balance after buy order");



    }

    @Then("buyer validate that the block amount has changed")
    public void buyer_validate_that_the_block_amount_has_changed() {
        // Retrieve the stored price and quantity
        String buyPrice = (String) ScenarioContext.get("limitBuyPrice");
        String buyQuantity = (String) ScenarioContext.get("limitBuyQuantity");
        String sellPrice = (String) ScenarioContext.get("limitSellPrice");
        String sellQuantity = (String) ScenarioContext.get("limitSellQuantity");


        Double initialBlockedAmount = initialAccountBalances.get("blockedAmount");
        Double newBlockedAmount = newAccountBalances.get("blockedAmount");

        Double buyPriceValue = Double.parseDouble(buyPrice);
        Double sellPriceValue = Double.parseDouble(sellPrice);
        Double SellQuantityValue = Double.parseDouble(sellQuantity);
        Double BuyQuantityValue = Double.parseDouble(buyQuantity);


        Double buyCommission = Double.parseDouble(spotLimitBuyCommission);

        //remainBlockedAmount = ((buyPriceValue * BuyQuantityValue)  - (sellPriceValue * SellQuantityValue)) * buyCommission / 100;
        remainBlockedAmount = (BuyQuantityValue-SellQuantityValue) * buyPriceValue * (1 + buyCommission/100);

        Double expectedBlockedAmount = initialBlockedAmount + (remainBlockedAmount);

//        System.out.println("Limit Buy Price: " + buyPrice);
//        System.out.println("Limit Buy Quantity: " + buyQuantity);
//        System.out.println("Limit sell Price: " + sellPrice);
//        System.out.println("Limit sell Quantity: " + sellQuantity);
        System.out.println("remainBlockedAmount: " + remainBlockedAmount);
        System.out.println("expectedBlockedAmount: " + expectedBlockedAmount);
        System.out.println("initialBlockedAmount: " + initialBlockedAmount);
        Assert.assertEquals(newBlockedAmount, expectedBlockedAmount, 0.01,"Fiat currency blocked amount should match the expected blocked amount after buy order");


//        System.out.println("initate old : " + initialGrossBalance);
//        Double fee = (priceValue * quantityValue) * BuyCommission / 100;
//        System.out.println("This is fee " + fee);
    }

    //credit balances

    @Given("buyer retrieve the new buyer credit balances")
    public void buyer_retrieve_the_new_buyer_credit_balances() {
//        driver.get("http://localhost:5173/myAssets/myAccount");
//
//        // Cast WebDriver to JavascriptExecutor
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//        // Get value from local storage and store it in the class variable
//        spotLimitBuyCommission = (String) js.executeScript("return localStorage.getItem('spotLimitBuyCommission');");
//        spotLimitBuyCommissionType = (String) js.executeScript("return localStorage.getItem('spotLimitBuyCommissionType');");
//        spotLimitSellCommission = (String) js.executeScript("return localStorage.getItem('spotLimitSellCommission');");
//        spotLimitSellCommissionType = (String) js.executeScript("return localStorage.getItem('spotLimitSellCommissionType');");


        // Retrieve balance values after the limit buy order is placed
        newCreditBalances.put("totalCredits", Double.valueOf(myAccountPage.getTotalCredit("CAR.088")));

        newCreditBalances.put("availableCredits", Double.valueOf(myAccountPage.getAvailableQuantity("CAR.088")));


        System.out.println("New total seller Credits Balance: " + newCreditBalances.get("totalCredits"));
        System.out.println("New Available seller credits Balance: " + newCreditBalances.get("availableCredits"));


    }

    @When("buyer validate that the total credit balance has increased")
    public void buyer_validate_that_the_total_credit_has_increased() {
        // Retrieve the stored price and quantity
        String quantity = (String) ScenarioContext.get("limitSellQuantity");
        Double quantityValue = Double.parseDouble(quantity);

        // Validate that the total credit has decreased accordingly
        Double initialTotalCredits = initialCreditBalances.get("totalCredits");
        Double newTotalCredits = newCreditBalances.get("totalCredits");

        System.out.println("initialTotalCredits " + initialTotalCredits);
        System.out.println("newTotalCredits " + newTotalCredits);

        Double expectedCreditBalance = initialTotalCredits + quantityValue;
        Assert.assertEquals(newTotalCredits, expectedCreditBalance, 0.01, "Buyer total credit balance should match the expected new total credit balance after buy order");

    }

    @Then("buyer validate that the available credit balance has increased")
    public void buyer_validate_that_the_available_credit_balance_has_increased() {
        // Retrieve the stored price and quantity
        String quantity = (String) ScenarioContext.get("limitSellQuantity");    //in partially filled scenario we trade sell Q=2 with buy Q=7, so we need to use actual trade quantity which mean Q=2, this can also use full filed scenario.
        Double quantityValue = Double.parseDouble(quantity);

        // Validate that the total credit has decreased accordingly
        Double initialAvailableCredits = initialCreditBalances.get("availableCredits");
        Double newAvailableCredits = newCreditBalances.get("availableCredits");


        Double expectedCreditBalance = initialAvailableCredits + quantityValue;
        Assert.assertEquals(newAvailableCredits, expectedCreditBalance, 0.01, "Buyer available credit balance should match the expected new available credit balance after buy order");
        System.out.println("initialAvailableCredits " + initialAvailableCredits);
        System.out.println("newAvailableCredits " + newAvailableCredits);
    }


    @Then("buyer logout from application")
    // Cleanup after tests
    public void buyer_logout_from_application() {
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        myAccountPage.clickPopIconButton();
        myAccountPage.clickLogOutButton();


    }
}
