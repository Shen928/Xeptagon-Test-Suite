//package StepDefinitions;
//import CommonClasses.DriverManager;
//import io.cucumber.java.Before;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import pageFactory.SpotSellPage;
//
//public class SpotSellOrdersSteps {
//
//    SpotSellPage spotPage;
//
//    private WebDriver driver;
//
//    @Before
//    public void setUp() {
//        driver = DriverManager.getDriver();
//        spotPage = new SpotSellPage(driver);
//
//    }
//
//    @Given("the user navigates to the spot order placement page")
//    public void the_user_navigates_to_the_spot_order_placement_page() {
//        driver.get("http://localhost:5173/trade/spot/CAR.085"); // Replace with actual URL
//    }
//
//    @When("the user selects the order type")
//    public void the_user_selects_the_order_type() {
//        spotPage.selectLimitOrderType();
//    }
//
//    @When("the user enters a limit price of {string}")
//    public void the_user_enters_a_limit_sell_price_of(String price) {
//        spotPage.enterLimitPrice(price);
//    }
//
//    @When("the user enters a quantity of {string}")
//    public void the_user_enters_a_quantity_of(String quantity) {
//        spotPage.enterQuantity(quantity);
//    }
//
//    @When("the user submits the order")
//    public void the_user_submits_the_order() {
//        spotPage.submitOrder();
//    }
//
////    @Then("the sell order should be placed successfully")
////    public void the_sell_order_should_be_placed_successfully() {
////        Assert.assertTrue(spotPage.isSuccessMessageDisplayed(), "Success notification did not appear or text did not match.");
////    }
//
//    @Then("the sell order should be placed successfully")
//    public void the_sell_order_should_be_placed_successfully() {
//        Assert.assertTrue(spotPage.isSuccessMessageDisplayed(), "Success message is not displayed");
//
//        String expectedMessage = "Your order has been successfully placed";
//        Assert.assertEquals(spotPage.getSuccessMessageText(), expectedMessage);
//
//    }
//////    @Then("the sell order should be visible in the order book")
////    public void the_sell_order_should_be_visible_in_the_order_book() {
////        Assert.assertTrue(spotPage.isOrderVisibleInOrderBook(), "Sell order is not visible in the order book.");
////    }
//}

package StepDefinitions;

import CommonClasses.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageFactory.SpotSellPage;

import static org.testng.AssertJUnit.assertTrue;

public class SpotSellOrdersSteps {

    SpotSellPage spotPage;
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        spotPage = new SpotSellPage(driver);
    }



    @Given("the seller navigates to the spot order placement page")
    public void the_seller_navigates_to_the_spot_order_placement_page() {
        driver.get("http://localhost:5173/trade/spot/CAR.088"); // Replace with actual URL
    }

    @When("the seller selects the order type")
    public void the_seller_selects_the_order_type() {
        spotPage.selectLimitOrderType();
    }

    @And("the seller enters a limit sell price of {string}")
    public void the_seller_enters_a_limit_sell_price_of(String price) {
        spotPage.enterLimitPrice(price);
        ScenarioContext.put("limitSellPrice", price);
    }

    @And("the seller enters a quantity of {string}")
    public void the_seller_enters_a_quantity_of(String quantity) {
        spotPage.enterQuantity(quantity);
        ScenarioContext.put("limitSellQuantity", quantity);
    }

    @And("the seller submits the order")
    public void the_seller_submits_the_order() {
        spotPage.submitOrder();
    }

    @And("the sell order should be placed successfully")
    public void the_sell_order_should_be_placed_successfully() {
        Assert.assertTrue(spotPage.isSuccessMessageDisplayed(), "Success message is not displayed");

        String expectedMessage = "Your order has been successfully placed";
        Assert.assertEquals(spotPage.getSuccessMessageText(), expectedMessage);
    }

    @And("seller cancels all open spot limit sell orders")
    public void seller_cancels_all_open_spot_limit_sell_orders() throws InterruptedException {
        // Wait for 10 seconds
        Thread.sleep(6000);

        spotPage.clickCancelAllButton();
        Assert.assertTrue(spotPage.isSuccessMessageDisplayed(), "Success message is not displayed");

        String expectedMessage = "Your order cancellation request to cancel all orders has been successfully placed";
        Assert.assertEquals(spotPage.getSuccessMessageText(), expectedMessage);
    }

    @And("all orders should be canceled successfully")
    public void all_orders_should_be_canceled_successfully() {
        // Refresh the current page
        driver.navigate().refresh();

        boolean ordersCanceled = spotPage.areAllOrdersCanceled();
        assertTrue("All orders should be canceled successfully, but some cancellations of spot orders of type Limit failed.", ordersCanceled);
    }

    @And("the seller info notification should be displayed")
    public void the_seller_info_notification_should_be_displayed() {
        // Check if the notification is displayed
        Assert.assertTrue(spotPage.isInfoNotificationDisplayed(), "Info notification is not displayed");

        // Capture the actual notification text
        String actualNotification = spotPage.getInfoNotificationText();

        // Update the expected notification pattern to match either of the two possible patterns
        //String expectedNotificationPattern = "Your Spot Order T\\.[A-Za-z0-9]{8} of Type limit was partially filled \\((\\d{1,2}(\\.\\d{1,2})?)%\\)|Your Spot Order T\\.[0-9A-Z]{8} of Type limit was successfully filled";
        String expectedNotificationPattern = "Your Spot Order T\\.[A-Za-z0-9]{8} of Type limit was partially filled \\((\\d{1,2}(\\.\\d{1,2})?)%\\)|Your Spot Order T\\.[A-Za-z0-9]{8} of Type limit was successfully filled|Your Limit Order T\\.[A-Za-z0-9]{8} was automatically terminated by the system";


        // Assert that the actual notification matches the expected pattern
        Assert.assertTrue(actualNotification.matches(expectedNotificationPattern),
                "Notification does not match the expected pattern. Actual notification: " + actualNotification);
    }

    @Then("the seller logout")
    public void the_seller_logout() {
//        driver.close();
    }



}
