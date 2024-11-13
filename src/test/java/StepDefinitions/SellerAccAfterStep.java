package StepDefinitions;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageFactory.MyAccountPage;
import org.testng.Assert;
import pageFactory.LoginPage;
import pageFactory.MarketPage;

import java.util.HashMap;
import java.util.Map;

public class SellerAccAfterStep {
    private WebDriver driver;
    private LoginPage loginPage;
    private MarketPage marketPage;
    private MyAccountPage myAccountPage;
    private Map<String, Double> initialAccountBalances;
    private Map<String, Double> initialCreditBalances;
    private Map<String, Double> newAccountBalances;
    private Map<String, Double> newCreditBalances;

    // Class-level variable to store local storage values
    private String spotLimitSellCommission;
    private Double totalAmount;
    private Double newTotalCredits;

    // Constructor or dependency injection for initial account values
    public SellerAccAfterStep(SellerAccBeforeStep sellerAccBeforeStep) {
        // Retrieve initial balances from SellerAccBeforeStep
        this.initialAccountBalances = new HashMap<>(sellerAccBeforeStep.accountBalances);
        this.initialCreditBalances = new HashMap<>(sellerAccBeforeStep.creditsBalances);
        this.newAccountBalances = new HashMap<>();
        this.newCreditBalances = new HashMap<>();
    }

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        marketPage = new MarketPage(driver);
        myAccountPage = new MyAccountPage(driver);
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
        newAccountBalances.put("grossBalance", myAccountPage.getGrossBalance());
        newAccountBalances.put("availableBalance", myAccountPage.getAvailableBalance());
        newAccountBalances.put("blockedAmount", myAccountPage.getBlockedAmount());

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
        System.out.println("newAvailableBalance " + newAvailableBalance);
        Double expectedAvailableBalance = initialAvailableBalance + totalAmount;
        System.out.println("seller expectedAvailableBalance" + expectedAvailableBalance);
        Assert.assertEquals(newAvailableBalance, expectedAvailableBalance, 0.01, "Fiat currency available balance should match the expected new available balance after sell order");
    }




    //credit
    @Given("seller retrieve the new seller credit balances")
    public void seller_retrieve_the_new_seller_credit_balances() {
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

    @When("seller validate that the total credit balance has decreased")
    public void seller_validate_that_the_total_credit_has_decreased() {

        // Retrieve the stored price and quantity
        String price = (String) ScenarioContext.get("limitSellPrice");
        String quantity = (String) ScenarioContext.get("limitSellQuantity");
        Double quantityValue = Double.parseDouble(quantity);

        // Validate that the total credit has decreased accordingly
        Double initialTotalCredits = initialCreditBalances.get("totalCredits");
        Double newTotalCredits = newCreditBalances.get("totalCredits");


        Double expectedCreditBalance = initialTotalCredits - quantityValue;
        Assert.assertEquals(newTotalCredits, expectedCreditBalance, 0.01, "Seller total credit balance should match the expected new total credit balance after sell order");

    }

    @Then("seller validate that the available credit balance has decreased")
    public void buyer_validate_that_the_available_credit_balance_has_decreased() {
        // Retrieve the stored price and quantity
        String quantity = (String) ScenarioContext.get("limitSellQuantity");
        Double quantityValue = Double.parseDouble(quantity);

        // Validate that the total credit has decreased accordingly
        Double initialAvailableCredits = initialCreditBalances.get("availableCredits");
        Double newAvailableCredits = newCreditBalances.get("availableCredits");


        Double expectedCreditBalance = initialAvailableCredits - quantityValue;
        Assert.assertEquals(newAvailableCredits, expectedCreditBalance, 0.01, "Seller available credit balance should match the expected new available credit balance after sell order");

    }


    @Then("the seller's available account balance should be unchanged from the initial available account balance")
    public void sellers_available_account_balance_should_be_unchanged_from_the_initial_available_account_balance() {
        // Logic to compare the current account balance with the initial account balance
        Double initialAvailableBalance = initialAccountBalances.get("availableBalance");
        Double newAvailableBalance = newAccountBalances.get("availableBalance");
        System.out.println("seller newAvailableBalance " + newAvailableBalance);
        Double expectedAvailableBalance = initialAvailableBalance;
        System.out.println("seller expectedAvailableBalance " + expectedAvailableBalance);
        Assert.assertEquals(newAvailableBalance, expectedAvailableBalance, 0.01, "Fiat currency available balance should not be changed after canceled spot limit sell orders");
    }

    @When("the available carbon credit balance should equal the initial credit balance")
    public void available_carbon_credit_balance_should_equal_the_initial_credit_balance() {
        // Logic to compare the available carbon credit balance with the initial credit balance

        // Validate that the total credit has decreased accordingly
        Double initialAvailableCredits = initialCreditBalances.get("availableCredits");
        Double newAvailableCredits = newCreditBalances.get("availableCredits");

        Double expectedCreditBalance = initialAvailableCredits;
        Assert.assertEquals(newAvailableCredits, expectedCreditBalance, 0.01, "Buyer available credit balance should not be changed after canceled spot limit sell orders");
        System.out.println("initialAvailableCredits " + initialAvailableCredits);
        System.out.println("newAvailableCredits " + newAvailableCredits);
    }



//    @Then("buyer logout from application")
//    // Cleanup after tests
//    public void buyer_logout_from_application() {
////        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//        myAccountPage.clickPopIconButton();
//        myAccountPage.clickLogOutButton();
//
//
//    }
}
